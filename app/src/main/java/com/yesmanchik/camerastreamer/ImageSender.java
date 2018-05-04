package com.yesmanchik.camerastreamer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ImageSender {

    private Socket socket;
    private OutputStream stream;
    private int width;
    private int height;

    public ImageSender(String host, int port, int width, int height) {
        try {
            this.width = width;
            this.height = height;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
            stream = socket.getOutputStream();
        } catch (Exception e) {
        }

    }

    public void send(final byte[] bytes) {
        if (stream == null) return;
        try {
            writeInt(width);
            writeInt(height);
            writeInt(bytes.length);
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeInt(int n) throws IOException {
        for (int i = 0; i < 4; i++) {
            stream.write((n >> i * 8) & 0x0ff);
        }
    }

    public void close() {
        try {
            stream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
