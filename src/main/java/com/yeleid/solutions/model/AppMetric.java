package com.yeleid.solutions.model;

import com.yeleid.solutions.Utils;

import java.util.Map;

public class AppMetric {
    private AppMetric() {}

    public static AppMetric apply(Map<String, Object> map, long ts) {
        AppMetric appMetric = new AppMetric();

        appMetric.applicationId = (String)map.get("id");
        appMetric.allocatedMB = (int)map.get("allocatedMB");
        appMetric.allocatedVCores = (int)map.get("allocatedVCores");
        appMetric.queue = (String)map.get("queue");
        appMetric.user = (String)map.get("user");
        appMetric.name = (String)map.get("name");

        appMetric.timestamp = Utils.getDateFormat(ts);
        appMetric.id = appMetric.applicationId + "$" + ts;

        return appMetric;
    }

    private String id;
    private int allocatedMB;
    private int allocatedVCores;
    private String queue;
    private String name;
    private String user;

    private String applicationId;
    private String timestamp;

    public String getId() { return id; }
    public void setId(String value) { id = value; }

    public int getAllocatedMB() { return allocatedMB; }
    public void setAllocatedMB(int value) { allocatedMB = value; }

    public int getAllocatedVCores() { return allocatedVCores; }
    public void setAllocatedVCores(int value) { allocatedVCores = value; }

    public String getQueue() { return queue; }
    public void setQueue(String value) { queue = value; }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String value) { applicationId = value; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String value) { timestamp = value; }

    public String getName() { return name; }
    public void setName(String value) { name = value; }

    public String getUser() { return user; }
    public void setUser(String value) { user = value; }

    public String toCSV() {
        char split = ',';
        StringBuffer sb = new StringBuffer()
                .append(id).append(split)
                .append(applicationId).append(split)
                .append(allocatedMB).append(split)
                .append(allocatedVCores).append(split)
                .append(queue).append(split)
                .append(timestamp).append(split)
                .append(user).append(split)
                .append(name);
        return sb.toString();
    }
}
