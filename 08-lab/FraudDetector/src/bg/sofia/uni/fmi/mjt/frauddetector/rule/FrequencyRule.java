package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Comparator;
import java.util.List;

public class FrequencyRule implements Rule {

    private final int transactionCountThreshold;
    private final TemporalAmount timeWindow;
    private final double weight;

    public FrequencyRule(int transactionCountThreshold, TemporalAmount timeWindow, double weight) {
        this.transactionCountThreshold = transactionCountThreshold;
        this.timeWindow = timeWindow;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        List<Transaction> sortedByDate = transactions.stream()
            .sorted(Comparator.comparing(Transaction::transactionDate))
            .toList();

        for (int i = 0; i + transactionCountThreshold <= sortedByDate.size() - 1; i++) {

            LocalDateTime startDate = sortedByDate.get(i).transactionDate();
            LocalDateTime endDate = sortedByDate.get(i + transactionCountThreshold ).transactionDate();

            LocalDateTime thresholdTime = startDate.plus(timeWindow);

            if (!endDate.isAfter(thresholdTime)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double weight() {
        return weight;
    }
}
