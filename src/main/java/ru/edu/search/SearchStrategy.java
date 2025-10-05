package ru.edu.search;

import java.util.Comparator;
import java.util.List;

public interface SearchStrategy<T> {
    int search(List<T> list, T target, Comparator<T> comparator);
}