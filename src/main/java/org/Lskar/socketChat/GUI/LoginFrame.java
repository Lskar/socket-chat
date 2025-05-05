package org.Lskar.socketChat.GUI;
import org.Lskar.socketChat.Client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("登录");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("用户名:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        panel.add(new JLabel("密码:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("登录");
        JButton registerBtn = new JButton("注册");

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            Client.connectToServer("LOGIN:" + username + ":" + password);
        });

        registerBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            Client.connectToServer("REGISTER:" + username + ":" + password);
        });

        panel.add(loginBtn);
        panel.add(registerBtn);
        add(panel);
        setVisible(true);
    }
}
