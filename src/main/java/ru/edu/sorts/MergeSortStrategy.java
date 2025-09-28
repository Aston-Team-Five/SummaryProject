package ru.edu.sorts;

import java.util.Comparator;

public class MergeSortStrategy<T> implements SortStrategy<T> {

    /**
     * Метод для сортировки массива способом "Сортировка слиянием"
     *
     * @param array      Исходный массив
     * @param comparator Объект, определяющий порядок сортировки
     */
    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array.length > 1) {
            mergeSort(array, 0, array.length - 1, comparator);
        }
    }

    /**
     * Основной метод сортировки слиянием
     *
     * @param arr        Исходный массив
     * @param left       Левая часть массива
     * @param right      Правая часть массива
     * @param comparator Способ сравнения
     */
    private void mergeSort(T[] arr, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, comparator); // левая часть
            mergeSort(arr, mid + 1, right, comparator); // правая часть
            merge(arr, left, mid, right, comparator); // слияние
        }
    }

    /**
     * Слияние двух отсортированных подмассивов
     *
     * @param arr        Исходный массив
     * @param left       Левая часть массива
     * @param mid        Середина массива
     * @param right      Правая часть массива
     * @param comparator Способ сравнения
     */
    private void merge(T[] arr, int left, int mid, int right, Comparator<T> comparator) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Создаём 2 временных массива
        T[] L = (T[]) new Object[n1];
        T[] R = (T[]) new Object[n2];

        System.arraycopy(arr, left, L, 0, n1); // копируем n1 в левый массив
        System.arraycopy(arr, mid + 1, R, 0, n2); // копируем n2 в правый массив

        int i = 0, j = 0, k = left;

        // сравниваем элементы из левого и правого подмассивов
        // и вставляем меньший в исходный массив
        while (i < n1 && j < n2) {
            if (comparator.compare(L[i], R[j]) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // если в левом подмассиве остались элементы,
        // то копируем их в исходный массив
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // если в правом подмассиве остались элементы,
        // то копируем их в исходный массив
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}
