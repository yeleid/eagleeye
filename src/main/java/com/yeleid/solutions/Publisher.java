package com.yeleid.solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

    Publisher httpPublisher = new Publisher() {
        class StringWrap {
            private String body;
            public String getBody() { return body; }
            public void setBody(String value) { body = value; }
        }

        private final HttpPost httpPost = new HttpPost(Utils.get(Constants.HTTP_SERVER, "127.0.0.1:4140"));

        {
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        }

        @Override
        public void send(List<String> data) {
            CloseableHttpClient client = HttpClients.createDefault();

            List<StringWrap> pojos = new ArrayList<StringWrap>();
            for (String str : data) {
                StringWrap pojo = new StringWrap();
                pojo.setBody(str);
                pojos.add(pojo);
            }
            if (pojos.size() > 0) {
                try {
                    httpPost.setEntity(new StringEntity(Utils.objectToJson(pojos)));
                    client.execute(httpPost);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                } finally {
                    try {
                        client.close();
                    } catch (IOException e) { }
                }
            }
        }
    };
}
