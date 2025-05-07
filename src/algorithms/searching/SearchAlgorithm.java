package algorithms.searching;

import java.util.List;

import datastructures.DataStructure;

public interface SearchAlgorithm {

    <T extends Comparable<T>> List<Integer> search(DataStructure<T> dataStructure, T key);

    String getName();
} 