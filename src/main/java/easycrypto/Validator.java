package easycrypto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class Validator implements Producer {

    private static final Logger logger = LogManager.getLogger();
    private final KafkaProducer<String, String> producer;
    private final String validMessages;
    private final String invalidMessages;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Validator(String servers, String validMessages, String invalidMessages) {

        final Properties config = Producer.createConfig(servers);

        this.producer = new KafkaProducer<>(config);
        this.validMessages = validMessages;
        this.invalidMessages = invalidMessages;
    }

    @Override
    public void process(String message) {

        try {

            JsonNode root = MAPPER.readTree(message);

            if (root != null) {

                String error = "";
                error = error.concat(validate(root, "event"));
                error = error.concat(validate(root, "customer"));
                error = error.concat(validate(root, "currency"));
                error = error.concat(validate(root, "timestamp"));

                if(error.length() > 0) {
                    Producer.write(this.producer, this.invalidMessages, "{\"error\": \" " + error + "\"}");
                } else {
                    Producer.write(this.producer, this.validMessages, MAPPER.writeValueAsString(root));
                }

            }

        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();

            Producer.write(this.producer, this.invalidMessages, "{\"error\": \""
                    + e.getClass().getSimpleName() + ": " + e.getMessage() + "\"}");
        }

    }

    private String validate(JsonNode root, String path) {

        if(!root.has(path)) {
            return path.concat(" is missing. ");
        }

        JsonNode node = root.path(path);
        if(node.isMissingNode()) {
            return path.concat(" is missing. ");
        }

        return "";
    }
}
