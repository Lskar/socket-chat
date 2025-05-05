package org.Lskar.socketChat.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class ChatFrame extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private int userId;

    public ChatFrame(int userId) {
        this.userId = userId;
        setTitle("聊天室 - 用户ID: " + userId);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        JButton sendBtn = new JButton("发送");

        sendBtn.addActionListener(e -> {
            String message = inputField.getText();
            chatArea.append("我: " + message + "\n");
            inputField.setText("");
            // 发送消息到服务器
            sendMessageToServer(message);
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendBtn, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        // 接收服务器消息
        new Thread(this::receiveMessages).start();
    }

    private void sendMessageToServer(String message) {
        // TODO 实现发送消息到服务器的逻辑
    }

    private void receiveMessages() {
        // TODO 实现接收服务器消息并显示
    }
}
