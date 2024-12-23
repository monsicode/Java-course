package bg.sofia.uni.fmi.mjt.goodreads.book;

import java.util.Arrays;
import java.util.List;

public record Book(
    String ID,
    String title,
    String author,
    String description,
    List<String> genres,
    double rating,
    int ratingCount,
    String URL
) {

    private static final int FIELD_ONE = 1;
    private static final int FIELD_TWO = 2;
    private static final int FIELD_THREE = 3;
    private static final int FIELD_FOUR = 4;
    private static final int FIELD_FIVE = 5;
    private static final int FIELD_SIX = 6;
    private static final int FIELD_SEVEN = 7;
    private static final String EMPTY_STRING = "";

    public static Book of(String[] tokens) {

        String numberRatings = tokens[FIELD_SIX];
        numberRatings = numberRatings.replace(",", EMPTY_STRING);

        String genresString = tokens[FIELD_FOUR];
        String genresWithoutBrackets = genresString.substring(1, genresString.length() - 1);
        List<String> genres = Arrays.stream(genresWithoutBrackets.replace("'", EMPTY_STRING).split(",\\s*"))
            .map(String::trim)
            .toList();

        return new Book(tokens[0],
            tokens[FIELD_ONE],
            tokens[FIELD_TWO],
            tokens[FIELD_THREE],
            genres,
            Double.parseDouble(tokens[FIELD_FIVE]), Integer.parseInt(numberRatings),
            tokens[FIELD_SEVEN]);
    }
}