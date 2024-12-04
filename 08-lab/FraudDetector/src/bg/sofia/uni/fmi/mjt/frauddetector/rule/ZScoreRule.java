package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class ZScoreRule implements Rule {

    private final double zScoreThreshold;
    private final double weight;

    public ZScoreRule(double zScoreThreshold, double weight) {
        this.zScoreThreshold = zScoreThreshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        double mean = calculateAverageValue(transactions);
        double deviation = calculateStandardDeviation(transactions);

        return transactions.stream()
            .mapToDouble(t -> (t.transactionAmount() - mean) / deviation)
            .anyMatch(z -> Double.compare(z, zScoreThreshold) > 0);
    }

    private double calculateAverageValue(List<Transaction> transactions) {
        return transactions.stream()
            .mapToDouble(Transaction::transactionAmount)
            .average()
            .orElse(0.0);
    }

    private double calculateVariance(List<Transaction> transactions) {
        double mean = Double.parseDouble(String.format("%.2f", calculateAverageValue(transactions)));

        return transactions.stream()
            .mapToDouble(t -> Math.pow(t.transactionAmount() - mean, 2))
            .average()
            .orElse(0.0);

    }

    private double calculateStandardDeviation(List<Transaction> transactions) {
        double variance = Double.parseDouble(String.format("%.2f", calculateVariance(transactions)));

        return Math.sqrt(variance);
    }

    @Override
    public double weight() {
        return Double.parseDouble(String.format("%.2f", weight));
    }
}
