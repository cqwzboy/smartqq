package com.smartqq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class QQClient {
    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket("47.106.201.17", 8088);
//        final Socket socket = new Socket("192.168.0.171", 8088);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream(), "UTF-8"));
                    String content;
                    while ((content=br.readLine()) != null){
                        content = new String(content.getBytes("UTF-8"));
                        if(content.contains("|")){
                            String[] contents = content.split("\\|");
                            String fromUser = contents[0];
                            String msg = contents[1];
                            System.out.println(fromUser+": "+msg);
                        }else{
                            System.out.println("from server: "+content);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        BufferedReader sendBuff = new BufferedReader(
                new InputStreamReader(
                        System.in, "UTF-8"));
        String send;
        try{
            PrintStream ps = new PrintStream(socket.getOutputStream(), true, "UTF-8");
            while ((send=sendBuff.readLine()) != null){
                ps.println(send);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
