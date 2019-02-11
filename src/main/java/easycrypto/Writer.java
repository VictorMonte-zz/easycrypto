package easycrypto;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class Writer implements Producer {

    private final String topic;
    private final KafkaProducer<String, String> producer;

    public Writer(String servers, String topic) {

        Properties config = Producer.createConfig(servers);

        this.producer = new KafkaProducer<>(config);
        this.topic = topic;
    }

    @Override
    public void process(String message) {
        Producer.write(this.producer, this.topic, message);
    }
}
