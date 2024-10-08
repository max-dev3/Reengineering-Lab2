import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // Method to clean the text by removing non-alphabetic characters and converting to lowercase
    public static String cleanText(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return content.replaceAll("[^A-Za-z ]", " ").toLowerCase(Locale.ENGLISH);
    }

    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();

        // Measure initial memory usage
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();

        LocalDateTime start = LocalDateTime.now();

        // Read and clean the text
        String content = cleanText("src/txt/harry.txt");

        // Split text into words
        String[] words = content.split("\\s+");

        // Calculate word frequencies using Streams
        Map<String, Long> wordFrequencies = Arrays.stream(words)
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        // Sort words by frequency and print the top 30
        wordFrequencies.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(30)
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));

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
