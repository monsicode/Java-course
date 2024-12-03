package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction(String transactionID, String accountID, double transactionAmount,
                          LocalDateTime transactionDate, String location, Channel channel) {

    private static final String DELIMITER = ",";
    private static final int FIELD_ONE = 1;
    private static final int FIELD_TWO = 2;
    private static final int FIELD_THREE = 3;
    private static final int FIELD_FOUR = 4;
    private static final int FIELD_FIVE = 5;

    private static final int TOTAL_FIELDS = 6;

    public static Transaction of(String line) {
        String[] tokens = line.split(DELIMITER);

        if (tokens.length != TOTAL_FIELDS) {
            throw new IllegalArgumentException("Invalid number of fields");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new Transaction(tokens[0], tokens[FIELD_ONE], Double.parseDouble(tokens[FIELD_TWO]),
            LocalDateTime.parse(tokens[FIELD_THREE], formatter),
            tokens[FIELD_FOUR], Channel.valueOf(tokens[FIELD_FIVE].toUpperCase()));
    }

    public static void main(String[] args) {
        Transaction tr = Transaction.of("TX000003,AC00019,126.29,2023-07-10 18:16:08,Mesa,Online");
        System.out.println(tr);
    }

}
