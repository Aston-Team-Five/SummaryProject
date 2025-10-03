package ru.edu.app;

class FindElementInCollectionState implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Поиск элемента в коллекции";
    }

    @Override
    public void process(AppContext context) {
        // TODO
        System.out.println("Не реализовано");
        context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
    }
    
}
