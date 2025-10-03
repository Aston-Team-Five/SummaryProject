package ru.edu.app;

import java.util.List;
import java.util.function.Supplier;

class FillCollectionState extends AbstractSimpleMenuState {

    public FillCollectionState(Supplier<Integer> userChoiceSupplier) {
        super(userChoiceSupplier);
    }

    private static final List<StateMenuItem> NEXT_STATES = List.of(
            new FinishedState(),
            new FillCollectionFromFileState(),
            new FillCollectionFromKeyboard(),
            new FillCollectionWithRandomValues()
    );

    @Override
    List<StateMenuItem> getNextStates() {
        return NEXT_STATES;
    }
}
