package com.nhnacademy.edu.project.simplenc;

import sun.misc.Signal;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MySocketServer extends Thread {

    static ArrayList<Socket> list = new ArrayList<>();
    static Socket socket = null;

    public MySocketServer(Socket socket) {
        this.socket = socket;
        list.add(socket);
    }

    @Override
    public void run() {
        try {
            System.out.println("서버 : " + socket.getInetAddress() + " IP의 클라이언트와 연결되었습니다." +
                    "port :" + socket.getPort());

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


            /* Ctrl + C 로 종료시키려 했으나 먹지 않음.... */
//            Runtime.getRuntime().addShutdownHook(new Thread(){
//                public void run() {
//                    System.out.println("test");
//                    System.exit(0);
//                }
//            });

//            Signal.handle(new Signal("INT"),
//                    sig -> System.out.println("Interrupted by Ctrl+C"));


            System.out.println("명령어를 입력하여 주세요. (ex: snc -l <using port num>");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String startingLine = br.readLine();

            String[] commends = startingLine.split(" ");

            String startCommend = "snc";
            String localHost = "-l";
            int socketPort = 0;

            if (!commends[0].equals(startCommend)) {
                throw new RuntimeException("잘못된 명령어 입니다.");
            }

            if (!commends[1].equals(localHost)) {
                throw new RuntimeException("잘못된 명령어 입니다.");
            }

            socketPort = Integer.parseInt(commends[2]);

            ServerSocket serverSocket = new ServerSocket(socketPort);
            System.out.println("socket: " + socketPort + " 으로 서버 열림 ");

            while (true) {
                Socket socketUser = serverSocket.accept();
                Thread thread = new MySocketServer(socketUser);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
