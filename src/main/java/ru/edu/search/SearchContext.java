package ru.edu.search;

import java.util.Comparator;
import java.util.List;

public class SearchContext<T> {
    private SearchStrategy<T> strategy;

    public void setStrategy(SearchStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public int executeSearch(List<T> list, T target, Comparator<T> comparator) {
        if (strategy == null) {
            throw new IllegalStateException("Search strategy is not set");
        }
        return strategy.search(list, target, comparator);
    }
}