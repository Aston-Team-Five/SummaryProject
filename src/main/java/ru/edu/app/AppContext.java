package ru.edu.app;

import ru.edu.app.helper.UserChoiceSupplier;
import ru.edu.app.helper.UserStringInputSupplier;
import ru.edu.sorts.SortStrategy;
import ru.edu.util.SimpleLinkedList;

import java.util.Collection;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Supplier;

final class AppContext {

    private State currentState;
    private Scanner scanner;
    private Supplier<Integer> userChoiceSupplier;
    private Supplier<String> userStringInputSupplier;

    private Collection<Object> collection;
    private int collectionMaxSize;
    private Class<?> genericType;
    private SortStrategy<?> sortStrategy;
    private Collection<String> fieldNamesToSortBy;

    public AppContext() {
        scanner = new Scanner(System.in);
        userChoiceSupplier = new UserChoiceSupplier(scanner);
        userStringInputSupplier = new UserStringInputSupplier(scanner);
        currentState = new StartState(userChoiceSupplier);
        collection = new SimpleLinkedList<>();
        fieldNamesToSortBy = new SimpleLinkedList<>();
    }

    public void process() {
        while (!(currentState instanceof FinishedState)) {
            currentState.process(this);
        }
    }

    public void setCurrentState(ru.edu.app.State state) {
        currentState = state;
    }

    public Supplier<Integer> getUserChoiceSupplier() {
        return userChoiceSupplier;
    }

    public Supplier<String> getUserStringInputSupplier() {
        return userStringInputSupplier;
    }

    public void setCollectionMaxSize(int size) {
        this.collectionMaxSize = size;
    }

    public void closeScanner() {
        try {
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setObjectType(Class<?> clazz) {
        this.genericType = clazz;
    }

    public Class<?> getCollectionGenericType() {
        return genericType;
    }

    public Collection<Object> getCollection() {
        return collection;
    }

    public int getCollectionMaxSize() {
        return collectionMaxSize;
    }

    @SuppressWarnings("rawtypes")
    public SortStrategy getSortStrategy() {
        return sortStrategy;
    }

    public void setSortStrategy(SortStrategy<?> sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public Collection<String> getFieldNamesToSortBy() {
        return fieldNamesToSortBy;
    }

    public String getFieldNamesToSortByAsString() {
        if (fieldNamesToSortBy.isEmpty()) {
            return "";
        }
        return String.join(", ", fieldNamesToSortBy);
    }
    //find
    Comparator<Object> currentComparator;
    public Comparator<Object> getCurrentComparator() {

        return currentComparator;
    }
    private Class<?> currentClass;
    public Object parseInputToObject(String input) {
        if (currentClass == null) {
            System.out.println("⚠ Класс коллекции не задан. Невозможно преобразовать ввод.");
            return null;
        }

        try {
            // Если коллекция содержит простые типы
            if (currentClass == String.class) {
                return input;
            }
            if (currentClass == Integer.class) {
                return Integer.parseInt(input);
            }
            if (currentClass == Double.class) {
                return Double.parseDouble(input);
            }

            // Если это пользовательский класс — ищем конструктор с одним String
            try {
                return currentClass.getConstructor(String.class).newInstance(input);
            } catch (NoSuchMethodException e) {
                // Если такого конструктора нет — пробуем без аргументов
                Object obj = currentClass.getDeclaredConstructor().newInstance();
                System.out.println("⚠ Не найден конструктор(String). Используется пустой объект: " + obj);
                return obj;
            }

        } catch (Exception e) {
            System.out.println("Ошибка преобразования строки в объект типа " + currentClass.getSimpleName());
            e.printStackTrace();
            return null;
        }
    }
}
