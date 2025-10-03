package ru.edu.app;

import ru.edu.model.Box;
import ru.edu.model.Person;
import ru.edu.model.Table;

import java.util.List;

class SetObjectTypeState implements State {

    private static final List<Class<?>> OBJECT_TYPES = List.of(Box.class, Person.class, Table.class);

    @Override
    public void process(AppContext context) {
        printStateDescription();
        try {
            int choice = context.getUserChoiceSupplier().get();
            if (choice == 0) {
                context.setCurrentState(new FinishedState());
            } else if (choice > 0 && choice <= OBJECT_TYPES.size()) {
                context.setObjectType(OBJECT_TYPES.get(choice - 1));
                context.setCurrentState(new FillCollectionState(context.getUserChoiceSupplier()));

            } else {
                System.out.println("Неверный выбор");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printStateDescription() {
        System.out.println("\nВыберите тип объекта или 0 для выхода:");
        for (int i = 0; i < OBJECT_TYPES.size(); i++) {
            System.out.println((i + 1) + " - " + OBJECT_TYPES.get(i).getName());
        }
    }
}
