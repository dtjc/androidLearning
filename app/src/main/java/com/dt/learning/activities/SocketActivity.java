package com.dt.learning.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dt.learning.R;
import com.dt.learning.Util.ConstantKt;
import com.dt.learning.Util.Util;
import com.dt.learning.service.TCPService;
import com.dt.learning.thread.CheckConnectionRunnable;
import com.dt.learning.thread.SocketListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketActivity extends AppCompatActivity implements SocketListener{
    private final static int RECEIVE_MSG_FROM_SERVICE = 2;
    private final static int MESSAGE_SOCKET_CONNECTED = 1;

    private final static int THIS_ID = 1;
    private final static int TO_ID = 2;

    private EditText edtMsgToServer;
    private TextView tvMsgFromServer;
    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private BufferedReader br;
    private Button btnSend;
    private volatile boolean isClosed = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECEIVE_MSG_FROM_SERVICE:
                    tvMsgFromServer.setText((String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    Toast.makeText(SocketActivity.this,"Socket connected",Toast.LENGTH_SHORT).show();
                    btnSend.setEnabled(true);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edtMsgToServer = findViewById(R.id.content_socket_edt_to_server);
        tvMsgFromServer = findViewById(R.id.content_socket_tv_from_server);
        btnSend = findViewById(R.id.content_socket_btn_send);
        Intent service = new Intent(this, TCPService.class);
        startService(service);
        new Thread(() -> {
            while (!SocketActivity.this.isFinishing()) {
                isClosed = false;
                connectToServerWithSocket();
            }
        }).start();
    }


    private void connectToServerWithSocket() {
        Socket socket = null;
        while (socket == null && !isFinishing()) {
            try {
                socket = new Socket(ConstantKt.SERVER_IP, 8688);
                btnSend.post(() -> Util.showToast("Socket connecting"));
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mPrintWriter.println(THIS_ID);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
            } catch (IOException e) {

                btnSend.post(() -> Util.showToast("Fail to connect"));

                e.printStackTrace();
            }
        }

        try {
            if (socket != null){
                char[] chars = new char[512];
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!this.isFinishing() && !isClosed) {
                    int read = br.read(chars);
                    Log.e("read", String.valueOf(read));
                    String msg = new String(chars,0,read - 1);
                    if (msg.startsWith("received:")){
                        btnSend.post(() -> Util.showToast("发送成功"));
                    }else {
                        msg = msg.substring(msg.indexOf(":") + 1, msg.length());
                        mHandler.obtainMessage(RECEIVE_MSG_FROM_SERVICE, msg).sendToTarget();
                        Log.e("msg from server", msg);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeSocket();
        }
    }

    public void sendClick(View view) {
        String txt = edtMsgToServer.getText().toString();
        if (!txt.equals("") && mPrintWriter != null) {
            final String msg = String.valueOf(TO_ID) + ":" + txt;
            if (msg.getBytes().length > 512){
                Util.showToast("消息过长");
                return;
            }
            Util.getSingleThreadEs().execute(new Runnable() {
                @Override
                public void run() {
                    mPrintWriter.println(msg);
                }
            });
            edtMsgToServer.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        Log.e("SocketActivity", "onDestroy");
        closeSocket();
        super.onDestroy();
    }

    @Override
    public void finish() {
        Log.e("SocketActivity", "finish");
        super.finish();
    }

    private void closeSocket() {
        try {
            if (mClientSocket != null && !mClientSocket.isClosed()) {
                mClientSocket.close();
            }
            if (br != null) {
                br.close();
            }
            if (mPrintWriter != null) {
                mPrintWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callBack() {
        isClosed = true;
    }
}
