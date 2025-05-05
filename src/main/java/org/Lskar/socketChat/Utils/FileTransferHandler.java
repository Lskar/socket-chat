package org.Lskar.socketChat.Utils;
import java.io.*;
import java.net.*;

public class FileTransferHandler {
    public static void sendFile(String filePath, InetAddress address, int port) {
        try (Socket socket = new Socket(address, port);
             OutputStream out = socket.getOutputStream();
             FileInputStream fis = new FileInputStream(filePath)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveFile(String savePath, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             InputStream in = socket.getInputStream();
             FileOutputStream fos = new FileOutputStream(savePath)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
