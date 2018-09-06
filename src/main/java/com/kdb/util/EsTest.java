package com.kdb.util;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Collections;

public class EsTest {

    //初始化RestClient实例
    static RestClient restClient = RestClient.builder(
            new HttpHost("127.0.0.1", 9200, "http")).build();


    public static void main(String[] args) throws IOException {

        Response response = restClient.performRequest("GET", "/index", Collections.singletonMap("pretty", "true"));
        System.out.println(EntityUtils.toString(response.getEntity()));
        restClient.close();
    }
}
