package datastructures;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> implements DataStructure<T> {
    
    private class Node {
        T data;
        Node left;
        Node right;
        
        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    
    private Node root;
    private int size;
    
    public BinarySearchTree() {
        root = null;
        size = 0;
    }
    
    @Override
    public void add(T element) {
        root = addRecursive(root, element);
        size++;
    }
    
    private Node addRecursive(Node current, T element) {
        if (current == null) {
            return new Node(element);
        }
        
        int comparison = element.compareTo(current.data);
        
        if (comparison < 0) {
            current.left = addRecursive(current.left, element);
        } else {
            current.right = addRecursive(current.right, element);
        }
        
        return current;
    }
    
    @Override
    public boolean remove(T element) {
        int originalSize = size;
        root = removeRecursive(root, element);
        return size < originalSize;
    }
    
    private Node removeRecursive(Node current, T element) {
        if (current == null) {
            return null;
        }
        
        int comparison = element.compareTo(current.data);
        
        if (comparison < 0) {
            current.left = removeRecursive(current.left, element);
        } else if (comparison > 0) {
            current.right = removeRecursive(current.right, element);
        } else {
            // no children
            if (current.left == null && current.right == null) {
                size--;
                return null;
            }
            
            // one child
            if (current.left == null) {
                size--;
                return current.right;
            }
            
            if (current.right == null) {
                size--;
                return current.left;
            }
            
            // two children
            current.data = findSmallestValue(current.right);
            current.right = removeRecursive(current.right, current.data);
            return current;
        }
        
        return current;
    }
    
    private T findSmallestValue(Node current) {
        return current.left == null ? current.data : findSmallestValue(current.left);
    }
    
    @Override
    public boolean contains(T element) {
        return containsRecursive(root, element);
    }
    
    private boolean containsRecursive(Node current, T element) {
        if (current == null) {
            return false;
        }
        
        int comparison = element.compareTo(current.data);
        
        if (comparison == 0) {
            return true;
        }
        
        return comparison < 0
                ? containsRecursive(current.left, element)
                : containsRecursive(current.right, element);
    }
    
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        
        List<T> elements = new ArrayList<>();
        inOrderTraversal(root, elements);
        
        if (index >= elements.size()) {
            return null;
        }
        
        return elements.get(index);
    }
    
    private void inOrderTraversal(Node node, List<T> elements) {
        if (node != null) {
            inOrderTraversal(node.left, elements);
            elements.add(node.data);
            inOrderTraversal(node.right, elements);
        }
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
        root = null;
        size = 0;
    }
    
    @Override
    public List<T> toList() {
        List<T> elements = new ArrayList<>(size);
        inOrderTraversal(root, elements);
        return elements;
    }
} 