package additional;

import java.util.HashSet;
import java.util.Set;

public class StaticMethods {

    public static boolean checkInt2(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        boolean result = true;
        Set<String> numbers = new HashSet<>();
        numbers.add("0");
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");
        numbers.add("7");
        numbers.add("8");
        numbers.add("9");
        for (int i = 0; i < value.length(); i++) {
            if (!numbers.contains(value.substring(i, i + 1))) {
                result = false;
                System.out.println(value.substring(i, i + 1) + " не является цифрой");
                break;
            }
        }
        return result;
    }

    public static boolean checkInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            System.out.println("не получилось преобразовать в число строку " + value);
            return false;
        }
    }
}