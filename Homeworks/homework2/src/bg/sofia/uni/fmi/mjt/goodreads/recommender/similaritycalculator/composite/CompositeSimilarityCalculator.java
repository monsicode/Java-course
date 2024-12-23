package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Map;

import static bg.sofia.uni.fmi.mjt.goodreads.Validator.nullCheck;

public class CompositeSimilarityCalculator implements SimilarityCalculator {

    private final Map<SimilarityCalculator, Double> similarityCalculatorMap;

    public CompositeSimilarityCalculator(Map<SimilarityCalculator, Double> similarityCalculatorMap) {
        this.similarityCalculatorMap = similarityCalculatorMap;
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {
        nullCheck(first, "First book cannot be empty");
        nullCheck(second, "Second book cannot be empty");

        return similarityCalculatorMap.entrySet().stream()
            .mapToDouble(
                entry -> {
                    SimilarityCalculator calculator = entry.getKey();
                    double resultCalculator = calculator.calculateSimilarity(first, second);
                    double weight = entry.getValue();

                    return resultCalculator * weight;
                })
            .sum();
    }

}