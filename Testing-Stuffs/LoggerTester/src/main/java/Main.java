import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.trace("1.This is a TRACE message.");
        logger.debug("2.This is a DEBUG message.");
        logger.info("3.This is an INFO message.");
        logger.warn("4.This is a WARN message.");
        logger.error("5.This is an ERROR message.");
        fun();
    }

    static void fun() {
        logger.info("Some info");
    }


}
