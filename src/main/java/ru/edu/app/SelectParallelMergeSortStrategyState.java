package ru.edu.app;

import ru.edu.sorts.ParallelMergeSortStrategy;

class SelectParallelMergeSortStrategyState implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Параллельная сортировка слиянием";
    }

    @Override
    public void process(AppContext context) {
        context.setSortStrategy(new ParallelMergeSortStrategy<>());
        context.setCurrentState(new SelectFieldsToSortByState(context.getUserChoiceSupplier(), context));
    }
}
