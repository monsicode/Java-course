package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.Validator.nullCheck;

public class TextTokenizer {

    private static final String PUNCTUATION_REGEX = "\\p{Punct}";
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String SPACE = " ";

    private final Set<String> stopwords;

    public TextTokenizer(Reader stopwordsReader) {
        try (var br = new BufferedReader(stopwordsReader)) {
            stopwords = br.lines().collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not load dataset", ex);
        }
    }

    public List<String> tokenize(String input) {
        nullCheck(input, "Input cannot be null");

        String resultToLowerCase =
            input.replaceAll(PUNCTUATION_REGEX, "").replaceAll(WHITESPACE_REGEX, " ").toLowerCase();

        return Arrays.stream(resultToLowerCase.split(SPACE))
            .filter(word -> !stopwords.contains(word))
            .toList();
    }

    public Set<String> stopwords() {
        return stopwords;
    }

}