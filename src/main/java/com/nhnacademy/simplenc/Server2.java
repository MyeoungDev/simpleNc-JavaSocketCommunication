package com.nhnacademy.simplenc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket socket = null;
        System.out.println("연결 대기중.......");

        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("host : "+socket.getInetAddress()+" | 통신 연결 성공");

        ServerWrite write = new ServerWrite(socket);
        ServerListen listen = new ServerListen(socket);

        write.start();
        listen.start();
    }
}

class ServerWrite extends Thread {

    Socket socket = null;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public ServerWrite(Socket socket) {
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

class ServerListen extends Thread {

    Socket socket = null;

    public ServerListen(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            while(true) {
                System.out.println("Client : " + br.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
