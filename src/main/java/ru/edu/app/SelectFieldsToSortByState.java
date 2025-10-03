package ru.edu.app;

import ru.edu.util.SimpleLinkedList;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

class SelectFieldsToSortByState extends AbstractSimpleMenuState implements StateSimpleMenu {

    private final AppContext appContext;
    private List<StateMenuItem> nextStates;

    public SelectFieldsToSortByState(Supplier<Integer> userChoiceSupplier, AppContext appContext) {
        super(userChoiceSupplier);
        this.appContext = appContext;
        fillNextStates();

    }

    private void fillNextStates() {
        nextStates = new SimpleLinkedList<>();
        nextStates.add(new FinishedState());
        nextStates.add(new StateMenuItem() {
            @Override
            public String getSelectionText() {
                return "Вернуться в меню действий с коллекцией";
            }

            @Override
            public void process(AppContext context) {
                context.setSortStrategy(null);
                context.getFieldNamesToSortBy().clear();
                context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
            }
        });
        nextStates.add(new StateMenuItem() {
            @Override
            public String getSelectionText() {
                return "Выполнить сортировку";
            }

            @Override
            public void process(AppContext context) {
                context.setCurrentState(new DoSortState());
            }
        });
        Field[] declaredFields = appContext.getCollectionGenericType().getDeclaredFields();
        SelectFieldsToSortByState selectFieldsToSortByState = this;
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            String fieldName = declaredFields[i].getName();
            nextStates.add(new StateMenuItem() {
                @Override
                public String getSelectionText() {
                    return "Сортировка по полю '" + fieldName + "'";
                }

                @Override
                public void process(AppContext context) {
                    context.getFieldNamesToSortBy().add(fieldName);
                    context.setCurrentState(selectFieldsToSortByState);
                }
            });
        }
    }

    @Override
    List<StateMenuItem> getNextStates() {
        if (nextStates == null) {
            fillNextStates();
        }
        return nextStates;
    }

    @Override
    protected void printMenuDescription() {
        System.out.println("\nСейчас выбраны поля для сортировки: [" + appContext.getFieldNamesToSortByAsString() + ']');
        System.out.println("Выберите поля для сортировки либо действие:");
    }

}
