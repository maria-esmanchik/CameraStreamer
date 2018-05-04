package com.yesmanchik.camerastreamer;

import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class ImageSenderTest {
    @Test
    public void connectToServer() throws Exception {
        ImageSender sender = new ImageSender();
        sender.send("1234".getBytes());
        //Thread.sleep(5000);
        sender.close();
    }
}