package easycrypto.core;

import easycrypto.infraestructure.Consumer;
import easycrypto.infraestructure.Producer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Collections;

public class Reader implements Consumer {

    private static final Logger logger = LogManager.getLogger();
    private final KafkaConsumer<String, String> consumer;
    private final String topic;

    public Reader(String servers, String groupId, String topic) {
        this.consumer = new KafkaConsumer<>(Consumer.createConfig(servers, groupId));
        this.topic = topic;
    }

    public void run(Producer producer) {
        logger.info("Subscribing to topic " + topic);
        this.consumer.subscribe(Collections.singletonList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                logger.info("Record: " + record.value());
                producer.process(record.value());
            }
        }
    }
}
