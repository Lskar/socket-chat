package org.Lskar.socketChat.Utils;
import java.net.*;
import java.io.*;

public class UDPChatHandler {
    private DatagramSocket socket;
    private int localPort = 9999;

    public UDPChatHandler() throws SocketException {
        socket = new DatagramSocket(localPort);
        new Thread(this::listenForMessages).start();
    }

    public void sendMessage(String message, InetAddress address, int port) throws IOException {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }

    private void listenForMessages() {
        byte[] buffer = new byte[1024];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("收到消息: " + received);
                // 更新聊天界面
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
