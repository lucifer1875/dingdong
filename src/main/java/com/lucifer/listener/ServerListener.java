package com.lucifer.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucifer on 2017/12/12.
 */
public class ServerListener implements ServletContextListener {

    private static List<Socket> sockets = new ArrayList<>();
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            ServerSocket serverSocket = new ServerSocket(8001);
            while (true){
                System.out.println("server is running ");
                Socket client = serverSocket.accept();
                sockets.add(client);
                new SocketServerThread(client, sockets);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
