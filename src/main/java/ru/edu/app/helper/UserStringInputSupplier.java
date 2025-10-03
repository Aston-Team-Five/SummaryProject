package ru.edu.app.helper;

import java.util.Scanner;
import java.util.function.Supplier;

public class UserStringInputSupplier implements Supplier<String> {

    private final Scanner scanner;

    public UserStringInputSupplier(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String get() {
        return scanner.next();
    }
}
