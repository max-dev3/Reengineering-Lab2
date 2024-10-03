import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class UnoptimizedWordCounter {

    public static String cleanText(String url) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(url)));
        content = content.replaceAll("[^A-Za-z ]", " ").toLowerCase(Locale.ROOT);
        return content;
    }

    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();

        // Measure initial memory usage
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();

        LocalDateTime start = LocalDateTime.now();

        String content = new String(Files.readAllBytes(Paths.get("src/txt/harry.txt")));
        content = content.replaceAll("[^A-Za-z ]", " ").toLowerCase(Locale.ROOT);
        String[] words = content.split(" +");
        Arrays.sort(words);

        String distinctString = " ";
        for (String word : words) {
            if (!distinctString.contains(word)) {
                distinctString += word + " ";
            }
        }

        String[] distincts = distinctString.split(" ");
        int[] freq = new int[distincts.length];

        for (int i = 0; i < distincts.length; i++) {
            int count = 0;
            for (String word : words) {
                if (distincts[i].equals(word)) {
                    count++;
                }
            }
            freq[i] = count;
        }

        for (int i = 0; i < distincts.length; i++) {
            distincts[i] += " " + freq[i];
        }

        Arrays.sort(distincts, Comparator.comparing(str -> Integer.valueOf(str.replaceAll("[^0-9]", ""))));
        for (int i = 0; i < 30; i++) {
            System.out.println(distincts[distincts.length - 1 - i]);
        }

        LocalDateTime finish = LocalDateTime.now();

        // Measure memory usage after execution
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();

        // Output time taken for the process
        System.out.println("------");
        System.out.println(ChronoUnit.MILLIS.between(start, finish) + " ms");

        // Output memory usage before and after
        System.out.println("Memory used before: " + beforeMemory / (1024 * 1024) + " MB");
        System.out.println("Memory used after: " + afterMemory / (1024 * 1024) + " MB");
        System.out.println("Memory increase: " + (afterMemory - beforeMemory) / (1024 * 1024) + " MB");
    }
}
