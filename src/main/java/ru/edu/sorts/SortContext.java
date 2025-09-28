package ru.edu.sorts;

import java.util.Comparator;

public class SortContext<T> {
    private SortStrategy<T> strategy;

    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void executeSort(T[] array, Comparator<T> comparator) {
        strategy.sort(array, comparator);
    }
}
