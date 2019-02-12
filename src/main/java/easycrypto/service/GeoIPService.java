package easycrypto.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.InetAddress;


public class GeoIPService {

    private static final String MAXMINDDB_LOCATION = "src/main/resources/GeoLite2-City.mmdb";
    private static final Logger logger = LogManager.getLogger();

    public CityResponse getLocation(String ipAddress) {

        try {

            final File database = new File(MAXMINDDB_LOCATION);
            final DatabaseReader reader = new DatabaseReader.Builder(database).build();

            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            CityResponse response = reader.city(inetAddress);

            return response;

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }

        return null;
    }

}
