package ru.edu.app;

final class FinishedState implements StateMenuItem {

    @Override
    public void process(AppContext context) {
        context.closeScanner();
    }

    @Override
    public String getSelectionText() {
        return "Выход";
    }

}
