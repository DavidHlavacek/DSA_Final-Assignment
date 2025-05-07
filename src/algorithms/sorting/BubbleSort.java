package algorithms.sorting;

import datastructures.DataStructure;
import java.util.ArrayList;
import java.util.List;

// O(n^2)
public class BubbleSort implements SortAlgorithm {
    
    @Override
    public <T extends Comparable<T>> List<T> sort(DataStructure<T> dataStructure) {
        if (dataStructure.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<T> list = dataStructure.toList();
        List<T> result = new ArrayList<>(list); 
        
        // compares left to right BUT orders from right to left
        int n = result.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (result.get(j).compareTo(result.get(j + 1)) > 0) {
                    // swap
                    T temp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, temp);
                }
            }
        }
        
        return result;
    }
    
    @Override
    public String getName() {
        return "Bubble Sort";
    }
} 