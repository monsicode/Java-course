package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.Validator.nullCheck;

public class TFIDFSimilarityCalculator implements SimilarityCalculator {

    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public TFIDFSimilarityCalculator(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    /*
     * Do not modify!
     */
    @Override
    public double calculateSimilarity(Book first, Book second) {
        Map<String, Double> tfIdfScoresFirst = computeTFIDF(first);
        Map<String, Double> tfIdfScoresSecond = computeTFIDF(second);

        return cosineSimilarity(tfIdfScoresFirst, tfIdfScoresSecond);
    }

    public Map<String, Double> computeTFIDF(Book book) {

        Map<String, Double> tf = computeTF(book);
        Map<String, Double> idf = computeIDF(book);

        return tf.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    double tfValue = entry.getValue();
                    double idfValue = idf.getOrDefault(entry.getKey(), 0.0);
                    return tfValue * idfValue;
                }
            ));

    }

    public Map<String, Double> computeTF(Book book) {
        nullCheck(book, "Book cannot be null in TF");

        List<String> words = tokenizer.tokenize(book.description());

        Map<String, Long> wordCounts = words.stream()
            .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        int totalWords = words.size();

        Map<String, Double> resultTF = new HashMap<>();
        wordCounts.forEach((word, count) -> resultTF.put(word, count / (double) totalWords));

        return resultTF;
    }

    public Map<String, Double> computeIDF(Book book) {
        nullCheck(book, "Book cannot be null in IDF");

        List<String> words = tokenizer.tokenize(book.description());

        long totalBooks = books.size();

        Map<String, Long> booksContainingWord = words.stream()
            .distinct()
            .collect(Collectors.toMap(
                word -> word,
                word -> books.stream()
                    .filter(curBook -> tokenizer.tokenize(curBook.description()).contains(word))
                    .count()
            ));

        return booksContainingWord.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    long count = entry.getValue();
                    if (count == 0) {
                        return 0.0;
                    }
                    return Math.log10((double) totalBooks / count);
                    //return Math.round(idfValue * 100.0) / 100.0;
                }
            ));
    }

    private double cosineSimilarity(Map<String, Double> first, Map<String, Double> second) {
        double magnitudeFirst = magnitude(first.values());
        double magnitudeSecond = magnitude(second.values());

        return dotProduct(first, second) / (magnitudeFirst * magnitudeSecond);
    }

    private double dotProduct(Map<String, Double> first, Map<String, Double> second) {
        Set<String> commonKeys = new HashSet<>(first.keySet());
        commonKeys.retainAll(second.keySet());

        return commonKeys.stream()
            .mapToDouble(word -> first.get(word) * second.get(word))
            .sum();
    }

    private double magnitude(Collection<Double> input) {
        double squaredMagnitude = input.stream()
            .map(v -> v * v)
            .reduce(0.0, Double::sum);

        return Math.sqrt(squaredMagnitude);
    }
}