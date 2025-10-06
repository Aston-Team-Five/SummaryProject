package ru.edu.app;

public class CallCollectionEditorState implements StateMenuItem {

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
