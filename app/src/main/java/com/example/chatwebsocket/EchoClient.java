package com.example.chatwebsocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

interface EchoClientInterface {
    void onMessage(String message);
    void onStatusChange(String newStatus);
}

public class EchoClient extends WebSocketClient {

    EchoClientInterface observer;

    public EchoClient(URI serverUri, EchoClientInterface observer) {
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("DEMO", "onOpen called");
        observer.onStatusChange("Connection open!");
    }

    @Override
    public void onMessage(String message) {
        Log.d("DEMO", "onMessage called");
        observer.onMessage(message);
        observer.onStatusChange("Received message");

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("DEMO", "onCLosed called");
        observer.onStatusChange("Socket closed");

    }

    @Override
    public void onError(Exception ex) {
        Log.d("DEMO", "onError called");
        observer.onStatusChange("Error in socket " + ex.toString());

    }
}
