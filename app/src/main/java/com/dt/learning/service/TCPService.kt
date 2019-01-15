package com.dt.learning.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

import com.dt.learning.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class TCPService : Service() {

    @Volatile
    private var mIsDestroyed = false

    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch(Dispatchers.IO) {
            while (!mIsDestroyed) {
                var serverSocket: ServerSocket
                try {
                    serverSocket = ServerSocket(8688)
                    while (!mIsDestroyed) {
                        //会阻塞当前进程
                        val socket = serverSocket.accept()
                        launch(Dispatchers.IO) {
                            responseClient(socket)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

    }

    private fun responseClient(client: Socket) {
        //用于接收客户端消息
        val reader = BufferedReader(InputStreamReader(client.getInputStream()))
        //用于向客户端发送消息
        val out = PrintWriter(BufferedWriter(OutputStreamWriter(client.getOutputStream())), true)
        out.println("欢迎来到聊天室")
        while (!mIsDestroyed) {
            if (reader.ready()) {
                val str = reader.readLine()
                Log.e("msg from client", str)
                if (str == null) break
                val msg = Util.randomLetter(10)
                Log.e("msg to client", msg)
                out.println(msg)
            }

            try {
                client.sendUrgentData(0xff)
            } catch (e: IOException) {
                Log.e("exception", "close socket")
                closeSocket(client)
                reader.close()
                out.close()
                break
            }

        }
    }

    private fun closeSocket(socket: Socket?) {
        if (socket == null) {
            return
        }
        try {
            if (!socket.isInputShutdown) {
                socket.shutdownInput()
            }
            if (!socket.isOutputShutdown) {
                socket.shutdownOutput()
            }
            if (!socket.isClosed) {
                socket.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onDestroy() {
        Log.e("TCPService", "onDestroy")
        mIsDestroyed = true
        super.onDestroy()
    }
}
