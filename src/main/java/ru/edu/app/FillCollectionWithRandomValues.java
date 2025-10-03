package ru.edu.app;

import ru.edu.app.helper.RandomObjectGenerator;

import java.util.Collection;

class FillCollectionWithRandomValues implements StateMenuItem {

    @Override
    public String getSelectionText() {
        return "Заполнить коллекцию случайными значениями";
    }

    @Override
    public void process(AppContext context) {
        fillCollection(context.getCollection(), context.getCollectionMaxSize(), context.getCollectionGenericType());
        context.setCurrentState(new ActionsWithCollectionMenuState(context.getUserChoiceSupplier()));
    }

    private void fillCollection(Collection<Object> collection, int maxSize, Class<?> objectType) {
        RandomObjectGenerator randomObjectGenerator = new RandomObjectGenerator(objectType);
        for (int i = collection.size(); i < maxSize; i++) {
            collection.add(randomObjectGenerator.generateObjectWithRandomValues());
        }
    }

}
