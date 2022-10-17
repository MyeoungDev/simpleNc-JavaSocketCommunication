package com.nhnacademy.edu.project.simplenc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ListeningThread extends Thread {

    Socket socket = null;

    public ListeningThread(Socket socket) {
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
