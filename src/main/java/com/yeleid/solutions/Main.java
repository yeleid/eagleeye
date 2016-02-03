package com.yeleid.solutions;

import com.yeleid.solutions.model.AppMetric;
import com.yeleid.solutions.model.YarnCluster;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Eagle eye starts monitoring ...");
        logger.info(SearchEngine.Request.yarnRequest.getUrl());

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                byte[] response = SearchEngine.search(SearchEngine.Request.yarnRequest);
                YarnCluster yarnCluster = Utils.jsonToObject(response, YarnCluster.class);
                List<AppMetric> appMetrics = yarnCluster.appMetrics();

                List<String> messages = new ArrayList<String>();
                for (AppMetric appMetric : appMetrics) {
                    messages.add(Utils.objectToJson(appMetric));
                }

                Utils.getPublisher().send(messages);
            }
        }, 0, Long.valueOf(Utils.get(Constants.SCHEDULE_PERIOD, "10")), TimeUnit.SECONDS);

    }
}
