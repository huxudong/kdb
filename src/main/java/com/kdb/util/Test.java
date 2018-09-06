package com.kdb.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kdb.consts.Consts;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.java_websocket.drafts.Draft_6455;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Test {



    public static void main(String[] args)  throws URISyntaxException{
        Scanner sc=new Scanner(System.in);
        System.out.println("输入要查询的关键字：");
        String word=sc.next();
        System.out.println("正在查询关键词<<<"+word+">>>...");


        //查询
        RequestBody body = new FormBody.Builder()
                .add("word",word)
                .add("clientId","0649a4a1492a42f6987f6dce4db926a1_5")// 830548c4a0d742919cebdd2350f222ca_5
                .build();
        String goodsJson = HttpRequest.post(Consts.SEARCH_URL,body);
        JSONArray goods = JSON.parseObject(goodsJson).getJSONArray("info");
        for(int i = 0 ; i < goods.size(); i++ ){
            System.out.println((i+1)+"、"+goods.getJSONObject(i).getString("name")+"（"+goods.getJSONObject(i).getDouble("curPrice")+"）:"+goods.getJSONObject(i).getString("curActivityId")+"\r\n");
        }
        System.out.println("输入商品名称后面的字符串：");

        String xh=sc.next();
        System.out.println("开始夺<<<"+goods.getJSONObject(Integer.valueOf(xh)-1).getString("name")+">>>...");
        Consts.ACTIVITY_ID = goods.getJSONObject(Integer.valueOf(xh)-1).getString("curActivityId");
        task();
        countDown();
    }

    public static void task() throws URISyntaxException{
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Host","121.42.247.139:8886");
        headers.put("Sec-WebSocket-Key","e3KHKZhqpnE6wBH3IPgEXg==");
        headers.put("Sec-WebSocket-Version","13");
        headers.put("Upgrade","Basic websocket");
        headers.put("Origin","http://121.42.247.139:8886");
        headers.put("Authorization","Basic KG51bGwpOihudWxsKQ==");
        ExampleClient c = new ExampleClient( new URI( "ws://121.42.247.139:8886/kauction/webSocket" ) ,new Draft_6455(),headers,2000);
        c.connect();

    }

    public static void countDown() {
        while (true) {
            //if(TimerCount.count > 0){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                TimerCount.count--;
                if(TimerCount.count  % 1000 ==0){
                    System.err.println(TimerCount.count / 1000);
                }

                if(TimerCount.count == Consts.limit){
                    RequestBody body = new FormBody.Builder()
                            .add("activityId",Consts.ACTIVITY_ID)
                            .add("bidCount","1")
                            .add("clientId","afcd1ec3c9b24b6a9630751ada2fb5dd_9")//
                            .add("token","903e261d25250a3a846b97ab75c7b3cb8259687633ac2ec98ca9d9434849b5e36d628d0a7e3e1768e8a2d3419a52db49") //
                            .build();
                    HttpRequest.post(Consts.BUY_URL,body);
                }
        //    }
        }
       // countDown();

    }
}
