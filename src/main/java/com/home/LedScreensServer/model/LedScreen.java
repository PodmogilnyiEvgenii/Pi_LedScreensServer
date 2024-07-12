package com.home.LedScreensServer.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Component
public class LedScreen {
    private int width;
    private int height;
    @Getter
    private BufferedImage screenFrame;

    public LedScreen() {
        this.width = 128;
        this.height = 640;
        this.screenFrame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = screenFrame.createGraphics();
        graphics2D.setColor(Color.RED);
        graphics2D.fillRect(0, 0, width, height);

        try {
            Font font1 = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("/fonts/newgen9.ttf").getInputStream()).deriveFont(9f);
            Font font2 = Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("/fonts/newgen9.ttf").getInputStream()).deriveFont(18f);

            graphics2D.setColor(Color.YELLOW);
            graphics2D.setFont(font1);
            graphics2D.drawString("АБВГДЕЁЖЗИЙКЛМН", 1, 20);

            graphics2D.setColor(Color.GREEN);
            graphics2D.setFont(font2);
            graphics2D.drawString("АБВГДЕЁЖЗИЙКЛМН", 1, 60);
            graphics2D.dispose();
            log.debug(String.valueOf(screenFrame.getRGB(0,0)));


        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setScreenProperties(int numberScreens) {
        //if (height / 64 < numberScreens) {
            height = 10 * 64;
            screenFrame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = screenFrame.createGraphics();
            graphics2D.setColor(Color.BLUE);
            graphics2D.fillRect(0, 0, width, height);
        //}
    }

    public String screenToBase64String() {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            ImageIO.write(screenFrame, "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String bigscreenToBase64String() {
        int scaleRate = 3;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            AffineTransform transform = new AffineTransform();
            transform.scale(scaleRate, scaleRate);
            AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage screen = new BufferedImage(width * scaleRate, height * scaleRate, BufferedImage.TYPE_INT_RGB);
            screen = transformOp.filter(screenFrame, screen);
            ImageIO.write(screen, "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
