import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//        BiFunc_override override = new BiFunc_override();
        String[] path = {args[0]};
        Integer a = Integer.parseInt(args[1]);
        Integer b = Integer.parseInt(args[2]);
        MyClassLoader clsLoader = new MyClassLoader(path);
        Class<?> cls = clsLoader.loadClass("BiFunc_override");
        BiFunction<Integer, Integer, Integer> arg = (BiFunction<Integer, Integer, Integer>) cls.newInstance();
        int result = arg.apply(a, b);
        System.out.println(result);
    }
}