package ru.edu.data;

import ru.edu.model.Person;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.System.*;



public class FillArray {
    List<Person> personList = new ArrayList<>();

    public List<Person> getPersonList() {
        return personList;
    }

    public void FillArrayInFile() {
        String path = "src/main/java/ru/edu/data/data.txt";
        personList = readFile(path);
        if (personList != null) {
            for (Person person : personList) {
                out.println(person);
            }
        }
    }

    public List<Person> readFile(String filePath) {
        List<Person> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String city = parts[2];
                    list.add(new Person(name, age, city));
                } else {
                    err.println("Ошибка обработки строки: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            out.println("Файл не найден!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> readFileWithRandom(String filePath) {
        List<String> arrayRandom = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                for (String part : parts) {
                    arrayRandom.add(part.replaceAll("\\s", ""));
                }
            }
        } catch (FileNotFoundException e) {
            out.println("Файл не найден!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return arrayRandom;
    }


    public void FillArrayRandom() {
        String pathName = "src/main/java/ru/edu/data/randomName.txt";
        String pathCity = "src/main/java/ru/edu/data/randomCity.txt";
        Random random = new Random();

        while (personList.isEmpty()) {
            try {
                out.print("Какое кол-во данных будет вводиться: ");
                Scanner scanner = new Scanner(in);
                int count = scanner.nextInt();

                List<String> randomName = readFileWithRandom(pathName);
                List<String> randomCity = readFileWithRandom(pathCity);

                for (int i = 0; i < count; i++) {
                    int randomIndexName = random.nextInt(randomName.size());
                    int randomIndexCity = random.nextInt(randomCity.size());
                    personList.add(new Person(randomName.get(randomIndexName), random.nextInt(1, 123), randomCity.get(randomIndexCity)));
                }
            } catch (InputMismatchException ex) {
                out.println("Неккоретно введенно значение!");
            }
        }
    }

    public void FillArrayScanner() {
        while (personList.isEmpty()) {
            try {
                out.print("Какое кол-во данных будет вводиться: ");
                Scanner scanner = new Scanner(in);
                int count = scanner.nextInt();
                scanner.nextLine();
                int age = 0;
                int sizePersonList = personList.size();
                for (int i = sizePersonList; i < count + sizePersonList; i++) {

                    out.print("\n Введите имя для " + (i + 1) + " элемента:");
                    String name = scanner.nextLine();

                    while (age == 0 || age > 123){
                        try {
                            out.print("\n Введите возвраст для " + (i + 1) + " элемента:");
                            age = scanner.nextInt();
                            scanner.nextLine();
                            if (age > 123) {
                                out.println("Возраст слишком большой!");
                            }
                        } catch (InputMismatchException ex) {
                            out.println("Неккоретно введенно значение!");
                            scanner.nextLine();
                        }
                    }

                    out.print("\n Введите город для " + (i + 1) + " элемента:");
                    String city = scanner.nextLine();

                    personList.add(new Person(name, age, city));
                }
            } catch (InputMismatchException ex) {
                out.println("Неккоретно введенно значение!");
            }
        }
    }
}