
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;


public class Log4jTest {

    public static Logger logger = LoggerFactory.getLogger(Log4jTest.class);

    public static void main(String[] args) {

        Profiler profiler = new Profiler("TEST");

        profiler.start("A");
        logger.debug("debug");

        profiler.start("B");
        logger.info("info");

        profiler.start("C");
        logger.error("error");

        profiler.stop().print();
    }
}
