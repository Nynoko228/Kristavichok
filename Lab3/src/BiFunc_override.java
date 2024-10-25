import java.math.BigDecimal;
import java.util.function.BiFunction;
public class BiFunc_override implements BiFunction<Integer, Integer, Integer>{
    @Override
    public Integer apply(Integer a, Integer b){
        return a+b;
    }

    public static void main(String[] args) {
        // Создаем экземпляр нашего класса
        BiFunction<Integer, Integer, Integer> arg = new BiFunc_override();
        BigDecimal a = new BigDecimal(565676);
        // Пример использования
        Integer result = arg.apply(a.intValue(), 20);
        System.out.println("Результат сложения: " + result);
    }
}
