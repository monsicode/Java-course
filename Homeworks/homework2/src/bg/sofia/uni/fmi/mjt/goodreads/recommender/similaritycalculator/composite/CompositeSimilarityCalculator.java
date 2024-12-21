package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions.TFIDFSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompositeSimilarityCalculator implements SimilarityCalculator {

    private final Map<SimilarityCalculator, Double> similarityCalculatorMap;

    public CompositeSimilarityCalculator(Map<SimilarityCalculator, Double> similarityCalculatorMap) {
        this.similarityCalculatorMap = similarityCalculatorMap;
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {
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

    public static void main(String[] args) {
        try (Reader stopwordsReader = new FileReader("stopwords.txt")) {
            TextTokenizer tokenizer = new TextTokenizer(stopwordsReader);

            // Създаваме книги за тестване
            Book book1 = new Book("id1", "Book One", "Author A",
                "academy superhero club superhero", List.of("Action", "Adventure"), 4.5, 150, "Publisher A");
            Book book2 = new Book("id2", "Book Two", "Author B",
                "superhero mission save club", List.of("Action", "Drama"), 4.0, 200, "Publisher B");
            Book book3 = new Book("id3", "Book Three", "Author C",
                "crime murder mystery club", List.of("Mystery", "Drama"), 3.8, 100, "Publisher C");

            Set<Book> books = Set.of(book1, book2, book3);

            // Създаваме калкулаторите
            SimilarityCalculator tfidfCalculator = new TFIDFSimilarityCalculator(books, tokenizer);
            SimilarityCalculator genreOverlapCalculator = new GenresOverlapSimilarityCalculator();

            // Дефинираме тежестите на калкулаторите
            Map<SimilarityCalculator, Double> calculatorsMap = Map.of(
                tfidfCalculator, 0.7,
                genreOverlapCalculator, 0.3
            );

            // Създаваме CompositeSimilarityCalculator
            CompositeSimilarityCalculator compositeCalculator = new CompositeSimilarityCalculator(calculatorsMap);

            Map<SimilarityCalculator, Double> calculatorsMap2 = Map.of(
                tfidfCalculator, 0.7,
                genreOverlapCalculator, 0.3,
                compositeCalculator, 0.2
            );

            CompositeSimilarityCalculator compositeCalculator2 = new CompositeSimilarityCalculator(calculatorsMap2);

            // Тествай изчисляването на сходство между две книги
            double similarity = compositeCalculator2.calculateSimilarity(book1, book2);

            System.out.println("Composite similarity between book1 and book2: " + similarity);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}