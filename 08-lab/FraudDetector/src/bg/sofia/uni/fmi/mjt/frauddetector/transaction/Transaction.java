package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction(String transactionID, String accountID, double transactionAmount,
                          LocalDateTime transactionDate, String location, Channel channel) {

    private static final String DELIMITER = ",";

    public static Transaction of(String line) {
        String[] tokens = line.split(DELIMITER);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new Transaction(tokens[0], tokens[1], Double.parseDouble(tokens[2]), LocalDateTime.parse(tokens[3], formatter),
            tokens[4], Channel.valueOf(tokens[5].toUpperCase()));
    }

    public static void main(String[] args) {
        Transaction tr = Transaction.of("TX000003,AC00019,126.29,2023-07-10 18:16:08,Mesa,Online");
        System.out.println(tr);
    }


}
