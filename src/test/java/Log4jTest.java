
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Log4jTest {

    public static Logger logger = LoggerFactory.getLogger(Log4jTest.class);

    public static void main(String[] args) {
        logger.debug("debug");
        logger.info("info");
        logger.error("error");
    }
}
