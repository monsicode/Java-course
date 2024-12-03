import bg.sofia.uni.fmi.mjt.frauddetector.analyzer.TransactionAnalyzerImpl;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.FrequencyRule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.time.Period;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Reader dataInput = new FileReader("dataset.csv");

        TransactionAnalyzerImpl tr = new TransactionAnalyzerImpl(dataInput);

        //System.out.println(tr.allAccountIDs());

        Map<Channel, Integer> map = tr.transactionCountByChannel();

        //System.out.println(map.get(Channel.ONLINE));

        //System.out.println(tr.amountSpentByUser("AC00411"));
        // System.out.println(tr.allTransactionsByUser("AC00411"));

//        System.out.println(Period.ofWeeks(1));

        FrequencyRule rule = new FrequencyRule(2, Period.ofMonths(2), 0.2);

        System.out.println(rule.applicable(tr.allTransactionsByUser("AC00135")));

    }
}
