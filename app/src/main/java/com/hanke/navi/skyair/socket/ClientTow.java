package com.hanke.navi.skyair.socket;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

public class ClientTow extends AsyncTask<Void, Void, String> {
    static final String TAG = "123";
    String ip = "192.168.10.68";
    int port = 8000;
    String Msg;
    Socket socket;

    public ClientTow() {
    }

    @Override
    protected String doInBackground(Void... param) {
        try {
            Log.e(TAG, "&&&&&&&&&&&&&");
            try {
                socket = new Socket(ip, port);
            } catch (Exception e) {
//                Log.e(TAG,"连接失败，原因："+e.getMessage());
                e.printStackTrace();
            }

            Log.e(TAG, "连接上服务器");
            InputStream inputStream = socket.getInputStream();
            DataInputStream input = new DataInputStream(inputStream);
            byte[] b = new byte[10000*1024];
            boolean isRun = true;
            while (isRun) {
                if (socket!=null){
                    while (input.read(b)!=-1){
                        int length = input.read(b);
                        Log.e(TAG, "接收数据的长度为 = " + length);
                        Msg = new String(b, 0, length);
                        Log.e(TAG+"123", "接收的数据 = " + Msg);

                        String nr[] = Msg.split(",");
                        Log.e(TAG, "按逗号拆分的数据nr[0] = " + nr[0]);
                        Log.e(TAG, "按逗号拆分的数据nr[1] = " + nr[1]);
                        Log.e(TAG, "按逗号拆分的数据nr[2] = " + nr[2]);
                        Log.e(TAG, "按逗号拆分的数据nr[3] = " + nr[3]);
                        Log.e(TAG, "按逗号拆分的数据nr[4] = " + nr[4]);
                        Log.e(TAG, "按逗号拆分的数据nr[5] = " + nr[5]);

                        String tous =nr[0].substring(0,6);
                        Log.e(TAG, "tous =" + tous);
                        String times = nr[0].substring(6, 15);
                        Log.e(TAG, "times =" + times);
                        String weidus = nr[0].substring(15, 26);
                        Log.e(TAG, "weidus =" + weidus);
                        String Ns = nr[0].substring(26, 27);
                        Log.e(TAG, "Ns =" + Ns);
                        String jingdus = nr[0].substring(27, 39);
                        Log.e(TAG, "jingdus =" + jingdus);
                        String Es = nr[0].substring(39, 40);
                        Log.e(TAG, "Es =" + Es);
                        String gaodus = nr[0].substring(40, 45);
                        Log.e(TAG, "gaodus =" + gaodus);
                        String dates = nr[0].substring(45, 51);
                        Log.e(TAG, "dates =" + dates);
                        String sudus = Msg.substring(51, nr[0].length());
//                String sudus = Msg.substring(51, Msg.indexOf(","));
                        Log.e(TAG, "sudus =" + sudus);
                        String douhaos1 = Msg.substring(Msg.indexOf(","), Msg.indexOf(",") + 1);
                        Log.e(TAG, "douhaos1 =" + douhaos1);
//                String feijihaos = Msg.substring(Msg.indexOf(",") + 1, Msg.indexOf(","));
                        String feijihaos = nr[1];
                        Log.e(TAG, "feijihaos =" + feijihaos);
//                String douhaos2 = Msg.substring(Msg.indexOf(","), Msg.indexOf(",") + 1);
//                Log.e(TAG, "douhaos2 =" + douhaos2);
//                String baolius1 = Msg.substring(Msg.indexOf(",") + 1, Msg.indexOf(","));
                        String baolius1 = nr[2];
                        Log.e(TAG, "baolius1 =" + baolius1);
                        String douhaos3 = Msg.substring(Msg.indexOf(","), Msg.indexOf(",") + 1);
                        Log.e(TAG, "douhaos3 =" + douhaos3);
//                String baolius2 = Msg.substring(Msg.indexOf(",") + 1, Msg.indexOf(","));
                        String baolius2 = nr[3];
                        Log.e(TAG, "baolius2 =" + baolius2);
                        String douhaos4 = Msg.substring(Msg.indexOf(","), Msg.indexOf(",") + 1);
                        Log.e(TAG, "douhaos4 =" + douhaos4);
//                String baolius3 = Msg.substring(Msg.indexOf(",") + 1, Msg.indexOf(","));
                        String baolius3 = nr[4];
                        Log.e(TAG, "baolius3 =" + baolius3);
                        String douhaos5 = Msg.substring(Msg.indexOf(","), Msg.indexOf(",") + 1);
                        Log.e(TAG, "douhaos5 =" + douhaos5);
//                String baolius4 = Msg.substring(Msg.indexOf(",") + 1, Msg.indexOf(","));
                        String baolius4 = nr[5];
                        Log.e(TAG, "baolius4 =" + baolius4);
                        String douhaos6 = Msg.substring(Msg.indexOf(","), Msg.indexOf(",") + 1);
                        Log.e(TAG, "douhaos6 =" + douhaos6);
                    }
                }

//                String Msg = new String(b, 0, length, "gb2312");
//                Msg = new String(b, 0, length, "gb2312");






            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Msg;
    }
}