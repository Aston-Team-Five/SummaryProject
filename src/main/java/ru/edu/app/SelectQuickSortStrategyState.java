package ru.edu.app;

import ru.edu.sorts.QuickSortStrategy;

import java.io.Serializable;

class SelectQuickSortStrategyState implements StateMenuItem, Serializable {

    @Override
    public String getSelectionText() {
        return "Быстрая сортировка";
    }

    @Override
    public void process(AppContext context) {
        context.setSortStrategy(new QuickSortStrategy<>());
        context.setCurrentState(new SelectFieldsToSortByState(context.getUserChoiceSupplier(), context));
    }
}
