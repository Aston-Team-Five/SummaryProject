package ru.edu.app;

import ru.edu.sorts.MergeSortStrategy;

class SelectMergeSortStrategyState implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Сортировка слиянием";
    }

    @Override
    public void process(AppContext context) {
        context.setSortStrategy(new MergeSortStrategy<>());
        context.setCurrentState(new SelectFieldsToSortByState(context.getUserChoiceSupplier(), context));
    }
}
