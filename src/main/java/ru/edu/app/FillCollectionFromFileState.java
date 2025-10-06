package ru.edu.app;

import ru.edu.data.FillArray;

class FillCollectionFromFileState implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Заполнить коллекцию из файла";
    }

    @Override
    public void process(AppContext context) {
        FillArray fillArray = new FillArray();
        fillArray.FillArrayInFile(); // Используем стандартный путь к файлу

        context.getCollection().addAll(fillArray.getPersonList());
        System.out.println("✅ Загружено " + fillArray.getPersonList().size() + " записей из файла");

        // 🔥 ПЕРЕНАПРАВЛЯЕМ К МАНИПУЛЯЦИЯМ С КОЛЛЕКЦИЕЙ
        context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
    }
}
