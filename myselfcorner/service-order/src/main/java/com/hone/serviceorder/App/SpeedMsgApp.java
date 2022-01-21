package com.hone.serviceorder.App;

import com.hone.localcommons.constant.FileConstant;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Date;

@Component
public class SpeedMsgApp {
    @Bean
    public WebSocketClient webSocketClient() {
        try {
            WebSocketClient webSocketClient = new WebSocketClient(new URI(FileConstant.SOCKETURL+new Date().getSeconds()),new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                }
                @Override
                public void onMessage(String message) {
                }
                @Override
                public void onClose(int code, String reason, boolean remote) {
                }
                @Override
                public void onError(Exception ex) {
                }
            };
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}