package algorithms.searching;

import algorithms.sorting.InsertionSort;
import datastructures.DataStructure;

import java.util.ArrayList;
import java.util.List;

// O(n log n)
public class BinarySearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> List<Integer> search(DataStructure<T> dataStructure, T key) {
        List<Integer> indices = new ArrayList<>();
        
        if (dataStructure.isEmpty()) {
            return indices;
        }
        
        // sort list
        InsertionSort sorter = new InsertionSort();
        List<T> sortedList = sorter.sort(dataStructure);
        
        // find first occurrence of key
        int index = binarySearchRecursive(sortedList, key, 0, sortedList.size() - 1);
        
        // key not found
        if (index == -1) {
            return indices;
        }
                
        // check to the left
        int left = index;
        while (left >= 0 && sortedList.get(left).equals(key)) {
            indices.add(left);
            left--;
        }
        
        // check to the right 
        int right = index + 1;
        while (right < sortedList.size() && sortedList.get(right).equals(key)) {
            indices.add(right);
            right++;
        }
        
        return indices;
    }
    
    private <T extends Comparable<T>> int binarySearchRecursive(List<T> list, T key, int left, int right) {
        // base case - element not found
        if (right < left) {
            return -1;
        }
        
        int middle = left + ((right - left) / 2);
        
        // compare middle with key
        int comparison = list.get(middle).compareTo(key);
        
        if (comparison == 0) {
            // key found 
            return middle;
        } else if (comparison > 0) {
            // key smaller - search left half
            return binarySearchRecursive(list, key, left, middle - 1);
        } else {
            // key larger - search right half
            return binarySearchRecursive(list, key, middle + 1, right);
        }
    }
    
    @Override
    public String getName() {
        return "Binary Search";
    }
} 