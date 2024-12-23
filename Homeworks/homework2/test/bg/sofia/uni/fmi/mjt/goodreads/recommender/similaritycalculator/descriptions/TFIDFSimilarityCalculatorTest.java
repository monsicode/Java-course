package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.log10;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TFIDFSimilarityCalculatorTest {

    private final TextTokenizer tokenizer = mock(TextTokenizer.class);

    private final Book book1 = new Book("id", "title", "Tom", "academy is a the superhero club superhero",
        List.of("Action", "Others"), 3.4, 3, "baba");
    private final Book bookX = new Book("id", "title", "Tom", "academy superhero club superhero",
        List.of(new String[] {"Action", "Others"}), 3.4, 3, "baba");
    private final Book bookY = new Book("id2", "title", "Tom", "superhero mission save club",
        List.of(new String[] {"Action", "Others"}), 3.4, 3, "baba");
    private final Book bookZ = new Book("id3", "title", "Tom", "crime murder mystery club",
        List.of(new String[] {"Action", "Others"}), 3.4, 3, "baba");

    @Test
    void testComputeTF() {
        when(tokenizer.tokenize("academy is a the superhero club superhero"))
            .thenReturn(List.of("academy", "superhero", "club", "superhero"));

        TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(Set.of(), tokenizer);

        Map<String, Double> expected = Map.of(
            "academy", 0.25,
            "club", 0.25,
            "superhero", 0.5
        );

        assertEquals(expected, calculator.computeTF(book1), "Should return as expected");
    }

    @Test
    void testComputeIDF() {
        when(tokenizer.tokenize("academy superhero club superhero"))
            .thenReturn(List.of("academy", "superhero", "club", "superhero"));
        when(tokenizer.tokenize("superhero mission save club"))
            .thenReturn(List.of("superhero", "mission", "save", "club"));
        when(tokenizer.tokenize("crime murder mystery club"))
            .thenReturn(List.of("crime", "murder", "mystery", "club"));

        Set<Book> books = Set.of(bookX, bookY, bookZ);

        TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(books, tokenizer);

        Map<String, Double> expectedToContain = Map.of(
            "academy", 0.47712125471966,
            "superhero", 0.17609125905568,
            "club", 0.0
        );

        for (Map.Entry<String, Double> entry : expectedToContain.entrySet()) {
            String word = entry.getKey();
            double expected = entry.getValue();
            double actual = calculator.computeIDF(bookX).get(word);
            assertEquals(expected, actual, 0.0001, "IDF for word " + word + " not as expected!");
        }
    }

    @Test
    void testComputeIDFWhenBookIsNotInSet() {
        when(tokenizer.tokenize("academy superhero club superhero"))
            .thenReturn(List.of("academy", "superhero", "club", "superhero"));
        when(tokenizer.tokenize("superhero mission save club"))
            .thenReturn(List.of("superhero", "mission", "save", "club"));
        when(tokenizer.tokenize("crime murder mystery club"))
            .thenReturn(List.of("crime", "murder", "mystery", "club"));

        Set<Book> books = Set.of(bookY, bookZ);

        TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(books, tokenizer);

        Map<String, Double> expectedToContain = Map.of(
            "academy", 0.0,
            "superhero", log10(2 / 1),
            "club", log10(2 / 2)
        );

        for (Map.Entry<String, Double> entry : expectedToContain.entrySet()) {
            String word = entry.getKey();
            double expected = entry.getValue();
            double actual = calculator.computeIDF(bookX).get(word);
            assertEquals(expected, actual, 0.0001, "IDF for word " + word + " not as expected!");
        }
    }

    @Test
    void testComputeTFIDF() {
        when(tokenizer.tokenize("academy superhero club superhero"))
            .thenReturn(List.of("academy", "superhero", "club", "superhero"));
        when(tokenizer.tokenize("superhero mission save club"))
            .thenReturn(List.of("superhero", "mission", "save", "club"));
        when(tokenizer.tokenize("crime murder mystery club"))
            .thenReturn(List.of("crime", "murder", "mystery", "club"));

        Set<Book> books = Set.of(bookX, bookY, bookZ);

        TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(books, tokenizer);

        Map<String, Double> expectedToContain = Map.of(
            "academy", 0.47712125471966 * 0.25,
            "superhero", 0.17609125905568 * 0.5,
            "club", 0.0 * 0.25
        );

        for (Map.Entry<String, Double> entry : expectedToContain.entrySet()) {
            String word = entry.getKey();
            double expected = entry.getValue();
            double actual = calculator.computeTFIDF(bookX).get(word);
            assertEquals(expected, actual, 0.0001, "IDF for word " + word + " not as expected!");
        }
    }

    @Test
    void testComputeTFWithNull() {

        TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(Set.of(), tokenizer);
        assertThrows(IllegalArgumentException.class, () -> calculator.computeTF(null), "Book cannot be null in Tf");

    }

    @Test
    void testComputeIDFWithNull() {

        TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(Set.of(), tokenizer);
        assertThrows(IllegalArgumentException.class, () -> calculator.computeIDF(null), "Book cannot be null in IDF");

    }

    @Test
    void testComputeTFIDFWithNull() {

        TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(Set.of(), tokenizer);
        assertThrows(IllegalArgumentException.class, () -> calculator.computeTFIDF(null),
            "Book cannot be null in TfIDF");
    }


    @Test
    void testCalculateSimilarityWithIdenticalBooks() {
        Book book1 = new Book("id1", "title1", "author1", "space travel adventure",
            List.of("Sci-Fi"), 4.5, 10, "publisher");
        Book book2 = new Book("id2", "title2", "author2", "crime mystery drama",
            List.of("Thriller"), 4.0, 8, "publisher");

        when(tokenizer.tokenize("space travel adventure"))
            .thenReturn(List.of("space", "travel", "adventure"));
        when(tokenizer.tokenize("crime mystery drama"))
            .thenReturn(List.of("crime", "mystery", "drama"));

        TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(Set.of(book1, book2), tokenizer);

        double expected = 0.0;
        double actual = calculator.calculateSimilarity(book1, book2);

        assertEquals(expected, actual, 0.0001, "Cosine Similarity for books that are not similar 0.0");
    }

}
