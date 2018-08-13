package com.smartqq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SocketThread implements Runnable {
    private Socket socket;
    private String name;
    private Set<String> sendTo = new HashSet<>();

    public SocketThread(Socket socket){
        this.socket = socket;
        this.name = String.valueOf(socket.hashCode());
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(), "UTF-8"));
            PrintStream ps = new PrintStream(socket.getOutputStream(), true, "UTF-8");
            String fromClient;
            while ((fromClient=br.readLine()) != null){
                fromClient = new String(fromClient.getBytes("UTF-8"));
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
                    String sendToStr = fromClient.substring(
                            fromClient.indexOf("send to ")+8)
                            .trim();
                    if(sendToStr==null || "".equals(sendToStr)){
                        ps.println("param must not be empty");
                    }else{
                        String[] sendToArray = sendToStr.split(",");
                        sendTo.clear();
                        for (String send : sendToArray) {
                            sendTo.add(send.trim());
                        }
                        ps.println("set send to "+sendTo+" successfully");
                    }
                }else if(fromClient.startsWith("show online")){
                    System.out.println(QQServer.socketMap.valueOf(socket) + " : " + fromClient);
                    StringBuilder online = new StringBuilder();
                    for (String key : QQServer.socketMap.keySet()) {
                        online.append(key).append(",");
                    }
                    online.setLength(online.length() - 1);
                    ps.println(online.toString());
                }else if(fromClient.startsWith("show sendTo")){
                    System.out.println(QQServer.socketMap.valueOf(socket) + " : " + fromClient);
                    ps.println(sendTo.toString());
                }else if(fromClient.startsWith("whoami")){
                    System.out.println(QQServer.socketMap.valueOf(socket) + " : " + fromClient);
                    ps.println(this.name);
                }else if(fromClient.startsWith("help")){
                    System.out.println(QQServer.socketMap.valueOf(socket) + " : " + fromClient);
                    ps.println("-----------------------------------");
                    ps.println("set name <user_name> 取名");
                    ps.println("whoami 查看当前用户名");
                    ps.println("show online 查看在线用户");
                    ps.println("send to <receiver> 指定被发送者");
                    ps.println("show sendTo 显示设置的被发送者");
                    ps.println("-----------------------------------");
                }else{
                    if(fromClient!=null && !"".equals(fromClient)){
                        if(sendTo.isEmpty()){
                            ps.println("请设置被发送者");
                        }else{
                            System.out.println(name + " send to " + sendTo + " : "+fromClient);
                            String msg = name + "|" + fromClient;
                            for (String send : sendTo) {
                                Socket target = QQServer.socketMap.get(send);
                                if(target == null){
                                    ps.println(send+"已经下线");
                                }else{
                                    new PrintStream(target.getOutputStream()).println(msg);
                                }
                            }
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
                        if(sendTo.contains(socketThread.getName())){
                            try {
                                new PrintStream(socketThread.getSocket().getOutputStream()).println(this.name+"已下线");
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

    public Set<String> getSendTo() {
        return sendTo;
    }

    public void setSendTo(Set<String> sendTo) {
        this.sendTo = sendTo;
    }
}
