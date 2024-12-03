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
        if (transactionCountThreshold == 1) {
            return true;
        }

        List<Transaction> sortedByDate = transactions.stream()
            .sorted(Comparator.comparing(Transaction::transactionDate))
            .toList();

        for (int i = 0; i + transactionCountThreshold <= sortedByDate.size(); i++) {

            LocalDateTime startDate = sortedByDate.get(i).transactionDate();
            LocalDateTime endDate = sortedByDate.get(i + transactionCountThreshold - 1).transactionDate();

            LocalDateTime thresholdTime = startDate.plus(timeWindow);

            if (endDate.isBefore(thresholdTime)) {
                return false;
            }

        }
        return true;
    }

    @Override
    public double weight() {
        return weight;
    }
}
