package bg.sofia.uni.fmi.mjt.goodreads.book;

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

    public static Book of(String[] tokens) {

        String numberRatings = tokens[6];

        numberRatings = numberRatings.replace(",","");

        return new Book(tokens[0], tokens[FIELD_ONE], tokens[FIELD_TWO], tokens[FIELD_THREE],
            List.of(tokens[FIELD_FOUR]),
            Double.parseDouble(tokens[FIELD_FIVE]), Integer.parseInt(numberRatings),
            tokens[7]);
    }
}