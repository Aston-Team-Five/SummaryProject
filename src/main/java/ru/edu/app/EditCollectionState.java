package ru.edu.app;

import ru.edu.model.Person;
import ru.edu.util.SimpleLinkedList;
import java.util.*;

class EditCollectionState implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Редактирование коллекции";
    }

    @Override
    public void process(AppContext context) {
        Scanner scanner = new Scanner(System.in);
        Collection<Object> collection = context.getCollection();

        if (collection.isEmpty()) {
            System.out.println("❌ Коллекция пуста! Сначала заполните коллекцию.");
            context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
            return;
        }

        SimpleLinkedList<Object> editableList = new SimpleLinkedList<>(collection);

        boolean continueEditing = true;
        while (continueEditing) {
            System.out.println("\n=== РЕДАКТИРОВАНИЕ КОЛЛЕКЦИИ ===");
            System.out.println("Текущее количество элементов: " + editableList.size());
            displayCollectionPreview(editableList);

            System.out.println("\n--- ВЫБЕРИТЕ ДЕЙСТВИЕ ---");
            System.out.println("1. Просмотреть все элементы");
            System.out.println("2. Добавить элемент");
            System.out.println("3. Редактировать элемент");
            System.out.println("4. Удалить элемент");
            System.out.println("5. Очистить коллекцию");
            System.out.println("6. Сохранить изменения и выйти");
            System.out.println("7. Многопоточный подсчет");
            System.out.println("0. Выйти без сохранения");

            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayAllElements(editableList);
                    break;
                case "2":
                    addElement(scanner, editableList);
                    break;
                case "3":
                    editElement(scanner, editableList);
                    break;
                case "4":
                    removeElement(scanner, editableList);
                    break;
                case "5":
                    clearCollection(editableList);
                    break;
                case "6":
                    saveChanges(context, editableList);
                    continueEditing = false;
                    break;
                case "7":
                    MultiThreadedCountState multiThreadedSearch = new MultiThreadedCountState();
                    multiThreadedSearch.process(context);
                    break;
                case "0":
                    System.out.println("❌ Изменения не сохранены");
                    continueEditing = false;
                    break;
                default:
                    System.out.println("❌ Неверный выбор");
            }
        }

        context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
    }

    private void displayCollectionPreview(SimpleLinkedList<Object> list) {
        if (list.isEmpty()) return;

        System.out.println("\n--- ПРЕВЬЮ КОЛЛЕКЦИИ (первые 5 элементов) ---");
        int displayCount = Math.min(5, list.size());
        for (int i = 0; i < displayCount; i++) {
            Object item = list.get(i);
            if (item instanceof Person) {
                Person person = (Person) item;
                System.out.printf("%d. %s\n", i + 1, person);
            }
        }
        if (list.size() > 5) {
            System.out.println("... и еще " + (list.size() - 5) + " элементов");
        }
    }

    private void displayAllElements(SimpleLinkedList<Object> list) {
        if (list.isEmpty()) {
            System.out.println("❌ Коллекция пуста");
            return;
        }

        System.out.println("\n--- ВСЕ ЭЛЕМЕНТЫ КОЛЛЕКЦИИ ---");
        for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);
            if (item instanceof Person) {
                Person person = (Person) item;
                System.out.printf("%d. %s\n", i + 1, person);
            } else {
                System.out.printf("%d. %s\n", i + 1, item);
            }
        }
    }

    private void addElement(Scanner scanner, SimpleLinkedList<Object> list) {
        System.out.println("\n--- ДОБАВЛЕНИЕ НОВОГО ELEMENT ---");

        try {
            System.out.print("Введите имя: ");
            String name = scanner.nextLine();

            System.out.print("Введите возраст: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите город: ");
            String city = scanner.nextLine();

            Person newPerson = Person.builder()
                    .setName(name)
                    .setAge(age)
                    .setCity(city)
                    .build();

            System.out.println("Выберите позицию для добавления:");
            System.out.println("1. В начало");
            System.out.println("2. В конец");
            System.out.println("3. На определенную позицию");

            String positionChoice = scanner.nextLine().trim();

            switch (positionChoice) {
                case "1":
                    list.add(0, newPerson);
                    System.out.println("✅ Элемент добавлен в начало");
                    break;
                case "2":
                    list.add(newPerson);
                    System.out.println("✅ Элемент добавлен в конец");
                    break;
                case "3":
                    System.out.print("Введите позицию (1-" + (list.size() + 1) + "): ");
                    int index = Integer.parseInt(scanner.nextLine()) - 1;
                    if (index >= 0 && index <= list.size()) {
                        list.add(index, newPerson);
                        System.out.println("✅ Элемент добавлен на позицию " + (index + 1));
                    } else {
                        System.out.println("❌ Неверная позиция");
                    }
                    break;
                default:
                    System.out.println("❌ Неверный выбор, элемент добавлен в конец");
                    list.add(newPerson);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Ошибка создания Person: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("❌ Ошибка при добавлении: " + e.getMessage());
        }
    }

    private void editElement(Scanner scanner, SimpleLinkedList<Object> list) {
        if (list.isEmpty()) {
            System.out.println("❌ Коллекция пуста");
            return;
        }

        System.out.println("\n--- РЕДАКТИРОВАНИЕ ЭЛЕМЕНТА ---");
        displayAllElements(list);

        try {
            System.out.print("Введите номер элемента для редактирования (1-" + list.size() + "): ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index < 0 || index >= list.size()) {
                System.out.println("❌ Неверный номер элемента");
                return;
            }

            Object currentItem = list.get(index);
            if (!(currentItem instanceof Person)) {
                System.out.println("❌ Этот элемент нельзя редактировать (не Person)");
                return;
            }

            Person currentPerson = (Person) currentItem;

            System.out.println("Текущие данные:");
            System.out.println("1. Имя: " + currentPerson.getName());
            System.out.println("2. Возраст: " + currentPerson.getAge());
            System.out.println("3. Город: " + currentPerson.getCity());
            System.out.println("4. Редактировать все поля");
            System.out.println("0. Отмена");

            System.out.print("Выберите поле для редактирования: ");
            String fieldChoice = scanner.nextLine().trim();

            Person editedPerson = null;

            switch (fieldChoice) {
                case "1":
                    System.out.print("Введите новое имя: ");
                    String newName = scanner.nextLine();
                    editedPerson = Person.builder()
                            .setName(newName)
                            .setAge(currentPerson.getAge())
                            .setCity(currentPerson.getCity())
                            .build();
                    break;
                case "2":
                    System.out.print("Введите новый возраст: ");
                    int newAge = Integer.parseInt(scanner.nextLine());
                    editedPerson = Person.builder()
                            .setName(currentPerson.getName())
                            .setAge(newAge)
                            .setCity(currentPerson.getCity())
                            .build();
                    break;
                case "3":
                    System.out.print("Введите новый город: ");
                    String newCity = scanner.nextLine();
                    editedPerson = Person.builder()
                            .setName(currentPerson.getName())
                            .setAge(currentPerson.getAge())
                            .setCity(newCity)
                            .build();
                    break;
                case "4":
                    System.out.print("Введите новое имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите новый возраст: ");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.print("Введите новый город: ");
                    String city = scanner.nextLine();
                    editedPerson = Person.builder()
                            .setName(name)
                            .setAge(age)
                            .setCity(city)
                            .build();
                    break;
                case "0":
                    System.out.println("❌ Редактирование отменено");
                    return;
                default:
                    System.out.println("❌ Неверный выбор");
                    return;
            }

            if (editedPerson != null) {
                list.set(index, editedPerson);
                System.out.println("✅ Элемент успешно обновлен");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Ошибка валидации: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Ошибка при редактировании: " + e.getMessage());
        }
    }

    private void removeElement(Scanner scanner, SimpleLinkedList<Object> list) {
        if (list.isEmpty()) {
            System.out.println("❌ Коллекция пуста");
            return;
        }

        System.out.println("\n--- УДАЛЕНИЕ ЭЛЕМЕНТА ---");
        displayAllElements(list);

        try {
            System.out.print("Введите номер элемента для удаления (1-" + list.size() + "): ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index < 0 || index >= list.size()) {
                System.out.println("❌ Неверный номер элемента");
                return;
            }

            Object removedItem = list.remove(index);
            System.out.println("✅ Элемент удален: " + removedItem);

        } catch (NumberFormatException e) {
            System.out.println("❌ Неверный формат числа");
        } catch (Exception e) {
            System.out.println("❌ Ошибка при удалении: " + e.getMessage());
        }
    }

    private void clearCollection(SimpleLinkedList<Object> list) {
        if (list.isEmpty()) {
            System.out.println("❌ Коллекция уже пуста");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("❌ Вы уверены, что хотите очистить всю коллекцию? (y/N): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("y") || confirmation.equals("yes")) {
            list.clear();
            System.out.println("✅ Коллекция очищена");
        } else {
            System.out.println("❌ Очистка отменена");
        }
    }

    private void saveChanges(AppContext context, SimpleLinkedList<Object> editedList) {
        Collection<Object> originalCollection = context.getCollection();
        originalCollection.clear();
        originalCollection.addAll(editedList);
        System.out.println("✅ Изменения сохранены");
    }
}