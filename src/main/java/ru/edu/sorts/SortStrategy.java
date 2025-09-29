package ru.edu.sorts;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface SortStrategy<T> {
    /**
     * Метод для сортировки коллекции
     *
     * @param collection Коллекция объектов типа T
     * @param comparator Объект, определяющий порядок сортировки
     */
    List<T> sort(Collection<T> collection, Comparator<T> comparator);
}
