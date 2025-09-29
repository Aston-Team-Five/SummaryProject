package data;

import model.Person;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;



public class FillArray {
    List<Person> personList;

    public List<Person> getPersonList() {
        return personList;
    }

    public void FillArrayInFile() {
        String path = "src/main/java/ru/edu/data/data.txt";
        personList = readFile(path);
        if (personList != null) {
            for (Person person : personList){
                out.println(person);
            }
        }
    }

    public List<Person> readFile(String filePath) {
        List<Person> list = new ArrayList<>();
        list.getClass().getFields();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String city = parts[2];
                    list.add(new Person(name,age,city));
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


    public void FillArrayRandom() {
        /*TODO: Изначально спрашивается скольок
                       будет значнеий и после этого идет заполнение массива рандомно*/
    }

    public void FillArrayScanner() {
            /*TODO: Изначально спрашивается скольок
                       будет значнеий и после этого идет заполнение массива через Scanner*/
    }
}

