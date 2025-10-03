package ru.edu.app;

class FillCollectionFromFileState implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Заполнить коллекцию из файла";
    }

    @Override
    public void process(AppContext context) {
        // TODO
        System.out.println("Не реализовано");
        context.setCurrentState(new FillCollectionState(context.getUserChoiceSupplier()));
    }
}
