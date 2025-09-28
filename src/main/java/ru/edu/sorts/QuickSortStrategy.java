package ru.edu.sorts;

import java.util.Comparator;

public class QuickSortStrategy<T> implements SortStrategy<T> {


    /**
     * Метод для сортировки массива способом "быстрой сортировки"
     *
     * @param array      Массив объектов типа T
     * @param comparator Объект, определяющий порядок сортировки
     */
    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        quickSort(array, 0, array.length - 1, comparator);
    }

    /**
     * Основной метод быстрой сортировки
     *
     * @param arr        Исходный массив
     * @param low        Индекс начала
     * @param high       Индекс конца
     * @param comparator Способ сравнения
     */
    private void quickSort(T[] arr, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            // делим массив на 2 части и получаем индекс опорного элемента
            int pi = partition(arr, low, high, comparator);
            // сортировка рекурсией: слева от опорного и справа от опорного
            quickSort(arr, low, pi - 1, comparator);
            quickSort(arr, pi + 1, high, comparator);
        }
    }

    /**
     * Разделение массива
     *
     * @param arr        Исходный массив
     * @param low        Индекс начала
     * @param high       Индекс конца
     * @param comparator Способ сравнения
     * @return Индекс опорного элемента (pi)
     */
    private int partition(T[] arr, int low, int high, Comparator<T> comparator) {
        T pivot = arr[high]; // опорный элемент
        int i = (low - 1);

        // проходимся по массиву от low до high-1
        for (int j = low; j < high; j++) {
            // если элемент меньше или равен опорному элементу,
            // то меняем местами arr[j] и arr[i]
            if (comparator.compare(arr[j], pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Метод для перестановки двух элементов массива
     *
     * @param arr Исходный массив
     * @param i   Индекс одного элемента массива
     * @param j   Индекс другого элемента массива
     */
    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
