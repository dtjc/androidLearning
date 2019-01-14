package com.dt.learning.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message

import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.dt.learning.R
import com.dt.learning.util.*
import com.dt.learning.util.Util
import com.dt.learning.service.TCPService
import com.dt.learning.thread.SocketListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket

class SocketActivity : BaseActivity(), SocketListener {

    private var edtMsgToServer: EditText? = null
    private var tvMsgFromServer: TextView? = null
    private var mClientSocket: Socket? = null
    private var mPrintWriter: PrintWriter? = null
    private var br: BufferedReader? = null
    private var btnSend: Button? = null
    @Volatile
    private var isClosed = false

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                RECEIVE_MSG_FROM_SERVICE -> tvMsgFromServer!!.text = msg.obj as String
            }
            super.handleMessage(msg)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        edtMsgToServer = findViewById(R.id.content_socket_edt_to_server)
        tvMsgFromServer = findViewById(R.id.content_socket_tv_from_server)
        btnSend = findViewById(R.id.content_socket_btn_send)
        val service = Intent(this, TCPService::class.java)
        startService(service)
        launch(Dispatchers.IO) {
            while (!this@SocketActivity.isFinishing) {
                isClosed = false
                connectToServerWithSocket()
            }
        }
    }


    private fun connectToServerWithSocket() {
        var socket: Socket? = null
        while (socket == null && !isFinishing) {
            try {
                launch(Dispatchers.Main) {
                    Util.showToast("Socket connecting")
                }
                socket = Socket(SERVER_IP, 8688)
                mClientSocket = socket
                mPrintWriter = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())), true)
                mPrintWriter!!.println(THIS_ID)
                launch(Dispatchers.Main) {
                    Util.showToast("Socket connected")
                    btnSend!!.isEnabled = true
                }
            } catch (e: IOException) {
                launch(Dispatchers.Main) {
                    Util.showToast("Fail to connect")
                }
                e.printStackTrace()
            }

        }

        try {
            socket?.let {
                val chars = CharArray(512)
                br = BufferedReader(InputStreamReader(socket.getInputStream()))
                while (!this.isFinishing && !isClosed) {
                    val read = br!!.read(chars)
                    Log.e("read", read.toString())
                    var msg = String(chars, 0, read - 1)
                    if (msg.startsWith("received:")) {
                        launch(Dispatchers.Main) {
                            Util.showToast("发送成功")
                        }
                    } else {
                        msg = msg.substring(msg.indexOf(":") + 1, msg.length)
                        mHandler.obtainMessage(RECEIVE_MSG_FROM_SERVICE, msg).sendToTarget()
                        Log.e("msg from server", msg)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            closeSocket()
        }
    }

    fun sendClick(view: View) {
        val txt = edtMsgToServer!!.text.toString()
        if (txt != "" && mPrintWriter != null) {
            val msg = TO_ID.toString() + ":" + txt
            if (msg.toByteArray().size > 512) {
                Util.showToast("消息过长")
                return
            }
            launch(Dispatchers.IO) {
                mPrintWriter!!.println(msg)
            }
            edtMsgToServer!!.setText("")
        }
    }

    override fun onDestroy() {
        Log.e("SocketActivity", "onDestroy")
        closeSocket()
        super.onDestroy()
    }

    override fun finish() {
        Log.e("SocketActivity", "finish")
        super.finish()
    }

    private fun closeSocket() {
        try {
            if (mClientSocket != null && !mClientSocket!!.isClosed) {
                mClientSocket!!.close()
            }
            if (br != null) {
                br!!.close()
            }
            if (mPrintWriter != null) {
                mPrintWriter!!.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun callBack() {
        isClosed = true
    }

    companion object {
        private val RECEIVE_MSG_FROM_SERVICE = 2

        private val THIS_ID = 1
        private val TO_ID = 2
    }
}
