package com.yeleid.solutions;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;

public class SearchEngine {

    public static interface Request {
        String getUrl();

        Request yarnRequest = new Request() {
            @Override
            public String getUrl() {
                String rm = Utils.get(Constants.RM_ADDRESS, null);
                if (rm == null) {
                    throw new RuntimeException("Resource Manager address is null.");
                }
                return rm + Utils.get(Constants.YARN_APPLICATIONS_PATH, "/ws/v1/cluster/apps?states=RUNNING");
            }
        };
    }

    private static final Logger logger = Logger.getLogger(SearchEngine.class.getName());
    private static CloseableHttpClient client = null;

    public static byte[] search(Request request) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(request.getUrl());
        httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = client.execute(httpGet);
            if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
                HttpEntity entity = httpResponse.getEntity();
                return Utils.getBytes(entity.getContent());
            }
            return null;
        } catch (IOException ex){
            throw new RuntimeException(ex);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {}
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {}
            }
        }
    }
}
