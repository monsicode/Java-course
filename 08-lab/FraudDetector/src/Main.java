import bg.sofia.uni.fmi.mjt.frauddetector.analyzer.TransactionAnalyzer;
import bg.sofia.uni.fmi.mjt.frauddetector.analyzer.TransactionAnalyzerImpl;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.FrequencyRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.LocationsRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.SmallTransactionsRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.ZScoreRule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.time.Period;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String filePath = "dataset.csv";

        Reader reader = new FileReader(filePath);
        List<Rule> rules = List.of(
            new ZScoreRule(1.5, 0.3),
            new LocationsRule(3, 0.4),
            new FrequencyRule(4, Period.ofWeeks(4), 0.25),
            new SmallTransactionsRule(1, 10.20, 0.05)
        );

        TransactionAnalyzer analyzer = new TransactionAnalyzerImpl(reader, rules);

        //  System.out.println(analyzer.allAccountIDs());
        //  System.out.println(analyzer.allTransactionsByUser(analyzer.allTransactions().getFirst().accountID()));
          //System.out.println(analyzer.accountsRisk());
//        Rule rule = new FrequencyRule(2, Period.ofMonths(1), 0.2);
//        ZScoreRule rule2 = new ZScoreRule(2, 0.2);

       // System.out.println(rule2.calculateVariance(analyzer.allTransactionsByUser("AC00192")));

        System.out.println(Double.parseDouble(String.format("%.2f", 0.2352)));

    }

}
