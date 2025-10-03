package ru.edu.app.helper;

import java.util.Scanner;
import java.util.function.Supplier;

public class UserChoiceSupplier implements Supplier<Integer> {

    private final Scanner scanner;

    public UserChoiceSupplier(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Integer get() {
        Integer result = null;
        while (result == null) {
            String next = scanner.next();
            try {
                result = Integer.parseInt(next);
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Пожалуйста, введите целое число");
            }
        }
        return result;
    }

}
