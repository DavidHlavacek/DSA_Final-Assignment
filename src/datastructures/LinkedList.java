package datastructures;

import java.util.ArrayList;
import java.util.List;

public class LinkedList<T> implements DataStructure<T> {
    
    private class Node {
        T data;
        Node next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node head;
    private int size;
    
    public LinkedList() {
        head = null;
        size = 0;
    }
    
    @Override
    public void add(T element) {
        Node newNode = new Node(element);
        
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        
        size++;
    }
    
    @Override
    public boolean remove(T element) {
        if (head == null) {
            return false;
        }
        
        if (head.data.equals(element)) {
            head = head.next;
            size--;
            return true;
        }
        
        Node current = head;
        while (current.next != null && !current.next.data.equals(element)) {
            current = current.next;
        }
        
        if (current.next != null) {
            current.next = current.next.next;
            size--;
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean contains(T element) {
        Node current = head;
        
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        
        return current.data;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public void clear() {
        head = null;
        size = 0;
    }
    
    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>(size);
        Node current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }
} 