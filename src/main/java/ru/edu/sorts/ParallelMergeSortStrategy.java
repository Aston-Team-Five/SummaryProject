package ru.edu.sorts;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSortStrategy<T> implements SortStrategy<T> {

    // Пул потоков для выполнения задач
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    /**
     * Основной метод для параллельной сортировки слиянием
     *
     * @param collection Исходная коллекция
     * @param comparator Объект, определяющий порядок сортировки
     */
    @Override
    public List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        T[] array = (T[]) collection.toArray();
        //запускаем задачу в пуле потока
        forkJoinPool.invoke(new MergeSortTask<>(array, 0, array.length - 1, comparator));
        return Arrays.asList(array);
    }

    // Наследуем абстрактный класс, который не требует возвращаемого результата
    private static class MergeSortTask<T> extends RecursiveAction {
        // входные данные
        private final T[] array;
        private final int left;
        private final int right;
        private final Comparator<T> comparator;

        /**
         * Конструктор
         *
         * @param array      Исходный массив
         * @param left       Левая часть массива
         * @param right      Правая часть массива
         * @param comparator Способ сравнения элементов
         */
        public MergeSortTask(T[] array, int left, int right, Comparator<T> comparator) {
            this.array = array;
            this.left = left;
            this.right = right;
            this.comparator = comparator;
        }

        /**
         * Основной метод выполнения задачи
         */
        @Override
        protected void compute() {
            if (left < right) { // если больше 1-го элемента
                int mid = (left + right) / 2;
                MergeSortTask<T> leftTask = new MergeSortTask<>(array, left, mid, comparator); // для левой половины
                MergeSortTask<T> rightTask = new MergeSortTask<>(array, mid + 1, right, comparator); // для правой половины

                invokeAll(leftTask, rightTask); // запускаем обе задачи в разных потоках

                merge(array, left, mid, right, comparator); // после завершения обеих задач объединяем отсортированные части
            }
        }

        /**
         * Основной метод сортировки слиянием
         *
         * @param arr        Исходный массив
         * @param left       Левая часть массива
         * @param mid        Середина массива
         * @param right      Правая часть массива
         * @param comparator Способ сортировки
         */
        private void merge(T[] arr, int left, int mid, int right, Comparator<T> comparator) {
            // проверка на многопоточность
            // System.out.println(Thread.currentThread().getName()+ " выполняет merge");
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
}
