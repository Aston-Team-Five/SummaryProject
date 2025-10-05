package ru.edu.app;

import java.util.List;
import java.util.function.Supplier;

abstract class AbstractSimpleMenuState implements StateSimpleMenu {

    private final Supplier<Integer> userChoiceSupplier;

    protected AbstractSimpleMenuState(Supplier<Integer> userChoiceSupplier) {
        this.userChoiceSupplier = userChoiceSupplier;
    }

    @Override
    public void process(AppContext context) {
        try {
            State nextState = makeChoice();
            context.setCurrentState(nextState);
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public State makeChoice() {
        printEntireMenu();
        Integer choice = userChoiceSupplier.get();
        return nextStateFromChoice(choice);
    }

    protected void printEntireMenu() {
        printMenuDescription();
        List<StateMenuItem> nextStates = getNextStates();
        for (int i = 0; i < nextStates.size(); i++) {
            StateMenuItem nextState = nextStates.get(i);
            System.out.println(i + " - " + nextState.getSelectionText());
        }
    }

    protected void printMenuDescription() {
        System.out.println("\nВыберите действие:");
    }

    private State nextStateFromChoice(Integer choice) {
        List<StateMenuItem> nextStates = getNextStates();
        if (choice >= 0 && choice < nextStates.size()) {
            return nextStates.get(choice);
        }
        throw new IllegalStateException("Не найдено действие с номером " + choice);
    }



    abstract List<StateMenuItem> getNextStates();

    protected final Supplier<Integer> getUserChoiceSupplier() {
        return userChoiceSupplier;
    }

}
