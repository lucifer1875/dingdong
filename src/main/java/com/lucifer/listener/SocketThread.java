package com.lucifer.listener;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * Created by lucifer on 2017/12/12.
 */
public class SocketThread implements Runnable {
    protected static Logger logger4J = Logger.getLogger("SocketThread");
    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
        new Thread(this).start();
    }


    @Override
    public void run() {

        try {

            System.out.println("source ip : " + socket.getInetAddress());
            System.out.println("source port : " + socket.getPort());




            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            StringBuffer buffer = new StringBuffer();
            while(null != (info = br.readLine())){
                logger4J.info("get info is : " + info);
                buffer.append(info);
            }

//            String result = buffer.toString();
//            if (-1 != result.indexOf("MAC")){
//                br.close();
//                is.close();
//                socket.close();
//                logger4J.info("connection is over ...");
//                return ;
//            }

            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("MAC_REQUEST");
            pw.flush();
            socket.shutdownOutput();

            logger4J.info("writing is over!");

            br.close();
            is.close();
            os.close();
            pw.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
