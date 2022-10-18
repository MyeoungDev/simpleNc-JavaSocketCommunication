package com.nhnacademy.simplenc;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {
    public static void main(String[] args) throws IOException {

        String serverHostname = "127.0.0.1";
        Socket echoSocket = null;

        try {
            echoSocket = new Socket(serverHostname, 3000);

            ClientWrite write = new ClientWrite(echoSocket);
            ClientListen listen = new ClientListen(echoSocket);

            write.start();
            listen.start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

class ClientWrite extends Thread {

    Socket socket = null;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public ClientWrite(Socket socket) {
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

class ClientListen extends Thread {

    Socket socket = null;

    public ClientListen(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            while(true) {
                System.out.println("Server : " + br.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

