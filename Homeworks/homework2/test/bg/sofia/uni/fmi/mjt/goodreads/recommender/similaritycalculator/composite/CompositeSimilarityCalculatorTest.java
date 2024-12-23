package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions.TFIDFSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeSimilarityCalculatorTest {

    private final SimilarityCalculator tfidfCalculator = mock(TFIDFSimilarityCalculator.class);
    private final SimilarityCalculator genreCalculator = mock(GenresOverlapSimilarityCalculator.class);

    private final Book book1 = new Book("id1", "Book One", "Author A",
        "academy superhero club superhero", List.of("Action", "Adventure"), 4.5, 150, "Publisher A");
    private final Book book2 = new Book("id2", "Book Two", "Author B",
        "superhero mission save club", List.of("Action", "Drama"), 4.0, 200, "Publisher B");

    private CompositeSimilarityCalculator compositeCalculator;

    @BeforeEach
    public void setUp() {
        when(tfidfCalculator.calculateSimilarity(book1, book2)).thenReturn(0.1499624235451596);
        when(genreCalculator.calculateSimilarity(book1, book2)).thenReturn(0.5);

        Map<SimilarityCalculator, Double> calculatorsMap = Map.of(
            tfidfCalculator, 0.7,
            genreCalculator, 0.3
        );

        compositeCalculator = new CompositeSimilarityCalculator(calculatorsMap);
    }

    @Test
    void testCalculateSimilarityWithValidData() {
        double expected = 0.1499624235451596 * 0.7 + 0.5 * 0.3;
        double result = compositeCalculator.calculateSimilarity(book1, book2);

        assertEquals(expected, result, "Calculator is not working properly, should return the expected result ");

    }

    @Test
    void testCalculateSimilarityWithDifferentWeightsForCalculators() {
        Map<SimilarityCalculator, Double> calculatorsMap2 = Map.of(
            tfidfCalculator, 0.5,
            genreCalculator, 0.3,
            compositeCalculator, 0.2
        );

        CompositeSimilarityCalculator compositeCalculator2 = new CompositeSimilarityCalculator(calculatorsMap2);

        double expected = (0.1499624235451596 * 0.5) + (0.5 * 0.3) + ((0.1499624235451596 * 0.7 + 0.5 * 0.3) * 0.2);
        double result = compositeCalculator2.calculateSimilarity(book1, book2);

        assertEquals(expected, result, "Calculator should return the expected result");

    }

    @Test
    void testEmptyBooks() {
        Book emptyBook1 = new Book("id4", "b", "Unknown", "", List.of(), 0.0, 0, "Publisher1");
        Book emptyBook2 = new Book("id5", "b2", "Unknown", "", List.of(), 0.0, 0, "Publisher2");

        double similarity = compositeCalculator.calculateSimilarity(emptyBook1, emptyBook2);

        assertEquals(0.0, similarity, "Similarity between empty books should be 0");
    }

    @Test
    void testFirstBookIsNull() {
        assertThrows(IllegalArgumentException.class, () -> compositeCalculator.calculateSimilarity(null, book2));
    }

    @Test
    void testSecondBookIsNull() {
        assertThrows(IllegalArgumentException.class, () -> compositeCalculator.calculateSimilarity(book1, null));
    }

}
