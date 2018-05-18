package com.yesmanchik.camerastreamer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class ImageSender {

    private static Logger log = Logger.getLogger(ImageSender.class.getName());

    private String host;
    private int port;
    private ServerSocket server;
    private Socket socket;
    private OutputStream stream;
    private int width;
    private int height;

    public ImageSender(int width, int height) {
        this.host = "";
        this.port = 0;
        this.width = width;
        this.height = height;
    }

    public void send(final byte[] bytes) {
        if (stream == null) return;
        try {
            writeInt(width);
            writeInt(height);
            writeInt(bytes.length);
            stream.write(bytes);
        } catch (IOException e) {
            log.severe(e.getMessage());
            String h = host;
            host = "";
            connect(h, port);
        }
    }

    private void writeInt(int n) throws IOException {
        for (int i = 0; i < 4; i++) {
            stream.write((n >> i * 8) & 0x0ff);
        }
    }

    public void close() {
        try {
            if (stream != null) stream.close();
            if (socket != null) socket.close();
            if (server != null) server.close();
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public void connect(String host, int port) {
        if (this.host.equals(host) && this.port == port) return;
        close();
        try {
            if (host.equals("0.0.0.0")) {
                server = new ServerSocket(port);
                socket = server.accept();
            } else {
                InetAddress address = InetAddress.getByName(host);
                socket = new Socket(address, port);
            }
            stream = socket.getOutputStream();
            this.host = host;
            this.port = port;
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }
}
