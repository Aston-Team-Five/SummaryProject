package ru.edu.sorts;

import java.util.Comparator;

public interface SortStrategy<T> {
    /**
     * Метод для сортировки массива
     *
     * @param array      Массив объектов типа T
     * @param comparator Объект, определяющий порядок сортировки
     */
    void sort(T[] array, Comparator<T> comparator);
}
