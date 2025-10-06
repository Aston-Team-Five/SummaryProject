package ru.edu.search;

import ru.edu.model.Person;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class SimpleMultiThreadedCounter {

    /**
     * Многопоточный подсчет Person объектов по условию
     */
    public static int countPersons(Collection<Object> collection, Predicate<Person> condition) {
        if (collection.isEmpty()) return 0;

        // Фильтруем только Person объекты
        List<Person> persons = new ArrayList<>();
        for (Object obj : collection) {
            if (obj instanceof Person) {
                persons.add((Person) obj);
            }
        }

        if (persons.isEmpty()) return 0;

        int threadCount = Math.min(Runtime.getRuntime().availableProcessors(), persons.size());
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            int chunkSize = Math.max(1, persons.size() / threadCount);
            List<Future<Integer>> futures = new ArrayList<>();

            for (int i = 0; i < threadCount; i++) {
                int start = i * chunkSize;
                int end = (i == threadCount - 1) ? persons.size() : start + chunkSize;

                if (start < persons.size()) {
                    List<Person> subList = persons.subList(start, end);
                    futures.add(executor.submit(new PersonCounterTask(subList, condition)));
                }
            }

            int total = 0;
            for (Future<Integer> future : futures) {
                total += future.get();
            }
            return total;

        } catch (Exception e) {
            return 0;
        } finally {
            executor.shutdown();
        }
    }

    private static class PersonCounterTask implements Callable<Integer> {
        private final List<Person> subList;
        private final Predicate<Person> condition;

        public PersonCounterTask(List<Person> subList, Predicate<Person> condition) {
            this.subList = subList;
            this.condition = condition;
        }

        @Override
        public Integer call() {
            int count = 0;
            for (Person person : subList) {
                if (condition.test(person)) {
                    count++;
                }
            }
            return count;
        }
    }
}
