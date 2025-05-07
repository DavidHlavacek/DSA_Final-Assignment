package algorithms.sorting;

import datastructures.DataStructure;
import java.util.List;

public interface SortAlgorithm {

    <T extends Comparable<T>> List<T> sort(DataStructure<T> dataStructure);
    
    String getName();
} 