package easycrypto;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public interface Producer {

    void process(String message);

    static void write(KafkaProducer<String, String> producer, String topic, String message) {
        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        producer.send(record);
    }

    static Properties createConfig(String servers) {
        final Properties config = new Properties();
        config.put("bootstrap.servers", servers);
        config.put("acks", "all");
        config.put("retries", 0);
        config.put("batch.size", 1000);
        config.put("linger.ms", 1);
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return config;
    }
}
