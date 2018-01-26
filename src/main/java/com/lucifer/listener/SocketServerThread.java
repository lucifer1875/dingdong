package com.lucifer.listener;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lucifer on 2017/12/14.
 */
public class SocketServerThread implements Runnable{

    protected static Logger logger4J = Logger.getLogger("SocketServerThread");
    private static HashMap<String, byte[]> map = new HashMap<>();
    private List<Socket> sockets;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean isConnect = false;

    public SocketServerThread(Socket socket, List<Socket> sockets) {
        this.socket = socket;
        this.sockets = sockets;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            isConnect = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(this).start();
    }

    static{
        map.put("OPEN_NET", hex2byte("5CC5FF010122"));
        map.put("CLOSE_NET", hex2byte("5CC5FF010223"));
    }


    @Override
    public void run() {
        logger4J.info("server thread is running !");


        while (isConnect){
            try {
                String result = readProcess(inputStream);
                logger4J.info("get info is ：" + result);
                if (null == result){
                    return ;
                }
                if (-1 != result.indexOf("send")){
                    String order = result.substring(5, result.length());
                    for (int i = 0; i < sockets.size(); i++) {
                        sendProcess(order, sockets.get(i));
                    }
                }
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {

            }
        }




    }


    /**
     * 读取信息
     */
    public String readProcess(InputStream inputStream){
        try {
            String info = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            while(null != (info = br.readLine())){
                buffer.append(info);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送指令
     */
    public void sendProcess(String s, Socket socket){

        if (null == s || "".equals(s)){
            return ;
        }

        try {
            socket.getOutputStream().write(map.get(s));
            logger4J.info("writing is over!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] hex2byte(String hex) {
        String digital = "0123456789ABCDEF";
        String hex1 = hex.replace(" ", "");
        char[] hex2char = hex1.toCharArray();
        byte[] bytes = new byte[hex1.length() / 2];
        byte temp;
        for (int p = 0; p < bytes.length; p++) {
            temp = (byte) (digital.indexOf(hex2char[2 * p]) * 16);
            temp += digital.indexOf(hex2char[2 * p + 1]);
            bytes[p] = (byte) (temp & 0xff);
        }
        return bytes;
    }
}
