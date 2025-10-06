package ru.edu.app;

import ru.edu.search.BinarySearchStrategy;
import ru.edu.search.SearchContext;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

class FindElementInCollectionState implements StateMenuItem {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getSelectionText() {
        return "Поиск элемента в коллекции (сначала коллекцию нужно отсортировать)";
    }

    @Override
    public void process(AppContext context) {
        Collection<Object> collection = context.getCollection();

        if (collection == null || collection.isEmpty()) {
            System.out.println("Коллекция пуста. Сначала заполните её.");
            return;
        }

        System.out.println("Введите имя для поиска:");
        String input = scanner.next();

        System.out.println("Введите возраст для поиска:");
        int input1 = scanner.nextInt();

        System.out.println("Введите город для поиска:");
        String input2 = scanner.next();

        // Преобразуем ввод в объект нужного типа (в зависимости от выбранного класса)
        Object target = parseInputToObject(input, input1, input2, context.getCollectionGenericType());

        // Создаем контекст стратегии поиска
        SearchContext<Object> searchContext = new SearchContext<>();
        searchContext.setStrategy(new BinarySearchStrategy<>());

        // Выполняем бинарный поиск
        int index = searchContext.executeSearch(collection, target);

        if (index >= 0) {
            System.out.println("Элемент найден по индексу: " + index);
            System.out.println("Найденный элемент: " + ((List<?>) collection).get(index));
        } else {
            System.out.println("Элемент не найден.");
        }

        context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
    }

    public Object parseInputToObject(String input, int input1, String input2, Class<?> genericType) {
        if (genericType == null) {
            System.out.println("⚠ Класс коллекции не задан. Невозможно преобразовать ввод.");
            return null;
        }

        try {
            // Если коллекция содержит простые типы
            if (genericType == String.class) {
                return input;
            }
            if (genericType == Integer.class) {
                return Integer.parseInt(input);
            }
            if (genericType == Double.class) {
                return Double.parseDouble(input);
            }

            // Если это пользовательский класс — ищем конструктор с одним String
            try {
                Constructor<?> declaredConstructor = genericType.getDeclaredConstructor(String.class, int.class, String.class);
                declaredConstructor.setAccessible(true);
                return declaredConstructor.newInstance(input, input1, input2);
            } catch (NoSuchMethodException e) {
                // Если такого конструктора нет — пробуем без аргументов
                Object obj = genericType.getDeclaredConstructor().newInstance();
                System.out.println("⚠ Не найден конструктор(String). Используется пустой объект: " + obj);
                return obj;
            }

        } catch (Exception e) {
            System.out.println("Ошибка преобразования строки в объект типа " + genericType.getSimpleName());
            e.printStackTrace();
            return null;
        }
    }

}
