package com.lzw.survival.net;


import android.util.EventLog;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by: lzw.
 * 用于连接 服务器——由eclipse+tomcat搭建的。
 */
public class ConnectServer {
    private static final String TAG = "ConnectServer";
    /**
     * 服务器ip
     */
    private static final String SERVER_IP = "192.168.43.35";
    /**
     * 服务器端口号
     */
    private static final int SERVER_PORT = 8888;

    private DataInputStream input;
    private DataOutputStream out;

    /**
     * 1、连接服务器
     * 2、创建输入/输出流
     */
    public void getConn() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                Log.v(TAG, "正在连接服务器...");

                try {
                    //创建一个流套接字并将其连接到指定主机上的指定端口号
                    socket = new Socket(SERVER_IP, SERVER_PORT);

                    //向服务端发送数据
                    out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF("I am client");

                    //读取服务器端数据
                    input = new DataInputStream(socket.getInputStream());
                    String ret;

                    ret = input.readUTF();
                    Log.v(TAG, "服务端返回的数据:"+ret);

                    out.close();
                    input.close();

                } catch (IOException e) {
                    Log.e(TAG, "客户端异常：");
                    e.printStackTrace();
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            Log.e("客户端finally异常", e.getMessage());
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 向服务器发送字符串
     *
     * @param str
     */
//    public void sendDataToServer(final String str) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    socket = new Socket(SERVER_IP, SERVER_PORT);
//
//                    out = new DataOutputStream(socket.getOutputStream());
//                    out.writeUTF(str);
//
//                    out.close();
//                } catch (IOException e) {
//                    Log.e(TAG, "客户端异常：");
//                    e.printStackTrace();
//                } finally {
//                    if (socket != null) {
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            socket = null;
//                            Log.e("客户端finally异常", e.getMessage());
//                        }
//                    }
//                }
//            }
//        }).start();
//    }

    /**
     * 从服务器读取数据
     */
//    public void getDataFromServer() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    String ret;
//
//                    try {
//                        socket = new Socket(SERVER_IP, SERVER_PORT);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (socket != null) {
//                            try {
//                                socket.close();
//                            } catch (IOException e) {
//                                socket = null;
//                                Log.e("客户端finally异常", e.getMessage());
//                            }
//                        }
//                    }
//                }
//            }
//        }).start();
//    }
}
