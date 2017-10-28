package com.dt.learning.thread;

import java.net.Socket;

public class CheckConnectionRunnable implements Runnable {

    private Socket socket;
    private SocketListener listener;

    public CheckConnectionRunnable(Socket socket, SocketListener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    @Override
    public void run() {
        checkConnection();
    }

    private void checkConnection(){
        try {
            socket.sendUrgentData(0xff);
            System.out.println("connected");
        }catch (Exception e){
            listener.callBack();
            System.out.println("disconnected");
            e.printStackTrace();
        }
    }
}
