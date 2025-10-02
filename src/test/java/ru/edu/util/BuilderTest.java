package ru.edu.util;

import ru.edu.data.FillArray;
import ru.edu.model.Person;

public class BuilderTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТИРУЕМ ПРОЕКТ ===");

        // Тест Builder
        try {
            Person person = Person.builder()
                .setName("Тестовый Пользователь")
                .setAge(30)
                .setCity("Тестовый Город")
                .build();
            System.out.println("✅ Builder работает: " + person);
        } catch (Exception e) {
            System.out.println("❌ Ошибка Builder: " + e.getMessage());
        }

        // Тест чтения файла
        FillArray fillArray = new FillArray();
        fillArray.FillArrayInFile();
        System.out.println("✅ Файл прочитан, записей: " + fillArray.getPersonList().size());
    }
}