package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TextTokenizerTest {

    private static final String stopWords = """
        a
        an
        the
        is
        in
        of
        """;

    @Test
    void testTokenizerWithValidInput() {
        TextTokenizer tokenizer = new TextTokenizer(new StringReader(stopWords));

        String input = "The quick, brown fox jumps over a lazy dog.";
        List<String> expected = List.of("quick", "brown", "fox", "jumps", "over", "lazy", "dog");

        assertEquals(expected, tokenizer.tokenize(input),
            "The tokenize method should filter stopwords and return the correct tokens.");
    }

    @Test
    void testTokenizerWithNull() {
        TextTokenizer tokenizer = new TextTokenizer(new StringReader(stopWords));

        assertThrows(IllegalArgumentException.class, () -> tokenizer.tokenize(null),
            "The tokenizer should throw an exception");
    }

    @Test
    void testTokenizerWithOnlyStopWords() {
        TextTokenizer tokenizer = new TextTokenizer(new StringReader(stopWords));

        String input = "The,a of in.";
        List<String> expected = List.of();

        assertEquals(expected, tokenizer.tokenize(input),
            "The tokenize method should return empty list");
    }

    @Test
    void testStopwordsInitialization() {
        TextTokenizer tokenizer = new TextTokenizer(new StringReader(stopWords));
        Set<String> expectedStopwords = Set.of("a", "an", "the", "is", "in", "of");

        assertEquals(expectedStopwords, tokenizer.stopwords(),
            "The stopwords method should return the correct set of stopwords.");
    }


}
