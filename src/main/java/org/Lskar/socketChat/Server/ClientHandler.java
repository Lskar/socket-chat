package org.Lskar.socketChat.Server;
import org.Lskar.socketChat.Utils.DatabaseUtil;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int userId;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            String request = (String) in.readObject();
            if (request.startsWith("LOGIN")) {
                handleLogin(request);
            } else if (request.startsWith("REGISTER")) {
                handleRegister(request);
            } else if (request.startsWith("GET_FRIENDS")) {
                sendOnlineFriends(userId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLogin(String request) throws Exception {
        String[] parts = request.split(":");
        String username = parts[1];
        String password = parts[2];

        // 查询数据库验证用户
        Map<String, Object> result = DatabaseUtil.loginUser(username, password);
        if ((Boolean) result.get("success")) {
            userId = (Integer) result.get("userId");
            DatabaseUtil.updateUserStatus(userId, "ONLINE");
            out.writeObject("LOGIN_SUCCESS:" + userId);
            sendOnlineFriends(userId);
        } else {
            out.writeObject("LOGIN_FAILED");
        }
    }

    private void handleRegister(String request) throws Exception {
        String[] parts = request.split(":");
        String username = parts[1];
        String password = parts[2];

        boolean success = DatabaseUtil.registerUser(username, password);
        if (success) {
            out.writeObject("REGISTER_SUCCESS");
        } else {
            out.writeObject("REGISTER_FAILED");
        }
    }

    private void sendOnlineFriends(int userId) throws IOException {
        List<Integer> onlineFriends = DatabaseUtil.getOnlineFriends(userId);
        StringBuilder sb = new StringBuilder("ONLINE_FRIENDS:");
        for (int friendId : onlineFriends) {
            sb.append(friendId).append(",");
        }
        out.writeObject(sb.toString());
    }
}