package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.recommender.BookRecommender;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.BookRecommenderAPI;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions.TFIDFSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileReader reader = new FileReader("goodreads_data.csv");
        FileReader reader1 = new FileReader("stopwords.txt");
        BookLoader.load(reader);
        BookRecommenderAPI recommender = new BookRecommender(BookLoader.load(reader),
            new TFIDFSimilarityCalculator(BookLoader.load(reader), new TextTokenizer(reader1)));
    }
}