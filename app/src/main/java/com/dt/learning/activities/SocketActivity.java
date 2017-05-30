package com.dt.learning.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dt.learning.R;
import com.dt.learning.service.TCPService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketActivity extends AppCompatActivity {
    private final static int RECEIVE_MSG_FROM_SERVICE=1;
    private final static int MESSAGE_SOCKET_CONNECTED=2;

    private EditText edtMsgToServer;
    private TextView tvMsgFromServer;
    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private Button btnSend;

    private  Handler mHander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case RECEIVE_MSG_FROM_SERVICE:
                    tvMsgFromServer.setText((String)msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edtMsgToServer=(EditText)findViewById(R.id.content_socket_edt_to_server);
        tvMsgFromServer=(TextView)findViewById(R.id.content_socket_tv_from_server);
        btnSend=(Button)findViewById(R.id.content_socket_btn_send);
        Intent service=new Intent(this, TCPService.class);
        startService(service);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectTCPService();
            }
        }).start();

    }

    private void connectTCPService(){
        Socket socket=null;
        while (socket==null){
            try {
                socket=new Socket("localhost",8688);
                mClientSocket=socket;
                mPrintWriter=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                mHander.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
            } catch (IOException e) {
                SystemClock.sleep(1000);
                e.printStackTrace();
            }
        }
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!this.isFinishing()){
                String msg=br.readLine();
                if (msg!=null)
                    mHander.obtainMessage(RECEIVE_MSG_FROM_SERVICE,msg).sendToTarget();
                else break;
            }
            Log.e("execute","printwriter close");
            if (mPrintWriter!=null) mPrintWriter.close();
            if (br!=null)   br.close();
            if (socket!=null)   socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendClick(View view){
        String msg=edtMsgToServer.getText().toString();
        if (msg!=null&&!msg.equals("")&&mPrintWriter!=null){
            mPrintWriter.println(msg);
            edtMsgToServer.setText("");
        }
    }

    @Override
    protected void onDestroy() {

        try {
//            if (mPrintWriter!=null) mPrintWriter.close();
            if (mClientSocket!=null) {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

}
