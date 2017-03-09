package com.hanke.navi.skyair.socket;

import android.os.Message;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ToClient {
    static final String TAG = "123";
    String ip = "192.168.10.68";
    int port = 8000;
    Socket socket;
    BufferedReader br;
    BufferedReader in;
    int n;

    public ToClient() {

    }

    public void initView(){

    }
    public void initData(){
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                Log.e(TAG,"11111");
                new Thread(){
                    public void run(){
                        try {
                            socket = new Socket(ip,port);
                            socket.setSoTimeout(5000);
                            Log.e(TAG,"连接上服务器");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                        InputStream in = null;
                        try {
//                            in = socket.getInputStream();
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        boolean b = true;
                        String line = "";
                        while (b){
                            try{
                                if (socket!=null){
                                    Log.e(TAG,"检测到数据");
                                    while ((line = in.readLine())!=null){
                                        Log.e(TAG,"得到数据 = "+line+"\n"+"  ,其长度为 ="+line.length());

                                    }
                                }
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
//                        PrintStream pt = new PrintStream(out);
//                        br = new BufferedReader(new InputStreamReader(in));

//                        boolean b = true;
//                        while (b){
//                                //从服务器获得到内容
//                                String info = null;
//                                try {
//                                    info = br.readLine();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                Log.e(TAG,"内容为 = "+info);
//
//                            //若是以字符串的形式接收
////                            int chang = info.length();
//                            String times = info.substring(0,9);
//                            Log.e(TAG,"times ="+times);
//                            String weidus = info.substring(9,20);
//                            Log.e(TAG,"weidus ="+weidus);
//                            String Ns = info.substring(20,21);
//                            Log.e(TAG,"Ns ="+Ns);
//                            String jingdus = info.substring(21,33);
//                            Log.e(TAG,"jingdus ="+jingdus);
//                            String Es = info.substring(33,34);
//                            Log.e(TAG,"Es ="+Es);
//                            String gaodus = info.substring(34,39);
//                            Log.e(TAG,"gaodus ="+gaodus);
//                            String dates = info.substring(39,45);
//                            Log.e(TAG,"dates ="+dates);
//                            String sudus = info.substring(45,info.indexOf(","));
//                            Log.e(TAG,"sudus ="+sudus);
//                            String douhaos1 = info.substring(info.indexOf(","),info.indexOf(",")+1);
//                            Log.e(TAG,"douhaos1 ="+douhaos1);
//                            String feijihaos=info.substring(info.indexOf(",")+1,info.indexOf(","));
//                            Log.e(TAG,"feijihaos ="+feijihaos);
//                            String douhaos2=info.substring(info.indexOf(","),info.indexOf(",")+1);
//                            Log.e(TAG,"douhaos2 ="+douhaos2);
//                            String baolius1=info.substring(info.indexOf(",")+1,info.indexOf(","));
//                            Log.e(TAG,"baolius1 ="+baolius1);
//                            String douhaos3=info.substring(info.indexOf(","),info.indexOf(",")+1);
//                            Log.e(TAG,"douhaos3 ="+douhaos3);
//                            String baolius2=info.substring(info.indexOf(",")+1,info.indexOf(","));
//                            Log.e(TAG,"baolius2 ="+baolius2);
//                            String douhaos4=info.substring(info.indexOf(","),info.indexOf(",")+1);
//                            Log.e(TAG,"douhaos4 ="+douhaos4);
//                            String baolius3=info.substring(info.indexOf(",")+1,info.indexOf(","));
//                            Log.e(TAG,"baolius3 ="+baolius3);
//                            String douhaos5=info.substring(info.indexOf(","),info.indexOf(",")+1);
//                            Log.e(TAG,"douhaos5 ="+douhaos5);
//                            String baolius4=info.substring(info.indexOf(",")+1,info.indexOf(","));
//                            Log.e(TAG,"baolius4 ="+baolius4);
//                            String douhaos6=info.substring(info.indexOf(","),info.indexOf(",")+1);
//                            Log.e(TAG,"douhaos6 ="+douhaos6);
//                            try {
//                                //若是字节接收
////                            byte[] by = info.getBytes();
//                                byte[] time=new byte[9];
//                                in.read(time);
//                                Log.e(TAG,"time ="+time);
//                                byte[] weidu=new byte[11];
//                                in.read(weidu);
//                                Log.e(TAG,"weidu ="+weidu);
//                                byte[] N=new byte[1];
//                                in.read(N);
//                                Log.e(TAG,"N ="+N);
//                                byte[] jingdu=new byte[12];
//                                in.read(jingdu);
//                                Log.e(TAG,"jingdu ="+jingdu);
//                                byte[] E=new byte[1];
//                                in.read(E);
//                                Log.e(TAG,"E ="+E);
//                                byte[] gaodu=new byte[5];
//                                in.read(gaodu);
//                                Log.e(TAG,"gaodu ="+gaodu);
//                                byte[] date=new byte[6];
//                                in.read(date);
//                                Log.e(TAG,"date ="+date);
//                                if (n<=50){
//                                    byte[] sudu=new byte[n];
//                                    in.read(sudu);
//                                    Log.e(TAG,"sudu ="+sudu);
//                                    byte[] douhao1=new byte[1];
//                                    in.read(douhao1);
//                                    Log.e(TAG,"douhao1 ="+douhao1);
//                                    byte[] feijihao=new byte[n];
//                                    in.read(feijihao);
//                                    Log.e(TAG,"feijihao ="+feijihao);
//                                    byte[] douhao2=new byte[1];
//                                    in.read(douhao2);
//                                    Log.e(TAG,"douhao2 ="+douhao2);
//                                    byte[] baoliu1=new byte[1];
//                                    in.read(baoliu1);
//                                    Log.e(TAG,"baoliu1 ="+baoliu1);
//                                    byte[] douhao3=new byte[1];
//                                    in.read(douhao3);
//                                    Log.e(TAG,"douhao3 ="+douhao3);
//                                    byte[] baoliu2=new byte[n];
//                                    in.read(baoliu2);
//                                    Log.e(TAG,"baoliu2 ="+baoliu2);
//                                    byte[] douhao4=new byte[1];
//                                    in.read(douhao4);
//                                    Log.e(TAG,"douhao4 ="+douhao4);
//                                    byte[] baoliu3=new byte[n];
//                                    in.read(baoliu3);
//                                    Log.e(TAG,"baoliu3 ="+baoliu3);
//                                    byte[] douhao5=new byte[1];
//                                    in.read(douhao5);
//                                    Log.e(TAG,"douhao5 ="+douhao5);
//                                    byte[] baoliu4=new byte[n];
//                                    in.read(baoliu4);
//                                    Log.e(TAG,"baoliu4 ="+baoliu4);
//                                    byte[] douhao6=new byte[1];
//                                    in.read(douhao6);
//                                    Log.e(TAG,"douhao6 ="+douhao6);
//                                }
//
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            Log.e(TAG,"yi ="+time[0]);
//                            Log.e(TAG,"er ="+time[1]);
//                            Log.e(TAG,"san ="+time[2]);
//                            Log.e(TAG,"si ="+time[3]);
//                        }
//                        pt.close();
//                            br.close();
//                            socket.close();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                    }
                }.start();


    }
    public void LiuClose(){
        try {
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
