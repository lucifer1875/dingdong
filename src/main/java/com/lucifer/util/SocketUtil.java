package com.lucifer.util;

import java.io.*;
import java.net.Socket;

/**
 * Created by lucifer on 2018/1/9.
 */
public class SocketUtil {

    public static String send (String msg){
        String serverUrl = "39.106.166.24";
        int serverPart = 8001;

        try {
            Socket socket = new Socket(serverUrl, serverPart);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(msg);
            printWriter.flush();
            socket.shutdownOutput();

            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String info = null;
            StringBuffer buffer = new StringBuffer();
            while (null != (info = bufferedReader.readLine())){
                System.out.println("get server reply info is : " + info);
                buffer.append(info);
            }

            bufferedReader.close();
            inputStream.close();
            printWriter.close();
            outputStream.close();
            socket.close();

            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
