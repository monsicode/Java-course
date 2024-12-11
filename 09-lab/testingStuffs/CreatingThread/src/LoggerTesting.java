import java.util.logging.Logger;

public class LoggerTesting {

    static final Logger logger = Logger.getLogger(LoggerTesting.class.getName());

    public static void main(String[] args) {
        logger.info("Some information");
        logger.warning("This is a warning");
    }

}
