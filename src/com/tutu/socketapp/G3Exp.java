package com.tutu.socketapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class G3Exp extends Activity {
	 // 定义侦听端口号  
    private final int SERVER_PORT = 8090;  
    private TextView textView;  
    private String content = "";  
      
      
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_test);  
        textView = (TextView)findViewById(R.id.g3_msg);  
          
        // 开一个新的线程来侦听客户端连接及发来的信息和打开相应网站  
        new Thread() {  
            public void run() {  
                startServer();  
            }  
        }.start();  
    }  
  
    private void startServer() {  
        try {  
            //ServerSocket serverSocket = new ServerSocket(SERVER_PORT);  
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);  
            // 循环侦听客户端连接请求  
            while (true) {  
                Socket client = serverSocket.accept();  
                  
                try {  
                    Log.e("hehheh", "有人来访:");  
                    // 等待客户端发送打开网站的消息  
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));  
                    String str = in.readLine();  
                    content += str+"\n";  
                    mHandler.sendMessage(mHandler.obtainMessage());  
                    openUrl(str);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                } finally {  
                    client.close();  
                }  
                Thread.sleep(3000);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    private void openUrl(String url) {  
        Uri uri = Uri.parse(url);  
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
        startActivity(intent);  
    }  
      
    public Handler mHandler = new Handler() {  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            textView.setText(textView.getText().toString()+content);  
        }  
    }; 
}
