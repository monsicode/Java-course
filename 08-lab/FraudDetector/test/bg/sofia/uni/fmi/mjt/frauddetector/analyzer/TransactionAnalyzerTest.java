package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.FrequencyRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.LocationsRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.SmallTransactionsRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.ZScoreRule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionAnalyzerTest {

    @Test
    void testSumWeightNotOne() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());


        List<Rule> rules = List.of(
            new ZScoreRule(1.5, 0.3),
            new LocationsRule(3, 0.1),
            new FrequencyRule(4, Period.ofWeeks(4), 0.25),
            new SmallTransactionsRule(1, 10.20, 0.05)
        );

        assertThrows(IllegalArgumentException.class, () -> new TransactionAnalyzerImpl(reader, rules)
            , "Should be thrown IllegalArgumentException when the sum of the weights is not 1.0");
    }

    @Test
    void testListOfAllTransactions() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());


        List<Rule> rules = List.of(new LocationsRule(3, 1.0));

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        assertNotNull(imp.allTransactions(), "Transactions list should be returned");
    }

    @Test
    void testListOfAllAccountsID() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());

        List<Rule> rules = List.of(new LocationsRule(3, 1.0));

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        List<String> expected = List.of("AC00055", "AC00299", "AC00107");

        assertEquals(expected, imp.allAccountIDs(), "All account ids should be returned");

    }

    @Test
    void testTransactionCountByChannel() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());

        List<Rule> rules = List.of(new LocationsRule(3, 1.0));

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        Map<Channel, Integer> expected = Map.of(Channel.BRANCH, 2, Channel.ONLINE, 1);

        assertEquals(expected, imp.transactionCountByChannel(), "Transaction count for chanel should be correct");
    }

    @Test
    void testAmountSpentByUser() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());
        List<Rule> rules = List.of(new LocationsRule(3, 1.0));

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        assertEquals(33.89, imp.amountSpentByUser("AC00055"), "User AC00055 have spent total 33.89");
    }

    @Test
    void testAmountSpentByUserWithNull() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());

        List<Rule> rules = List.of(new LocationsRule(3, 1.0));

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        assertThrows(IllegalArgumentException.class, () -> imp.amountSpentByUser(null),
            "When user is null should throw IllegalArgExc");
    }

    @Test
    void testAmountSpentByUserWithEmptyString() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());

        List<Rule> rules = List.of(new LocationsRule(3, 1.0));

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        assertThrows(IllegalArgumentException.class, () -> imp.amountSpentByUser(""),
            "When user is empty should throw IllegalArgExc");
    }

    @Test
    void testAllTransactionsWithNullUser() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());

        List<Rule> rules = List.of(new LocationsRule(3, 1.0));

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        assertThrows(IllegalArgumentException.class, () -> imp.amountSpentByUser(null),
            "When user is empty should throw IllegalArgExc");
    }

    @Test
    void testAllTransactionsByUser() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());

        List<Rule> rules = List.of(new LocationsRule(3, 1.0));

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        List<Transaction> transactionList =
            List.of(Transaction.of("TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch"));

        assertEquals(transactionList, imp.allTransactionsByUser("AC00055"), "User AC00055 have only one transaction");
    }

    @Test
    void testAllAccountsRisk() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());

        List<Rule> rules = List.of(new LocationsRule(3, 1.0));

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        SortedMap<String, Double> expected = new TreeMap<>();
        expected.put("AC00055", 0.0);
        expected.put("AC00299", 0.0);
        expected.put("AC00107", 0.0);

        assertEquals(expected, imp.accountsRisk(), "All users should have 0.0 risk");

    }

    @Test
    void testOneAccountsRisk() throws FileNotFoundException {
        Reader reader =
            new StringReader(
                "TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel" + System.lineSeparator() +
                    "TX000339,AC00055,33.89,2023-11-10 16:10:34,Nashville,Branch" + System.lineSeparator() +
                    "TX000340,AC00299,23.71,2023-09-18 16:04:52,Atlanta,Branch" + System.lineSeparator() +
                    "TX000341,AC00107,1830.0,2023-03-01 16:31:58,San Antonio,Online" + System.lineSeparator());

        List<Rule> rules = List.of(
            new ZScoreRule(1.5, 0.3),
            new LocationsRule(3, 0.4),
            new FrequencyRule(4, Period.ofWeeks(4), 0.25),
            new SmallTransactionsRule(1, 10.20, 0.05)
        );

        TransactionAnalyzerImpl imp = new TransactionAnalyzerImpl(reader, rules);

        assertEquals(0.3, imp.accountRating("AC00055"), "User AC00055 should have 0.4 total risk");

    }


}
