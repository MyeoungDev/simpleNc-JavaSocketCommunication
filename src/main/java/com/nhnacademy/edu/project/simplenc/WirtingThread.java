package com.nhnacademy.edu.project.simplenc;

import java.io.*;
import java.net.Socket;

public class WirtingThread extends Thread {

    Socket socket = null;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public WirtingThread(Socket socket) {
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
