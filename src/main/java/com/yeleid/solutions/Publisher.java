package com.yeleid.solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.log4j.Logger;

public interface Publisher {
    void send(List<String> data);

    Publisher kafkaPublisher = new Publisher() {

        private final kafka.javaapi.producer.Producer<Integer, String> producer;
        private final String topic;
        private final Properties props = new Properties();

        {
            props.put("serializer.class", "kafka.serializer.StringEncoder");
            props.put("metadata.broker.list", Utils.get(Constants.KAFKA_BROKER_LIST, "localhost:9092"));

            producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
            this.topic = Utils.get(Constants.KAFKA_TOPIC, "eagleeye");
        }

        @Override
        public void send(List<String> data) {
            List<KeyedMessage<Integer, String>> messages = new ArrayList<KeyedMessage<Integer, String>>();
            for (String str : data) {
                messages.add(new KeyedMessage<Integer, String>(topic, str));
            }
            producer.send(messages);
        }
    };

    Publisher loggerPublisher = new Publisher() {
        private final Logger logger = Logger.getLogger(Publisher.class.getName());
        @Override
        public void send(List<String> data) {
            logger.info(data);
        }
    };
}
