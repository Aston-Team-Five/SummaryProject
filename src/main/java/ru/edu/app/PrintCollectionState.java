package ru.edu.app;

import java.util.Collection;

public class PrintCollectionState implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Вывести коллекцию на экран";
    }

    @Override
    public void process(AppContext context) {
        printCollection(context.getCollection());
        context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
    }

    private void printCollection(Collection<Object> collection) {
        collection.forEach(System.out::println);
    }

}
