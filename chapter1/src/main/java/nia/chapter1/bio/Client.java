package nia.chapter1.bio;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;

    public Client(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (null == socket) {
            throw new RuntimeException("socket is null");
        }
        System.out.println("客户端发送消息:" + message);
        try (PrintWriter outputStreamWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (true) {
                outputStreamWriter.println(message);
                String reply = inputStreamReader.readLine();
                System.out.println("收到服务端回复:" + reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

