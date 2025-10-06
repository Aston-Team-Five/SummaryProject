package ru.edu.search;

import java.util.Collection;

public class BinarySearchStrategy<T> implements SearchStrategy<T> {

    @Override
    public int search(Collection<T> collection, T target) {
        if (collection == null || collection.isEmpty() || target == null) {
            return -1;
        }

        T[] collectionArray = (T[]) collection.toArray();

        int left = 0;
        int right = collectionArray.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            T midValue = collectionArray[mid];
            int cmp = ((Comparable<T>) midValue).compareTo(target);

            if (cmp == 0) {
                return mid; // элемент найден
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1; // не найден
    }
}