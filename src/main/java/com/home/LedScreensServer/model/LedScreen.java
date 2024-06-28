package com.home.LedScreensServer.model;

import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


public class LedScreen {
    private int width;
    private int height;
    @Getter private BufferedImage ledScreen;

    public LedScreen(int width, int height) {
        this.width = width;
        this.height = height;
        this.ledScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = ledScreen.createGraphics();
        graphics2D.setColor(Color.RED);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.dispose();
        graphics2D.setColor(Color.YELLOW);
        graphics2D.setFont(new Font("TimesRoman", Font.BOLD, 25));
        graphics2D.drawString("1234", 1, 30);
    }

    public String screenToBase64String() {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            ImageIO.write(ledScreen, "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
