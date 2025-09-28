package ru.edu.sorts;
import java.util.Comparator;

// этот класс для проверки реализованного
public class example {
    private static class PersonExampleArtem {
        private String name;
        private int age;

        PersonExampleArtem(String name, int age) {
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
//        PersonExampleArtem[] people = {
//                new PersonExampleArtem("Bob", 30),
//                new PersonExampleArtem("Alice", 25),
//                new PersonExampleArtem("Alice", 24),
//                new PersonExampleArtem("Charlie", 35)
//        };
//        SortContext<PersonExampleArtem> context = new SortContext<>();
//        context.setStrategy(new ParallelMergeSortStrategy<>());
//        context.executeSort(people, Comparator.comparing(PersonExampleArtem::getName).thenComparing(PersonExampleArtem::getAge));
//        for (PersonExampleArtem p : people) {
//            System.out.println(p.getName() + " " + p.getAge());
//        }


//        Integer[] data = {5,2,8,3,9,1};
//        SortContext<Integer> context1 = new SortContext<>();
//        context1.setStrategy(new ParallelMergeSortStrategy<>());
//        context1.executeSort(data, Integer::compareTo);
//        for (Integer i : data) {
//            System.out.println(i+" ");
//        }

//        Integer[] data = {5,6,2,7,8,2,7,8,81,7,5,1,79,2,90};
//        SortContext<Integer> context2 = new SortContext<>();
//        context2.setStrategy(new QuickSortStrategy<>());
//        context2.executeSort(data, Integer::compareTo);
//        for (Integer i : data){
//            System.out.print(i+" ");
//        }
    }
}
