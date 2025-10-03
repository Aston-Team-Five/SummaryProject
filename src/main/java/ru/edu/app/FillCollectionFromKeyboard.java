package ru.edu.app;

public class FillCollectionFromKeyboard implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Заполнить коллекцию вручную с клавиатуры";
    }

    @Override
    public void process(AppContext context) {
        // TODO
        System.out.println("Не реализовано");
        context.setCurrentState(new FillCollectionState(context.getUserChoiceSupplier()));
    }

}
