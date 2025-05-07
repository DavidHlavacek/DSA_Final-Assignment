package algorithms.sorting;

import datastructures.DataStructure;
import java.util.ArrayList;
import java.util.List;

// O(n^2)
public class InsertionSort implements SortAlgorithm {
    
    @Override
    public <T extends Comparable<T>> List<T> sort(DataStructure<T> dataStructure) {
        if (dataStructure.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<T> list = dataStructure.toList();
        List<T> result = new ArrayList<>(list); 
        
        int n = result.size();
        for (int i = 1; i < n; i++) {
            T key = result.get(i);
            int j = i - 1;
            
            // shift elements to the right
            while (j >= 0 && result.get(j).compareTo(key) > 0) {
                result.set(j + 1, result.get(j));
                j--;
            }
            
            result.set(j + 1, key);
        }
        
        return result;
    }
    
    @Override
    public String getName() {
        return "Insertion Sort";
    }
} 