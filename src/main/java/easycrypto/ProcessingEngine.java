package easycrypto;

import easycrypto.core.Enricher;
import easycrypto.core.Reader;

public class ProcessingEngine {

    public static void main(String[] args) {

        String servers = System.getenv("SERVERS");
        String groupId = System.getenv("GROUP_ID");
        String sourceTopic = System.getenv("SOURCE_TOPIC");
        String validTopic = System.getenv("VALID_TOPIC");
        String invalidTopic = System.getenv("INVALID_TOPIC");

        Reader reader = new Reader(servers, groupId, sourceTopic);
        Enricher enricher = new Enricher(servers, validTopic, invalidTopic);
        reader.run(enricher);

    }
}
