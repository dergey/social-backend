package com.sergey.zhuravlev.social.util;

import com.sergey.zhuravlev.social.enums.ImageSize;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.CleanupFailureDataAccessException;

import javax.activation.UnsupportedDataTypeException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.function.Function;

public class ImageUtils {

    static {
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("font/Inter-Regular.ttf").getInputStream()));
        } catch (IOException | FontFormatException ignored) {
        }
    }

    public static ImageReader getImageReader(final InputStream inputStream)
            throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (readers.hasNext()) {
            ImageReader imageReader = readers.next();
            imageReader.setInput(iis, true);
            return imageReader;
        }
        throw new UnsupportedDataTypeException("Can't detect file reader");
    }

    @SneakyThrows(value = IOException.class)
    public static byte[] getBufferImageBytes(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "JPEG", baos);
        byte[] data = baos.toByteArray();
        baos.close();
        return data;
    }

    public static BufferedImage generateDefaultAvatar(String letters, ImageSize size) {
        BufferedImage bufferedImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        Color backgroundColor = new Color(
                ((int) (Math.random() * 192)) + 64,
                ((int) (Math.random() * 192)) + 64,
                ((int) (Math.random() * 192)) + 64);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, size.getWidth(), size.getHeight());
        g.setColor(new Color(backgroundColor.getRed() - 32, backgroundColor.getGreen() - 32, backgroundColor.getBlue() - 32));
        Font newFont = getFont("Inter", Font.PLAIN, Math.round(size.getHeight() * 0.66f), (f) -> g.getFontMetrics(f).getHeight());
        g.setFont(newFont);
        int stringWidth = g.getFontMetrics().stringWidth(letters);
        int stringHeight = Math.round(g.getFontMetrics().getHeight() * 0.60f);
        g.drawString(letters, size.getWidth() / 2 - stringWidth / 2, size.getHeight() / 2 + stringHeight / 2);
        g.dispose();
        return bufferedImage;
    }

    public static Font getFont(String name, int style, int height, Function<Font, Integer> fontHeightFunction) {
        int size = height;
        Boolean up = null;
        while (true) {
            Font font = new Font(name, style, size);
            int testHeight = fontHeightFunction.apply(font);
            if (testHeight < height && up != Boolean.FALSE) {
                size++;
                up = Boolean.TRUE;
            } else if (testHeight > height && up != Boolean.TRUE) {
                size--;
                up = Boolean.FALSE;
            } else {
                return font;
            }
        }
    }


}
