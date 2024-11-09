import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static HashMap<String, Object> processString(String inputStr, String separator) {
        HashMap<String, Object> result = new HashMap<>();

        // Разделяне на стринга по разделителя
        String[] items = inputStr.split(separator);

        // Преброяване на поднизовете
        result.put("Count", items.length);

        // Префикс
        if (inputStr.startsWith(separator)) {
            result.put("Prefix", "");
        } else {
            result.put("Prefix", items[0]);
        }

        // Сортирани елементи
        String[] sortedItems = Arrays.copyOfRange(items, 1, items.length); // пропускаме първия елемент (префикса)
        Arrays.sort(sortedItems);
        result.put("SortedItems", String.join(" ", sortedItems));

        // Символи на четни индекси
        StringBuilder evenChars = new StringBuilder();
        for (int i = 0; i < inputStr.length(); i++) {
            if (i % 2 == 0) {
                evenChars.append(inputStr.charAt(i));
            }
        }
        result.put("EvenChars", evenChars.toString());

        return result;
    }

    public static void main(String[] args) {
        String inputStr = "abcdefSEPgabcwetSEPsdsSEPdsfgSEPfro";
        String separator = "SEP";

        HashMap<String, Object> result = processString(inputStr, separator);

        System.out.println("Count: " + result.get("Count"));
        System.out.println("Prefix: " + result.get("Prefix"));
        System.out.println("SortedItems: " + result.get("SortedItems"));
        System.out.println("EvenChars: " + result.get("EvenChars"));
    }
}
