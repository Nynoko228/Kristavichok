import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Reflections {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Необходимо передать хотя бы имя класса.");
            return;
        }

        String className = args[0]; // Имя класса
        String methodName = args.length > 1 ? args[1] : null; // Имя метода (опционально)

        try {
            // Получаем объект класса по имени класса
            Class<?> class_name = Class.forName(className);

            if (methodName == null) {
                // Если передано только имя класса, выводим информацию о классе и его методах
                printClassInfo(class_name);
            } else {
                // Если передано имя метода, вызываем метод с переданными аргументами
                Object[] methodArgs = new Object[args.length - 2]; // Аргументы метода (если есть)
                for (int i = 2; i < args.length; i++) {
                    // Для простоты считаем, что все аргументы метода — int
                    methodArgs[i - 2] = Integer.parseInt(args[i]);
                }

                invokeMethod(class_name, methodName, methodArgs);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Класс " + className + " не найден.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для вывода информации о классе и его методах
    private static void printClassInfo(Class<?> class_name) throws IllegalAccessException{
        System.out.println("Класс: " + class_name.getName());
        Method[] methods = class_name.getDeclaredMethods(); // Получаем список методов введённого класса
        for (Method method : methods) {
            System.out.print("Метод: " + method.getName() + " ("); // Выводим название метода
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.print(parameterTypes[i].getSimpleName());
                if (i < parameterTypes.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(") Возвращаемый тип: " + method.getReturnType().getSimpleName());
        }
        Field[] declaredFields = class_name.getDeclaredFields(); // Получаем список полей введённого класса
        for (Field field : declaredFields) {
            System.out.println("Поле: " + field.getName()); // Выводим название метода
        }
    }

    // Метод для вызова метода класса с указанными аргументами
    private static void invokeMethod(Class<?> class_name, String methodName, Object[] args) throws Exception {
        Method[] methods = class_name.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Результат вызова метода "  + method.getName());
            if (method.getName().equals(methodName) && (args[0].getClass() == method.getParameterTypes()[0])) {
                if ((method.getParameterCount() == args.length)) {
                    System.out.println("Результат вызова метода " + args[0].getClass() + " " + method.getParameterTypes()[0]);
                    method.setAccessible(true); // Делаем метод доступным
                    Object instance = class_name.getConstructor().newInstance(); // Создаем экземпляр класса

                    // Проверяем, переданы ли аргументы, иначе просто вызываем метод без аргументов
                    Object result = method.invoke(instance, args.length > 0 ? args : null);
                    Object[] objectArray = (Object[]) result;
                    System.out.println("Результат вызова метода " + methodName + ":");
                    for (Object str : objectArray) {
                        System.out.println(str);
                    }
                    return;
                }
            }
        }
        System.out.println("Метод " + methodName + " не найден или количество аргументов не совпадает.");
    }
}