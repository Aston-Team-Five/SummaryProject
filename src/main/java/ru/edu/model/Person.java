package ru.edu.model;

public class Person {
    private final String name;
    private final int age;
    private final String city;

    // Приватный конструктор - только Builder может создавать объекты
    private Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.city = builder.city;
    }

    // Геттеры (без сеттеров - объект неизменяемый)
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return String.format("Person{name='%s', age=%d, city='%s'}", name, age, city);
    }

    // Static Builder класс
    public static class Builder {
        private String name;
        private int age;
        private String city;

        public Builder setName(String name) {
            this.name = name;
            return this; // Возвращает this для цепочки вызовов
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Person build() {
            // Валидация при создании объекта
            validate();
            return new Person(this);
        }

        private void validate() {
            validateName();
            validateAge();
            validateCity();
        }

        private void validateName() {
            if (name == null) {
                throw new IllegalArgumentException("Имя не может быть null");
            }
            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("Имя не может быть пустым");
            }
            if (name.length() < 2) {
                throw new IllegalArgumentException("Имя должно содержать минимум 2 символа");
            }
            if (!name.matches("[а-яА-Яa-zA-Z\\s-]+")) {
                throw new IllegalArgumentException("Имя может содержать только буквы, пробелы и дефисы");
            }
        }

        private void validateAge() {
            if (age < 0) {
                throw new IllegalArgumentException("Возраст не может быть отрицательным");
            }
            if (age > 150) {
                throw new IllegalArgumentException("Возраст не может быть больше 150");
            }
        }

        private void validateCity() {
            if (city == null) {
                throw new IllegalArgumentException("Город не может быть null");
            }
            if (city.trim().isEmpty()) {
                throw new IllegalArgumentException("Город не может быть пустым");
            }
            if (city.length() < 2) {
                throw new IllegalArgumentException("Название города должно содержать минимум 2 символа");
            }
        }
    }

    // Статический метод для создания Builder
    public static Builder builder() {
        return new Builder();
    }
}