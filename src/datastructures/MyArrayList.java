package datastructures;

import java.util.ArrayList;
import java.util.List;

public class MyArrayList<T> implements DataStructure<T> {
    private T[] data;
    private int size;
    
    public MyArrayList() {
        data = (T[]) new Object[10];
        size = 0;
    }
    
    @Override
    public void add(T element) {
        if (size == data.length) {
            grow();
        }
        data[size++] = element;
    }
    
    private void grow() {
        T[] newData = (T[]) new Object[data.length * 2];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }
    
    @Override
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                // shift elements left
                System.arraycopy(data, i + 1, data, i, size - i - 1);
                data[--size] = null;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public T get(int index) {
        return (index >= 0 && index < size) ? data[index] : null;
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
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }
    
    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(data[i]);
        }
        return list;
    }
} 