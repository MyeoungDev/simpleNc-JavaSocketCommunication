package com.nhnacademy.edu.project.simplenc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocketClient {
    public static void main(String[] args) {

        try {

            Socket socket = null;

            if (!args[0].equals("snc")) {
                throw new RuntimeException("잘못된 명령어 입니다.");
            }

            if (!args[1].equals("127.0.0.1")) {
                throw new RuntimeException("잘못된 명령어 입니다.");
            }

            String ip = args[1];
            int port = Integer.parseInt(args[2]);


            socket = new Socket(ip, port);
            System.out.println("서버 접속 완료");

            ListeningThread listen = new ListeningThread(socket);
            WirtingThread write = new WirtingThread(socket);

            listen.start();
            write.start();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
