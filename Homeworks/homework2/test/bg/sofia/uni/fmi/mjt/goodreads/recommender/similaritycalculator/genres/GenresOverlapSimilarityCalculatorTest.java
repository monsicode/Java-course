package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenresOverlapSimilarityCalculatorTest {

    @Test
    public void testCalculateSimilarityWhenNoCommonGenres() {

        Book firstBook = new Book("id1", "Title1", "Author1", "Description1",
            List.of("Fantasy", "Adventure"), 4.5, 10, "Publisher1");
        Book secondBook = new Book("id2", "Title2", "Author2", "Description2",
            List.of("Science Fiction", "Horror"), 3.8, 8, "Publisher2");

        SimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

        double similarity = calculator.calculateSimilarity(firstBook, secondBook);

        assertEquals(0.0, similarity, 0.001, "");
    }

    @Test
    public void testCalculateSimilarityWithOneCommonGenres() {

        Book firstBook = new Book("id1", "Title1", "Author1", "Description1",
            List.of("Horror", "Adventure"), 4.5, 10, "Publisher1");
        Book secondBook = new Book("id2", "Title2", "Author2", "Description2",
            List.of("Science Fiction", "Horror"), 3.8, 8, "Publisher2");

        SimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

        double similarity = calculator.calculateSimilarity(firstBook, secondBook);

        assertEquals(0.5, similarity, 0.001, "");
    }

    @Test
    public void testCalculateSimilarityWhenOneBookHasNoGenres() {

        Book firstBook = new Book("id1", "Title1", "Author1", "Description1",
            List.of("Horror", "Adventure"), 4.5, 10, "Publisher1");
        Book secondBook = new Book("id2", "Title2", "Author2", "Description2",
            List.of(), 3.8, 8, "Publisher2");

        SimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

        double similarity = calculator.calculateSimilarity(firstBook, secondBook);

        assertEquals(0.0, similarity, 0.001, "");
    }

    @Test
    public void testCalculateSimilarityWithDifferentCountOfGenres() {

        Book firstBook = new Book("id1", "Title1", "Author1", "Description1",
            List.of("Horror", "Adventure"), 4.5, 10, "Publisher1");
        Book secondBook = new Book("id2", "Title2", "Author2", "Description2",
            List.of("Fantasy", "Horror", "Si-fy", "Comedy"), 3.8, 8, "Publisher2");

        SimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

        double similarity = calculator.calculateSimilarity(firstBook, secondBook);

        assertEquals(0.5, similarity, 0.001, "");
    }

    @Test
    public void testCalculateSimilarityWithDifferentCountOfGenresWithTwoSimilarGenres() {

        Book firstBook = new Book("id1", "Title1", "Author1", "Description1",
            List.of("Horror", "Fantasy"), 4.5, 10, "Publisher1");
        Book secondBook = new Book("id2", "Title2", "Author2", "Description2",
            List.of("Fantasy", "Horror", "Si-fy", "Comedy"), 3.8, 8, "Publisher2");

        SimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

        double similarity = calculator.calculateSimilarity(secondBook, firstBook);

        assertEquals(1.0, similarity, 0.001, "");
    }


}
