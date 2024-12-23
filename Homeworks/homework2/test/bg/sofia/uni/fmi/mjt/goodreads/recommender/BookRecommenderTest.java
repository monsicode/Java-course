package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookRecommenderTest {

    @Mock
    private SimilarityCalculator calculator;

    @InjectMocks
    private BookRecommender recommender;

    private final Book book1 = new Book("id1", "title1", "Tom", "academy is a the superhero club superhero",
        List.of("Action", "Horror", "Young Adult"), 3.4, 3, "baba");
    private final Book book2 = new Book("id2", "title2", "Tom", "academy superhero club superhero",
        List.of("Science Fiction", "Fantasy", "Adventure"), 3.4, 3, "baba");
    private final Book book3 = new Book("id3", "title3", "Greg", "superhero mission save club",
        List.of("Young Adult", "Horror"), 3.4, 3, "baba");
    private final Book book4 = new Book("id4", "title4", "Anna", "crime murder mystery club",
        List.of("Fantasy", "Criminal"), 3.4, 3, "baba");

    @BeforeEach
    void setUp() {
        recommender = new BookRecommender(Set.of(book1, book2, book3, book4), calculator);
    }

    @Test
    void testRecommendBooksNullBook() {
        assertThrows(IllegalArgumentException.class, () -> recommender.recommendBooks(null, 1),
            "Book cannot be null");
    }

    @Test
    void testRecommendBooksInvalidMaxCountOfBooks() {
        assertThrows(IllegalArgumentException.class, () -> recommender.recommendBooks(book1, -1),
            "MaxN cannot be less than 0");
    }

    @Test
    void testRecommendBooks() {
        when(calculator.calculateSimilarity(book1, book2)).thenReturn(0.5);
        when(calculator.calculateSimilarity(book1, book3)).thenReturn(0.7);
        when(calculator.calculateSimilarity(book1, book4)).thenReturn(0.3);
        when(calculator.calculateSimilarity(book1, book1)).thenReturn(0.2);

        Map<Book, Double> expected = new LinkedHashMap<>();

        expected.put(book3, 0.7);
        expected.put(book2, 0.5);
        expected.put(book4, 0.3);
        expected.put(book1, 0.2);

        assertEquals(expected, recommender.recommendBooks(book1, 4),
            "No valid recommended books");
    }

    @Test
    void testRecommendBooksWithLowerLimit() {
        when(calculator.calculateSimilarity(book1, book2)).thenReturn(0.5);
        when(calculator.calculateSimilarity(book1, book3)).thenReturn(0.7);
        when(calculator.calculateSimilarity(book1, book4)).thenReturn(0.3);
        when(calculator.calculateSimilarity(book1, book1)).thenReturn(0.2);

        Map<Book, Double> expected = new LinkedHashMap<>();
        expected.put(book3, 0.7);
        expected.put(book2, 0.5);

        assertEquals(expected, recommender.recommendBooks(book1, 2),
            "No valid recommended books");
    }

    @Test
    void testRecommendBooksWithOneRecommendation() {
        when(calculator.calculateSimilarity(book1, book2)).thenReturn(0.1);
        when(calculator.calculateSimilarity(book1, book3)).thenReturn(0.0);
        when(calculator.calculateSimilarity(book1, book4)).thenReturn(0.0);
        when(calculator.calculateSimilarity(book1, book1)).thenReturn(0.0);

        Map<Book, Double> expected = Map.of(book2, 0.1);

        assertEquals(expected, recommender.recommendBooks(book1, 1),
            "The result should be an empty map when no books are recommended");
    }
}
