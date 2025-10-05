package ru.edu.search;

import java.util.Comparator;
import java.util.List;

public class BinarySearchStrategy<T> implements SearchStrategy<T> {

    @Override
    public int search(List<T> list, T target, Comparator<T> comparator) {
        if (list == null || list.isEmpty() || target == null) {
            return -1;
        }

        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            T midValue = list.get(mid);
            int cmp = comparator.compare(midValue, target);

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