package ru.edu.app;

public class SetCollectionSizeState implements StateMenuItem {

    @Override
    public void process(AppContext context) {
        printStateDescription();
        try {
            Integer choice = context.getUserChoiceSupplier().get();
            if (choice == 0) {
                context.setCurrentState(new FinishedState());
            } else if (choice > 0) {
                context.setCollectionMaxSize(choice);
                context.setCurrentState(new SetObjectTypeState());
            } else {
                printWarning();
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void printStateDescription() {
        System.out.println("\nВведите размер коллекции или 0 для выхода:");
    }


    private void printWarning() {
        System.out.println("Необходимо ввести целое число >= 0");
    }


    @Override
    public String getSelectionText() {
        return "Заполнить коллекцию";
    }
}
