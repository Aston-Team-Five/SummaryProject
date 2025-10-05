package ru.edu.app.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class RandomObjectGenerator {

    private final Class<?> objectType;
    private final Random random;

    public RandomObjectGenerator(Class<?> objectType) {
        this.objectType = objectType;
        random = new Random();
    }

    public Object generateObjectWithRandomValues() {
        // 🔥 ПЕРВОЕ: проверяем есть ли метод builder()
        try {
            Method builderMethod = objectType.getMethod("builder");
            if (builderMethod != null) {
                return createObjectViaBuilder();
            }
        } catch (NoSuchMethodException e) {
            // Builder метода нет - используем старую логику
        }

        // 🔥 ВТОРОЕ: старая логика для обычных классов
        Constructor<?>[] declaredConstructors = objectType.getDeclaredConstructors();

        // Ищем конструктор без параметров или с минимальным количеством параметров
        Constructor<?> constructor = findSuitableConstructor(declaredConstructors);

        if (constructor == null) {
            throw new IllegalStateException("No suitable constructor found for " + objectType);
        }

        return createInstance(constructor);
    }

    // 🔥 НОВЫЙ МЕТОД: создание через Builder
    private Object createObjectViaBuilder() {
        if (objectType.equals(ru.edu.model.Person.class)) {
            try {
                // Создаем Person через Builder со случайными данными
                return ru.edu.model.Person.builder()
                        .setName(generateRandomString(6))
                        .setAge(random.nextInt(80) + 18) // возраст 18-97
                        .setCity(generateRandomString(8))
                        .build();
            } catch (IllegalArgumentException e) {
                // Если валидация не прошла, пробуем с другими данными
                return createObjectViaBuilder(); // рекурсивно пробуем снова
            }
        }

        // Для других классов с Builder можно добавить аналогичную логику
        throw new UnsupportedOperationException("Builder not yet supported for: " + objectType);
    }

    // 🔥 УЛУЧШЕНИЕ: ищем подходящий конструктор
    private Constructor<?> findSuitableConstructor(Constructor<?>[] constructors) {
        // Сначала ищем конструктор без параметров
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return constructor;
            }
        }

        // Ищем конструктор с минимальным количеством параметров
        Constructor<?> bestConstructor = null;
        int minParams = Integer.MAX_VALUE;

        for (Constructor<?> constructor : constructors) {
            int paramCount = constructor.getParameterCount();
            if (paramCount < minParams && !isBuilderConstructor(constructor)) {
                bestConstructor = constructor;
                minParams = paramCount;
            }
        }

        return bestConstructor;
    }

    // 🔥 НОВЫЙ МЕТОД: проверяем не Builder ли это конструктор
    private boolean isBuilderConstructor(Constructor<?> constructor) {
        Class<?>[] paramTypes = constructor.getParameterTypes();
        if (paramTypes.length == 1) {
            Class<?> paramType = paramTypes[0];
            // Проверяем не является ли параметр Builder'ом
            return paramType.getSimpleName().equals("Builder") &&
                    paramType.getEnclosingClass() != null &&
                    paramType.getEnclosingClass().equals(objectType);
        }
        return false;
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

        if (parameterType.equals(int.class) || parameterType.equals(Integer.class)) {
            return random.nextInt(100) + 1;
        }

        if (parameterType.equals(long.class) || parameterType.equals(Long.class)) {
            return random.nextLong();
        }

        if (parameterType.equals(double.class) || parameterType.equals(Double.class)) {
            return random.nextDouble();
        }

        if (parameterType.equals(boolean.class) || parameterType.equals(Boolean.class)) {
            return random.nextBoolean();
        }

        throw new IllegalArgumentException("Parameter type " + parameterType + " not supported");
    }

    private String generateRandomString(int length) {
        String characterSet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        StringBuilder sb = new StringBuilder();

        // Первая буква заглавная
        if (length > 0) {
            int index = random.nextInt(characterSet.length());
            sb.append(Character.toUpperCase(characterSet.charAt(index)));
        }

        // Остальные буквы
        for (int i = 1; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            sb.append(characterSet.charAt(index));
        }
        return sb.toString();
    }
}
