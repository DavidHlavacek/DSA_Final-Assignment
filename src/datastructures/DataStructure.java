package datastructures;

import java.util.List;

public interface DataStructure<T> {
    void add(T element);
    boolean remove(T element);
    boolean contains(T element);
    T get(int index);
    
    int size();
    boolean isEmpty();
    void clear();
        
    List<T> toList();
} 