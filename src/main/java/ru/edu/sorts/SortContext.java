package ru.edu.sorts;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class SortContext<T> {
    private SortStrategy<T> strategy;

    /**
     * Установка стратегии сортировки
     * @param strategy Стратегия сортировки
     */
    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    /**
     * Выполнение сортировки выбранной стратегией
     * @param collection Коллекция для сортировки
     * @param comparator Объект, определяющий порядок сортировки
     */
    public List<T> executeSort(Collection<T> collection, Comparator<T> comparator) {
        return strategy.sort(collection, comparator);
    }
}
