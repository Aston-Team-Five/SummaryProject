package ru.edu.app;

import ru.edu.model.Person;
import ru.edu.search.SimpleMultiThreadedCounter;
import java.util.*;
import java.util.function.Predicate;

class MultiThreadedCountState implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Многопоточный подсчет элементов";
    }

    @Override
    public void process(AppContext context) {
        Scanner scanner = new Scanner(System.in);
        Collection<Object> collection = context.getCollection();

        if (collection.isEmpty()) {
            System.out.println("❌ Коллекция пуста!");
            return;
        }

        System.out.println("\n=== МНОГОПОТОЧНЫЙ ПОДСЧЕТ ===");
        System.out.println("Выберите поле для поиска:");
        System.out.println("1. Имя");
        System.out.println("2. Возраст");
        System.out.println("3. Город");

        String choice = scanner.nextLine().trim();

        Predicate<Person> condition;
        String fieldName;

        switch (choice) {
            case "1":
                System.out.print("Введите имя для поиска: ");
                String name = scanner.nextLine().trim();
                condition = person -> person.getName().equalsIgnoreCase(name);
                fieldName = "имени '" + name + "'";
                break;

            case "2":
                System.out.print("Введите возраст для поиска: ");
                try {
                    int age = Integer.parseInt(scanner.nextLine().trim());
                    condition = person -> person.getAge() == age;
                    fieldName = "возрасту " + age;
                } catch (NumberFormatException e) {
                    System.out.println("❌ Возраст должен быть числом!");
                    return;
                }
                break;

            case "3":
                System.out.print("Введите город для поиска: ");
                String city = scanner.nextLine().trim();
                condition = person -> person.getCity().equalsIgnoreCase(city);
                fieldName = "городу '" + city + "'";
                break;

            default:
                System.out.println("❌ Неверный выбор!");
                return;
        }

        System.out.println("⏳ Подсчет...");

        long startTime = System.currentTimeMillis();
        int count = SimpleMultiThreadedCounter.countPersons(collection, condition);
        long endTime = System.currentTimeMillis();

        System.out.println("\n📊 РЕЗУЛЬТАТ:");
        System.out.println("Поиск по: " + fieldName);
        System.out.println("Найдено вхождений: " + count);
        System.out.println("Время: " + (endTime - startTime) + " мс");
        System.out.println("Потоков: " + Runtime.getRuntime().availableProcessors());

        if (count == 0) {
            System.out.println("💡 Совет: проверьте правильность ввода данных");
        }
    }
}

