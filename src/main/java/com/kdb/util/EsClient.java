package com.kdb.util;

import okhttp3.*;

import java.io.IOException;

public class EsClient {

    public static void main(String[] args) {
        String url = "http://10.0.1.70:9200/hero-api-tomcat-2018.04.04/_search";
        String json = "{\n" +
                "\"query\": {\n" +
                "\"bool\": {\n" +
                "\"must\": [\n" +
                "{\n" +
                "\"term\": {\n" +
                "\"message\": \"2018-04_05\"\n" +
                "}\n" +
                "}\n" +
                "],\n" +
                "\"must_not\": [ ],\n" +
                "\"should\": [ ]\n" +
                "}\n" +
                "},\n" +
                "\"from\": 0,\n" +
                "\"size\": 10,\n" +
                "\"sort\": [{\"timestamp\":{\"order\":\"desc\"}} ],\n" +
                "\"aggs\": { }\n" +
                "}";
        MediaType mediaType = MediaType.parse("application/json");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(mediaType,json);
        Request request = new Request.Builder().url(url).post(requestBody).build();

        try {
            ResponseBody responseBody = okHttpClient.newCall(request).execute().body();
            System.out.println(responseBody.string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
