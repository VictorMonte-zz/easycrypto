package easycrypto.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public final class OpenExchangeService {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger();
    private String apiKey;

    public OpenExchangeService() {
        this.apiKey = System.getenv("OPENEXCHANGE_API_KEY");
    }

    public double getPrice(String currency) {

        try {
            final URL url = new URL("https://openexchangerates.org/api/latest.json?app_id=" + apiKey);
            final JsonNode root = MAPPER.readTree(url);
            final JsonNode node = root.path("rates").path(currency);
            return Double.parseDouble(node.toString());

        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        }

        return 0;
    }

}
