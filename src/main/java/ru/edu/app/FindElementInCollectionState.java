package ru.edu.app;

class FindElementInCollectionState implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "ВЫЗОВ РЕДАКТОРА";
    }

    @Override
    public void process(AppContext context) {

        EditCollectionState editor = new EditCollectionState();
        editor.process(context);

        // После редактирования возвращаемся в меню манипуляций
        context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
    }
}
