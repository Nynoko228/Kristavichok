public class MyClass {
    public static String pole_public = "default";
    private static String pole_private = "default";

    public static void func_public() {
        System.out.println("Privet eto public func");
    }
    private static void func_private() {
        System.out.println("Privet eto private func");
    }

    public static String[] get(){
        return new String[]{pole_public, pole_private};
    }

    public static Float summa(Float a, Float b){
        return (a+b);
    }

    public static Integer summa(Integer a, Integer b){
        return (a+b);
    }

}
