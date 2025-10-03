package ru.edu.app.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RandomObjectGenerator {

    private final Class<?> objectType;
    private final Random random;

    public RandomObjectGenerator(Class<?> objectType) {
        this.objectType = objectType;
        random = new Random();
    }

    public Object generateObjectWithRandomValues() {
        Constructor<?>[] declaredConstructors = objectType.getDeclaredConstructors();
        Constructor<?> constructor = declaredConstructors[0];
        return createInstance(constructor);
    }

    private Object createInstance(Constructor<?> constructor) {
        constructor.setAccessible(true);
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = getRandomParameters(parameterTypes);
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    private Object[] getRandomParameters(Class<?>[] parameterTypes) {
        Object result[] = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            result[i] = getRandomValue(parameterTypes[i]);
        }
        return result;
    }

    private Object getRandomValue(Class<?> parameterType) {
        if (parameterType.equals(String.class)) {
            int length = random.nextInt(10) + 5;
            return generateRandomString(length);
        }

        if (parameterType.equals(int.class)) {
            return random.nextInt(100) + 1;
        }

        throw new IllegalArgumentException("Parameter type " + parameterType + " not supported");

    }

    private String generateRandomString(int length) {
        String characterSet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            sb.append(characterSet.charAt(index));
        }
        return sb.toString();
    }
}
