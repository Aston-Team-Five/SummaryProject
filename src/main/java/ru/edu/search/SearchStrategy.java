package ru.edu.search;

import java.util.Collection;

public interface SearchStrategy<T> {

    int search(Collection<T> list, T target);

}