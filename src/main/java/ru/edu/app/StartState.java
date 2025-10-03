package ru.edu.app;

import java.util.List;
import java.util.function.Supplier;

final class StartState extends AbstractSimpleMenuState {

    private static final List<StateMenuItem> NEXT_STATES = List.of(
            new FinishedState(),
            new SetCollectionSizeState());

    public StartState(Supplier<Integer> userChoiceSupplier) {
        super(userChoiceSupplier);
    }

    @Override
    protected List<StateMenuItem> getNextStates() {
        return NEXT_STATES;
    }
}
