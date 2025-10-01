package ru.edu.sorts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

// этот класс для проверки реализованного
public class exampleArtem {
    private static class Person {
        private final String name;
        private final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        String getName() {
            return name;
        }

        int getAge() {
            return age;
        }
    }

    public static void main(String[] args) {
        Collection<Person> people = new ArrayList<>();
        people.add(new Person("Charlie", 35));
        people.add(new Person("Alice",31));
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));

        SortContext<Person> context = new SortContext<>();
        context.setStrategy(new ParallelMergeSortStrategy<>());

        var sortedList = context.executeSort(people, Comparator.comparing(Person::getName).thenComparing(Person::getAge));

        System.out.println("\nСортировка по имени, затем по возрасту:");
        for (Person p : sortedList) {
            System.out.println(p.name+" "+p.age);
        }
    }
}
