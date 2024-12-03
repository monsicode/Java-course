package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class TransactionAnalyzerImpl implements TransactionAnalyzer {

    private final List<Transaction> transactions;

    public TransactionAnalyzerImpl(Reader reader) {
        var read = new BufferedReader(reader);
        transactions = read.lines()
            .skip(1)
            .map(Transaction::of)
            .toList();
    }

    @Override
    public List<Transaction> allTransactions() {
        return transactions;
    }

    @Override
    public List<String> allAccountIDs() {
        return transactions.stream()
            .map(Transaction::accountID)
            .toList();
    }

    @Override
    public Map<Channel, Integer> transactionCountByChannel() {
        return transactions.stream()
            .collect(Collectors.groupingBy(
                Transaction::channel,
                Collectors.summingInt(t -> 1)));
    }

    @Override
    public double amountSpentByUser(String accountID) {
        return transactions.stream()
            .filter(t -> t.accountID().equals(accountID))
            .mapToDouble(Transaction::transactionAmount)
            .sum();
    }

    @Override
    public List<Transaction> allTransactionsByUser(String accountId) {
        return transactions.stream()
            .filter(t -> t.accountID().equals(accountId))
            .toList();
    }

    @Override
    public double accountRating(String accountId) {
        return 0;
    }

    @Override
    public SortedMap<String, Double> accountsRisk() {
        return null;
    }
}
