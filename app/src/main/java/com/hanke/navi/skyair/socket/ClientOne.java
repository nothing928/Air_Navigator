package com.hanke.navi.skyair.socket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Asus on 2017/2/16.
 */
public class ClientOne {
    static final String TAG = "123";
    String ip = "192.168.10.68";
    int port = 8000;
    Socket socket;
    InputStream inputStream;
    DataInputStream input;

    public ClientOne() {

    }

    public void initData(){
    new Thread(){
        @Override
        public void run() {
//            super.run();
            try {
//                try {
                    socket = new Socket(ip,port);
                    socket.setSoTimeout(5000);
                Log.e(TAG,"socket = "+socket.toString());

//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                Log.e(TAG,"连接上服务器");
                inputStream = socket.getInputStream();
                input = new DataInputStream(inputStream);
                Log.e(TAG,"input = "+input.toString());
                byte[] b = new byte[10000];
                while(true) {
                    int length = input.read(b);
                    String Msg = new String(b, 0, length, "gb2312");
//                    String Msg = new String(b, 0, length);
                    Log.e(TAG,"接收的数据 = "+Msg);
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }.start();

    }

    public void LiuClose(){
        try {
            input.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
