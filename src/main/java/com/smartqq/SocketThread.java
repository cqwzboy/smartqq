package com.smartqq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

public class SocketThread implements Runnable {
    private Socket socket;
    private String name;
    private String sendTo;

    public SocketThread(Socket socket){
        this.socket = socket;
        this.name = String.valueOf(socket.hashCode());
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            PrintStream ps = new PrintStream(socket.getOutputStream());
            String fromClient;
            while ((fromClient=br.readLine()) != null){
                if(fromClient.startsWith("set name ")){
                    System.out.println(QQServer.socketMap.valueOf(socket) + " : " + fromClient);
                    String name = fromClient.substring(
                            fromClient.indexOf("set name ")+9)
                            .trim();
                    this.name = name;
                    QQServer.socketMap.remove(String.valueOf(socket.hashCode()));
                    QQServer.socketMap.put(name, socket);
                    ps.println("set name "+name+" successfully");
                }else if(fromClient.startsWith("send to ")){
                    System.out.println(QQServer.socketMap.valueOf(socket) + " : " + fromClient);
                    sendTo = fromClient.substring(
                            fromClient.indexOf("send to ")+8)
                            .trim();
                    ps.println("set send to "+sendTo+" successfully");
                }else{
                    if(fromClient!=null && !"".equals(fromClient)){
                        System.out.println(name + " send to " + sendTo + " : "+fromClient);
                        String msg = name + "|" + fromClient;
                        Socket target = QQServer.socketMap.get(sendTo);
                        if(target == null){
                            ps.println(sendTo+"已经下线");
                        }else{
                            new PrintStream(target.getOutputStream()).println(msg);
                        }
                    }
                }
            }
        } catch (IOException e) {
            if(e instanceof SocketException){
                if(e.getMessage().contains("Connection reset")){
                    System.out.println("---- "+name+" 已下线 ----");
                    try {
                        socket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    QQServer.socketMap.remove(name);
                    QQServer.threadCache.remove(this);
                    for (SocketThread socketThread : QQServer.threadCache) {
                        if(sendTo.equals(socketThread.getName())){
                            try {
                                new PrintStream(socketThread.getSocket().getOutputStream()).println("对方已下线");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
