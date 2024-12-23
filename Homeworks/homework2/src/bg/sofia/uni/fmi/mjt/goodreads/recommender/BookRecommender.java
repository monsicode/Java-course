package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.Validator.nullCheck;

public class BookRecommender implements BookRecommenderAPI {

    private final Set<Book> initialBooks;
    private final SimilarityCalculator calculator;

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {
        this.initialBooks = initialBooks;
        this.calculator = calculator;
    }

    @Override
    public SortedMap<Book, Double> recommendBooks(Book origin, int maxN) {
        nullCheck(origin, "Origin book cannot be null");

        if (maxN <= 0) {
            throw new IllegalArgumentException("MaxN cannot be smaller or equal to 0");
        }

        SortedMap<Book, Double> allBooksSorted = initialBooks.stream()
            .collect(Collectors.toMap(
                book -> book,
                book -> calculator.calculateSimilarity(origin, book),
                (existing, _ ) -> existing,
                () -> new TreeMap<>(Comparator.comparingDouble(
                    (Book book) -> calculator.calculateSimilarity(origin, book)
                ).reversed())
            ));

        return allBooksSorted.entrySet().stream()
            .limit(maxN)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (existing, _ ) -> existing,
                () -> new TreeMap<>(Comparator.comparingDouble(
                    (Book book) -> calculator.calculateSimilarity(origin, book)
                ).reversed())
            ));
    }
}