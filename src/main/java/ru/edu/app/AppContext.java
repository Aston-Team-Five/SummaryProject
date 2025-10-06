package ru.edu.app;

import ru.edu.app.helper.UserChoiceSupplier;
import ru.edu.app.helper.UserStringInputSupplier;
import ru.edu.sorts.SortStrategy;
import ru.edu.util.SimpleLinkedList;

import java.util.Collection;
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
        this.scanner = new Scanner(System.in);
        this.userChoiceSupplier = new UserChoiceSupplier(scanner);
        this.userStringInputSupplier = new UserStringInputSupplier(scanner);
        this.currentState = new StartState(userChoiceSupplier);
        this.collection = new SimpleLinkedList<>();
        this.fieldNamesToSortBy = new SimpleLinkedList<>();
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

    public void setCollection(Collection<Object> collection) {
        this.collection = collection;
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

}
