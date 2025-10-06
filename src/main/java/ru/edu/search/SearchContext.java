package ru.edu.search;

import java.util.Collection;

public class SearchContext<T> {

    private SearchStrategy<T> strategy;

    public void setStrategy(SearchStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public int executeSearch(Collection<T> collection, T target) {
        if (strategy == null) {
            throw new IllegalStateException("Search strategy is not set");
        }
        return strategy.search(collection, target);
    }
}