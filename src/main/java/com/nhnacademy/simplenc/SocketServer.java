package com.nhnacademy.simplenc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer extends Thread {

    static ArrayList<Socket> list = new ArrayList<>();
    static Socket socket = null;

    public SocketServer(Socket socket) {
        this.socket = socket;
        list.add(socket);
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            OutputStream out = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(out, true);

            pw.println("서버에 연결되었습니다. ID를 입력해주세요");

            String readValue;
            String name = null;
            boolean identify = false;

            while ((readValue = br.readLine()) != null) {
                if (!identify) {
                    name = readValue;
                    identify = true;
                    pw.println(name + " 입장");
                    continue;
                }

                for (int i = 0; i < list.size(); i++) {
                    out = list.get(i).getOutputStream();
                    pw = new PrintWriter(out, true);

                    pw.println(name + " : " + readValue);
                }
            }
       } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        try {

            String startCommend = "snc";
            String localHost = "-l";
            int socketPort = 0;

            if (!args[0].equals(startCommend)) {
                throw new RuntimeException("잘못된 명령어 입니다.");
            }

            if (!args[1].equals(localHost)) {
                throw new RuntimeException("잘못된 명령어 입니다.");
            }

            socketPort = Integer.parseInt(args[2]);

            ServerSocket serverSocket = new ServerSocket(socketPort);
            System.out.println("socket: " + socketPort + " 으로 서버 열림 ");

            while (true) {
                Socket socketUser = serverSocket.accept();
                Thread thread = new SocketServer(socketUser);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
