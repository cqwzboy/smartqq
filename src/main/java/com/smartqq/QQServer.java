package com.smartqq;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QQServer {

    public static QQMap<String, Socket> socketMap = new QQMap<>();
    public static List<SocketThread> threadCache = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("server start successfully...");
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("receive  a new user ["+socket.hashCode()+"]");
            socketMap.put(String.valueOf(socket.hashCode()), socket);
            SocketThread socketThread = new SocketThread(socket);
            threadCache.add(socketThread);
            new Thread(socketThread).start();
        }
    }
}
