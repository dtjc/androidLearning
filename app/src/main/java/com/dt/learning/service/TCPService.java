package com.dt.learning.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.IBinder;
import android.util.Log;

import com.dt.learning.Util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPService extends Service {
    private boolean mIsDestroyed=false;
    public TCPService() {}

    @Override
    public void onCreate() {
        new Thread(()->{
            ServerSocket serverSocket=null;
            try {
                serverSocket=new ServerSocket(8688);
                while (!mIsDestroyed){
                    Socket socket=serverSocket.accept();
                    new Thread(()->{
                        try {
                            responseClient(socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        super.onCreate();
    }

    private void responseClient(Socket client)  throws IOException{
        //用于接收客户端消息
        BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
        out.println("欢迎来到聊天室");
        while (!mIsDestroyed){
            String str=in.readLine();
            if (str==null)  break;
            Log.e("msgFromClient",str);
            String msg=Util.randomLetter(10);
            out.println(msg);
        }
        if (in!=null)   in.close();
        if (out!=null)  out.close();
        client.close();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
