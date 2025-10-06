package ru.edu.app;

import ru.edu.sorts.SortContext;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;

class DoSortState implements State {

    @Override
    public void process(AppContext context) {
        if (context.getFieldNamesToSortBy().isEmpty()) {
            System.out.println("Невозможно выполнить сортировку, т. к. не заданы поля объекта для сортировки");
            context.setSortStrategy(null);
            context.setCurrentState(new SelectFieldsToSortByState(context.getUserChoiceSupplier(), context));
        } else {
            System.out.println("Выполнение сортировки по полям: " + context.getFieldNamesToSortByAsString() + " ...");
            try {
                Collection sorted = doSort(context);
                context.setCollection(sorted);
                System.out.println("Готово");
                printCollection(sorted);
            } catch (Exception e) {
                System.out.println("Возникла ошибка при выполнении сортировки: " + e.getMessage());
            }
            context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
        }
        context.getFieldNamesToSortBy().clear();
    }

    @SuppressWarnings("unchecked")
    private Collection<?> doSort(AppContext context) {
        SortContext<Object> sortContext = new SortContext<>();
        sortContext.setStrategy(context.getSortStrategy());
        Collection<Object> collection = context.getCollection();
        Comparator<Object> comparator = createComparator(context.getCollectionGenericType(), context.getFieldNamesToSortBy());
        return sortContext.executeSort(collection, comparator);
    }

    public static <T> Comparator<Object> createComparator(Class<T> clazz, Collection<String> propertyNames) {
        if (propertyNames == null || propertyNames.isEmpty()) {
            throw new IllegalArgumentException("Property names cannot be empty");
        }

        Comparator<Object> comparator = null;

        for (String propertyName : propertyNames) {
            Comparator<Object> currentComparator = createSinglePropertyComparator(clazz, propertyName);

            if (comparator == null) {
                comparator = currentComparator;
            } else {
                comparator = comparator.thenComparing(currentComparator);
            }
        }

        return comparator;
    }

    private static <T> Comparator<Object> createSinglePropertyComparator(Class<T> clazz, String propertyName) {
        try {
            Method getter = findGetterMethod(clazz, propertyName);
            return Comparator.comparing(obj -> extractValue(obj, getter), Comparator.nullsLast(Comparator.naturalOrder()));
        } catch (Exception e) {
            throw new IllegalStateException("Невозможно создать компаратор для поля '" + propertyName + "' класса " + clazz, e);
        }
    }

    private static <T> Method findGetterMethod(Class<T> clazz, String propertyName) {
        String[] possibleGetters = {
                "get" + capitalize(propertyName),
                "is" + capitalize(propertyName),
                propertyName
        };

        for (String getterName : possibleGetters) {
            try {
                return clazz.getMethod(getterName);
            } catch (NoSuchMethodException e) {
                // Продолжаем поиск
            }
        }

        throw new IllegalStateException("Не найден геттер для поля: " + propertyName);
    }

    @SuppressWarnings("unchecked")
    private static <T> Comparable<Object> extractValue(T obj, Method getter) {
        try {
            return (Comparable<Object>) getter.invoke(obj);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to extract value", e);
        }
    }

    public static String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private void printCollection(Collection<?> collection) {
        System.out.println("Отсортированная коллекция:");
        for (Object object : collection) {
            System.out.println(object);
        }
    }
}
