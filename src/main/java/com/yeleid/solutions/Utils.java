package com.yeleid.solutions;

import com.google.common.io.ByteStreams;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import scala.collection.immutable.Stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    private static Properties props = new Properties();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static {
        try {
            props.load(ClassLoader.getSystemResource(Constants.PROPERTIES).openStream());
        } catch (IOException e) {
            logger.error("Fail to locate properties file.");
            System.exit(1);
        }
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static byte[] getBytes(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ByteStreams.copy(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }

    public static InputStream getStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static <T> T jsonToObject(byte[] json, Class<T> cls) {
        T obj = null;
        try {
            obj = new ObjectMapper().readValue(json, cls);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return obj;
    }

    public static String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getDateFormat(long millionseconds) {
        return dateFormat.format(new Date(millionseconds));
    }

    public static Publisher getPublisher() {
        String producerStr = get(Constants.PRODUCER, "logger");
        final String[] producers = producerStr.split(",");

        return new Publisher() {
            final List<Publisher> publishers = new ArrayList<Publisher>();
            {
                for (String producer : producers) {
                    if (Constants.KAFKA_PRODUCER.equals(producer)) {
                        publishers.add(Publisher.kafkaPublisher);
                    }
                    else if (Constants.LOGGER_PRODUCER.equals(producer)) {
                        publishers.add(Publisher.loggerPublisher);
                    }
                    else if (Constants.HTTP_PRODUCER.equals(producer)) {
                        publishers.add(Publisher.httpPublisher);
                    }
                }
            }
            @Override
            public void send(List<String> data) {
                for (Publisher publisher : publishers) {
                    publisher.send(data);
                }
            }
        };
    }
}
