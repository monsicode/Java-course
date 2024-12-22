package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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

        return initialBooks.stream()
            .limit(maxN)
            .collect(Collectors.toMap(
                book -> book,
                book -> calculator.calculateSimilarity(origin, book),
                (existing, replacement) -> existing,
                () -> new TreeMap<>(Comparator.comparingDouble(
                    (Book book) -> calculator.calculateSimilarity(origin, book)
                ).reversed())
            ));
    }

    public static void main(String[] args) {
        Book book1 = new Book("id1", "Title1", "Author1", "Description1",
            List.of("Genre1", "Genre2"), 4.5, 10, "Publisher1");
        Book book2 = new Book("id2", "Title2", "Author2", "Description2",
            List.of("Genre2", "Genre3"), 3.8, 8, "Publisher2");
        Book book3 = new Book("id3", "Title3", "Author3", "Description3",
            List.of("Genre1", "Genre3"), 4.2, 12, "Publisher3");
        Book originBook = new Book("id4", "Origin", "Author4", "DescriptionOrigin",
            List.of("Genre1", "Genre4"), 4.0, 15, "Publisher4");

        Set<Book> books = new HashSet<>(Set.of(book1, book2, book3));

        SimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

        BookRecommender recommender = new BookRecommender(books, calculator);

        int maxN = 2;
        SortedMap<Book, Double> recommendations = recommender.recommendBooks(originBook, maxN);

        recommendations.forEach((book, similarity) -> {
            System.out.println("Book: " + book.title() + ", Similarity: " + similarity);
        });

    }
}