package com.example.chatwebsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EchoClientInterface {

    EchoClient echoClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.connectBtn).setOnClickListener(this);
        findViewById(R.id.sendBtn).setOnClickListener(this);
        findViewById(R.id.closeBtn).setOnClickListener(this);
    }

    private void openConnection(){
        try {
            //echoClient = new EchoClient(new URI("wss://echo.websocket.org"), this);
            echoClient = new EchoClient(new URI("wss://obscure-waters-98157.herokuapp.com"), this);
            echoClient.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.connectBtn) {
            openConnection();
        }
        if (v.getId() == R.id.sendBtn) {
            sendMessage();
        }
        if (v.getId() == R.id.closeBtn){
            closeConnection();
        }
    }

    private void closeConnection() {
        if(echoClient != null && echoClient.isOpen()) {
            echoClient.close();
        }
    }

    private void sendMessage() {
        if (echoClient != null && echoClient.isOpen()) {
            EditText editor = findViewById(R.id.messageTxt);
            String text = editor.getText().toString();
            echoClient.send(text);
        }
    }

    @Override
    public void onMessage(final String message) {
        // luodaan uusi runnable olio joka ajetaan käyttöliittymän säikeessä
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView messageView = findViewById(R.id.messageView);
                messageView.append(message);

            }
        });

    }

    @Override
    public void onStatusChange(final String newStatus) {
        runOnUiThread(new Runnable() {
        @Override
        public void run() {
            TextView messageView = findViewById(R.id.statusText);
            messageView.setText(newStatus);

        }
    });


    }
}
