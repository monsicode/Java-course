package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TransactionAnalyzerImpl implements TransactionAnalyzer {

    private final List<Transaction> transactions;
    private final List<Rule> rules;

    private static final double SUM = 1.0;

    public TransactionAnalyzerImpl(Reader reader, List<Rule> rules) {
        checkIfSumIsOne(rules);

        this.rules = rules;
        var read = new BufferedReader(reader);
        transactions = read.lines()
            .skip(1)
            .map(Transaction::of)
            .toList();

    }

    private void checkIfSumIsOne(List<Rule> rules) {
        double totalWeight = rules.stream()
            .mapToDouble(Rule::weight)
            .sum();

        if (Double.compare(totalWeight, SUM) != 0) {
            throw new IllegalArgumentException("The sum of the rules dosen't make 1.0");
        }
    }

    @Override
    public List<Transaction> allTransactions() {
        return transactions;
    }

    @Override
    public List<String> allAccountIDs() {
        return transactions.stream()
            .map(Transaction::accountID)
            .distinct()
            .toList();
    }

    @Override
    public Map<Channel, Integer> transactionCountByChannel() {
        return transactions.stream()
            .collect(Collectors.groupingBy(
                Transaction::channel,
                Collectors.summingInt(t -> 1)));
    }

    private void checkNull(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id cannot be not initialize");
        }
    }

    @Override
    public double amountSpentByUser(String accountID) {
        checkNull(accountID);

        return transactions.stream()
            .filter(t -> t.accountID().equals(accountID))
            .mapToDouble(Transaction::transactionAmount)
            .sum();
    }

    @Override
    public List<Transaction> allTransactionsByUser(String accountId) {
        checkNull(accountId);

        return transactions.stream()
            .filter(t -> t.accountID().equals(accountId))
            .toList();
    }

    @Override
    public double accountRating(String accountId) {
        checkNull(accountId);

        return 0;
    }

    @Override
    public SortedMap<String, Double> accountsRisk() {

        Map<String, List<Transaction>> transactionsByAccount = transactions.stream()
            .collect(Collectors.groupingBy((Transaction::accountID), TreeMap::new, Collectors.toList()));

        SortedMap<String, Double> sortedRiskByAccount = new TreeMap<>();

        transactionsByAccount.forEach((accountID, accountTransactions) -> {
            double totalRisk = rules.stream()
                .filter(rule -> rule.applicable(accountTransactions))
                .mapToDouble(Rule::weight)
                .sum();

            sortedRiskByAccount.put(accountID, totalRisk);

        });

        return sortedRiskByAccount;
    }

}
