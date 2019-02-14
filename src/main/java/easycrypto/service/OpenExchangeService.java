package easycrypto.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class OpenExchangeService {

    private static final String API_KEY = "";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger();

    public double getPrice(String currency) {

        try {
            final URL url = new URL("https://openexchangerates.org/api/latest.json?app_id=" + API_KEY);
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
