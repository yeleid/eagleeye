package com.yeleid.solutions.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YarnCluster {
    private Apps apps;

    public Apps getApps() { return apps; }
    public void setApps(Apps value) { apps = value; }

    public List<AppMetric> appMetrics() {
        List<AppMetric> metrics = new ArrayList<AppMetric>();
        if (apps != null) {
            long ts = System.currentTimeMillis();
            for (Map<String, Object> map : apps.getApp()) {
                metrics.add(AppMetric.apply(map, ts));
            }
        }
        return metrics;
    }

    public static class Apps {
        private List<Map<String, Object>> app;

        public List<Map<String, Object>> getApp() { return app; }
        public void setApp(List<Map<String, Object>> value) { app = value; }
    }
}

