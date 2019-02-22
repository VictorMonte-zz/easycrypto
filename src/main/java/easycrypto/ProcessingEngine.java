package easycrypto;

import easycrypto.core.Enricher;
import easycrypto.core.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessingEngine {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        logger.info("Starting easycrypto app");

        String servers = System.getenv("SERVERS");
        String groupId = System.getenv("GROUP_ID");
        String sourceTopic = System.getenv("SOURCE_TOPIC");
        String validTopic = System.getenv("VALID_TOPIC");
        String invalidTopic = System.getenv("INVALID_TOPIC");

        logger.info("{} {} {} {} {}", servers, groupId, sourceTopic, validTopic, invalidTopic);

        Reader reader = new Reader(servers, groupId, sourceTopic);
        Enricher enricher = new Enricher(servers, validTopic, invalidTopic);

        logger.info("Running enricher");

        reader.run(enricher);

    }
}
