package com.hone.serviceorder.App;

import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpeenMsgClient {
    @Autowired
    private WebSocketClient webSocketClient;
    public void appointSending(String message) {
        webSocketClient.send(message);
    }
}