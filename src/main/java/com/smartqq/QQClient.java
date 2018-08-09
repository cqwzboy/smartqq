package com.smartqq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class QQClient {
    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket("192.168.31.112", 8088);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));
                    String content;
                    while ((content=br.readLine()) != null){
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
                        System.in));
        String send;
        try{
            PrintStream ps = new PrintStream(socket.getOutputStream());
            while ((send=sendBuff.readLine()) != null){
                ps.println(send);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
