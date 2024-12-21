package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

public class GenresOverlapSimilarityCalculator implements SimilarityCalculator {

    @Override
    public double calculateSimilarity(Book first, Book second) {
        long commonGenresCount = first.genres().stream()
            .distinct()
            .filter(second.genres()::contains)
            .count();

        long genresFirst = first.genres().stream()
            .distinct()
            .count();

        long genresSecond = second.genres().stream()
            .distinct()
            .count();

        double sizeSmallerSet = Math.min(genresFirst, genresSecond);

        return (commonGenresCount / sizeSmallerSet);
    }

}