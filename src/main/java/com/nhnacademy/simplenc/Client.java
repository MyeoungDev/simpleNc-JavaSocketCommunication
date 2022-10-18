package com.nhnacademy.simplenc;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws IOException {

        if (!args[0].equals("snc")) {
            throw new RuntimeException();
        }

        if (!args[1].equals("127.0.0.1")) {
            throw new RuntimeException();
        }

        int serverPort = 0;

        try {
            serverPort = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String serverHostname = "127.0.0.1";
        Socket socket = null;

        try {
            socket = new Socket(serverHostname, serverPort);

            ClientWrite write = new ClientWrite(socket);
            ClientListen listen = new ClientListen(socket);

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

