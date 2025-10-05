package ru.edu.app;

import ru.edu.search.BinarySearchStrategy;
import ru.edu.search.SearchContext;

import java.util.Comparator;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

class FindElementInCollectionState implements StateMenuItem {
    private final AppContext context;
    private final Scanner scanner = new Scanner(System.in);

    FindElementInCollectionState(AppContext context) {
        this.context = context;
    }

    @Override
    public String getSelectionText() {
        return "Поиск элемента в коллекции";
    }

    @Override
    public void process(AppContext context) {

        //System.out.println("Не реализовано");

        Collection<?> collection = context.getCollection();

        if (collection == null || collection.isEmpty()) {
            System.out.println("Коллекция пуста. Сначала заполните её.");
            return;
        }

        System.out.println("Введите значение элемента для поиска:");
        String input = scanner.nextLine().trim();

        // Преобразуем ввод в объект нужного типа (в зависимости от выбранного класса)
        Object target = context.parseInputToObject(input);

        // Получаем текущий компаратор, по которому коллекция была отсортирована
        Comparator<Object> comparator = context.getCurrentComparator();

        if (comparator == null) {
            System.out.println("Компаратор не задан. Сначала выполните сортировку.");
            return;
        }

        // Создаем контекст стратегии поиска
        SearchContext<Object> searchContext = new SearchContext<>();
        searchContext.setStrategy(new BinarySearchStrategy<>());

        // Выполняем бинарный поиск
        int index = searchContext.executeSearch((List<Object>) collection, target, comparator);

        if (index >= 0) {
            System.out.println("Элемент найден по индексу: " + index);
            System.out.println("Найденный элемент: " + ((List<?>) collection).get(index));
        } else {
            System.out.println("Элемент не найден.");
        }
        // context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
    }
    
}
