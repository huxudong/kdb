package com.kdb.util;

import com.alibaba.fastjson.JSON;
import com.kdb.consts.Consts;
import com.kdb.pojo.CurInfo;
import com.kdb.pojo.Result;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class ExampleClient extends WebSocketClient {

    public ExampleClient( URI serverURI ) {
        super( serverURI );
    }

    public ExampleClient( URI serverUri , Draft draft ) {
        super( serverUri, draft );
    }

    public ExampleClient( URI serverUri,Draft draft, Map<String, String> httpHeaders, int connectTimeout) {
        super(serverUri, new Draft_6455(),httpHeaders,connectTimeout);
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        send("Hello, it is me. Mario :)");
        System.out.println( "opened connection" );
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage( String message ) {
        //System.out.println( "received: " + message );
        Result result = JSON.parseObject(message, Result.class);
        CurInfo info = result.getInfo();
        if(result.getStatus() == 0 && Consts.ACTIVITY_ID.equals(info.getActivityId())){
            Double price = Double.valueOf(info.getCurPrice());
            System.out.println("["+info.getCurName() +"]出价：" + price);
            System.out.println(info.getTimeCount());
            if(price >= Consts.PRICE_LIMIT){
                System.out.println("等待出价......");
                TimerCount.count = info.getTimeCount() * 1000;
            }
        }

    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) + " Code: " + code + " Reason: " + reason );
        if(remote){
            try {
                Test.task();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            this.reconnect();
        }
    }

    @Override
    public void onError( Exception ex ) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

    public static void main( String[] args ) throws URISyntaxException {
        ExampleClient c = new ExampleClient( new URI( "ws://localhost:8887" )); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        c.connect();
    }
}