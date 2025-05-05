package org.Lskar.socketChat.Client;
import org.Lskar.socketChat.GUI.ChatFrame;
import org.Lskar.socketChat.GUI.LoginFrame;

import java.net.*;
import java.io.*;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        new LoginFrame(); // 显示登录界面
    }

    public static void connectToServer(String message) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(message);
            String response = (String) in.readObject();
            System.out.println("服务器响应: " + response);

            if (response.startsWith("LOGIN_SUCCESS")) {
                int userId = Integer.parseInt(response.split(":")[1]);
                new ChatFrame(userId); // 打开聊天界面
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
