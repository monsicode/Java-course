package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LuminosityGrayscaleTest {

    private final LuminosityGrayscale grayscale = new LuminosityGrayscale();

    private static final double RED_SCALE = 0.21;
    private static final double GREEN_SCALE = 0.72;
    private static final double BLUE_SCALE = 0.07;


    @TempDir
    File tempFolder;

    @Test
    void testIfProcessIsValid() {
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, new Color(255, 0, 0).getRGB());

        BufferedImage transformedImg = grayscale.process(img);


        int red = (int) (255 * RED_SCALE);
        int green = (int) (0 * GREEN_SCALE);
        int blue = (int) (0 * BLUE_SCALE);

        int sum = red + green + blue;

        Color transformedImgColor = new Color(transformedImg.getRGB(0, 0));

        assertEquals(sum, transformedImgColor.getRed());
        assertEquals(sum, transformedImgColor.getGreen());
        assertEquals(sum, transformedImgColor.getBlue());

    }

}
