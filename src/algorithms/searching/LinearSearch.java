package algorithms.searching;

import java.util.ArrayList;
import java.util.List;

import datastructures.DataStructure;

// O(n)
public class LinearSearch implements SearchAlgorithm {
    
    @Override
    public <T extends Comparable<T>> List<Integer> search(DataStructure<T> dataStructure, T key) {
        List<Integer> indices = new ArrayList<>();
        
        if (dataStructure.isEmpty()) {
            return indices;
        }
        
        List<T> elements = dataStructure.toList();
        
        // pretty straightforward
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(key)) {
                indices.add(i);
            }
        }
        
        return indices;
    }
    
    @Override
    public String getName() {
        return "Linear Search";
    }
} 