package ru.edu.app;

import java.util.List;
import java.util.function.Supplier;

class ChooseSortTypeMenuState extends AbstractSimpleMenuState implements StateMenuItem {

    private static final List<StateMenuItem> NEXT_STATES = List.of(
            new SelectMergeSortStrategyState(),
            new SelectParallelMergeSortStrategyState(),
            new SelectQuickSortStrategyState()
    );

    public ChooseSortTypeMenuState(Supplier<Integer> userChoiceSupplier) {
        super(userChoiceSupplier);
    }

    @Override
    public String getSelectionText() {
        return "Сортировка коллекции";
    }

    @Override
    protected void printMenuDescription() {
        System.out.println("\nВыберите тип сортировки:");
    }

    @Override
    List<StateMenuItem> getNextStates() {
        return NEXT_STATES;
    }

}
