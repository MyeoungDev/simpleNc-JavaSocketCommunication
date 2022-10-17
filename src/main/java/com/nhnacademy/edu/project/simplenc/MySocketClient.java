package com.nhnacademy.edu.project.simplenc;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocketClient {
    public static void main(String[] args) {

        try {
            Socket socket = null;

            socket = new Socket("127.0.0.1", 3000);
            System.out.println("서버 접속 완료");


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
