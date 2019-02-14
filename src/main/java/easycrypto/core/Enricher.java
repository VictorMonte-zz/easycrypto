package easycrypto.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.maxmind.geoip2.model.CityResponse;
import easycrypto.infraestructure.Producer;
import easycrypto.service.GeoIPService;
import easycrypto.service.OpenExchangeService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Enricher implements Producer {

    private final KafkaProducer<String, String> producer;
    private final String validMessages;
    private final String invalidMessages;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger();

    public Enricher(String servers, String validMessages, String invalidMessages) {
        this.producer = new KafkaProducer<>(Producer.createConfig(servers));
        this.validMessages = validMessages;
        this.invalidMessages = invalidMessages;
    }

    @Override
    public void process(String message) {

        try {

            final JsonNode root = MAPPER.readTree(message);
            addGeoLocation(root);
            addPrice(root);

            Producer.write(
                    this.producer,
                    this.validMessages,
                    MAPPER.writeValueAsString(root));

        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private void addPrice(JsonNode root) {
        final OpenExchangeService exchangeService = new OpenExchangeService();
        ((ObjectNode) root).with("currency").put("rate", exchangeService.getPrice("BTC"));
    }

    private void addGeoLocation(JsonNode root) {

        final JsonNode ipAddressNode = root.path("customer").path("ipAddress");

        if(ipAddressNode.isMissingNode()) {

            Producer.write(
                    this.producer,
                    this.invalidMessages,
                    "{\"error\": \"customer.ipAddress is missing\"}");
        } else {

            final String ipAddress = ipAddressNode.textValue();
            final CityResponse response = new GeoIPService().getLocation(ipAddress);

            ((ObjectNode) root).with("customer").put("country", response.getCountry().getName());
            ((ObjectNode) root).with("customer").put("city", response.getCity().getName());
        }
    }
}
