package com.dt.learning.service;

import android.app.Service;
import android.content.Intent;
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

    //这个service是会发生内存泄露的，但是为了模仿服务端，因此不考虑

    private volatile boolean mIsDestroyed=false;
    public TCPService() {}

    @Override
    public void onCreate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket=null;
                try {
                    serverSocket=new ServerSocket(8688);
                    while (!mIsDestroyed){
                        //会阻塞当前进程
                        final Socket socket=serverSocket.accept();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    responseClient(socket);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            if (in.ready()){
                String str=in.readLine();
                Log.e("msg from client",str);
                if (str==null)  break;
                String msg=Util.randomLetter(10);
                Log.e("msg to client",msg);
                out.println(msg);
            }

            try {
                client.sendUrgentData(0xff);
            }catch (IOException e){
                Log.e("exception","close socket");
                closeSocket(client);
                in.close();
                out.close();
                break;
            }
        }
    }

    private void closeSocket(Socket socket){
        if (socket == null){
            return;
        }
        try {
            if (!socket.isInputShutdown()){
                socket.shutdownInput();
            }
            if (!socket.isOutputShutdown()){
                socket.shutdownOutput();
            }
            if (!socket.isClosed()){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.e("TCPService","onDestroy");
        mIsDestroyed=true;
        super.onDestroy();
    }
}
