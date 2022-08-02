package com.source.yume;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class ImageController {
    public static byte[] changeBackground(BufferedImage bufferedImage) throws IOException {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int colB = new Color(255, 255, 255).getRGB();
        int colN = new Color(1, 1, 1).getRGB();
        int colMoyen = (colB + colN) / 2;
        byte[] imgOutput = new byte[0];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int k = bufferedImage.getRGB(x, y);

                if (k <= colMoyen)
                    bufferedImage.setRGB(x, y, colN);
                if (k > colMoyen)
                    bufferedImage.setRGB(x, y, colB);

            }
        }
        ByteArrayOutputStream byteArrayImage = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayImage);
        imgOutput = byteArrayImage.toByteArray();
        return imgOutput;
    }

    @PostMapping(
            path = "/",
            consumes = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE},
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public byte[] ImageController(@RequestBody byte[] image) throws IOException {
        ByteArrayInputStream output = new ByteArrayInputStream(image);
        return changeBackground(ImageIO.read(output));
    }
}
