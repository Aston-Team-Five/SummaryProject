package ru.edu.app;

import java.util.List;
import java.util.function.Supplier;

class ActionsWithCollectionMenuState extends AbstractSimpleMenuState {

    public ActionsWithCollectionMenuState(Supplier<Integer> userChoiceSupplier) {
        super(userChoiceSupplier);
    }

    private final List<StateMenuItem> nextStates = List.of(
            new FinishedState(),
            new PrintCollectionState(),
            new ChooseSortTypeMenuState(getUserChoiceSupplier()),
            new FindElementInCollectionState(),
            new CallCollectionEditorState()
    );


    @Override
    List<StateMenuItem> getNextStates() {
        return nextStates;
    }
}


