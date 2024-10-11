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

    public static String[] summa(float a, float b){
        return new String[]{""+(a+b),};
    }

    public static String[] summa(int a, int b){
        return new String[]{""+(a+b),};
    }

}
