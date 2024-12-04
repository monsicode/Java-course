package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class LocationsRule implements Rule {

    private final int threshold;
    private final double weight;

    public LocationsRule(int threshold, double weight) {
        this.threshold = threshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        int countLocations = (int) transactions.stream()
            .map(Transaction::location)
            .distinct()
            .count();

        return countLocations >= threshold;
    }

    @Override
    public double weight() {
        return weight;
    }
}
