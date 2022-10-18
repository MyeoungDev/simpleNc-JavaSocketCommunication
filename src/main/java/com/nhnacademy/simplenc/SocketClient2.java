package com.nhnacademy.simplenc;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient2 {


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

            ListeningThread2 listen = new ListeningThread2(socket);
            WritingThread2 write = new WritingThread2(socket);

            listen.start();
            write.start();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class WritingThread2 extends Thread {

    Socket socket = null;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public WritingThread2(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(out, true);

            while (true) {
                pw.println(br.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class ListeningThread2 extends Thread {

    Socket socket = null;

    public ListeningThread2(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            while(true) { //서버에서 메세지를 보냈을 경우 바로 읽어옴
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}