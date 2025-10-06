package ru.edu.app;

import ru.edu.data.FillArray;
import java.util.Scanner;

public class FillCollectionFromKeyboard implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Заполнить коллекцию вручную с клавиатуры";
    }

    @Override
    public void process(AppContext context) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== ЗАПОЛНЕНИЕ КОЛЛЕКЦИИ С КЛАВИАТУРЫ ===");
        System.out.print("Сколько записей хотите добавить? ");

        try {
            int count = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            if (count <= 0) {
                System.out.println("❌ Количество должно быть положительным числом");
                return;
            }

            // Используем FillArray для ввода данных
            FillArray fillArray = new FillArray();

            // Если в FillArray есть метод для ручного ввода определенного количества
            // fillArray.fillManual(count);

            // Или используем существующий FillArrayScanner
            fillArray.FillArrayScanner(); // он сам спросит количество

            // Переносим данные в основную коллекцию
            context.getCollection().addAll(fillArray.getPersonList());

            System.out.println("✅ Успешно добавлено " + fillArray.getPersonList().size() + " записей");

        } catch (Exception e) {
            System.out.println("❌ Ошибка ввода: " + e.getMessage());
        } finally {
            context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
        }
    }
}