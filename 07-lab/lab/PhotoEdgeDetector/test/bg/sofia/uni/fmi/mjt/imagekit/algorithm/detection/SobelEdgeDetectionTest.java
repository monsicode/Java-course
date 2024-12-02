package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SobelEdgeDetectionTest {

    @TempDir
    File tempFolder;

    private static final int BLACK = 0x000000;
    private static final int WHITE = 0xFFFFFF;
    private static final int THRESHOLD = 50;

    private BufferedImage createTestImage() {
        BufferedImage image = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, WHITE);
        image.setRGB(1, 0, WHITE);
        image.setRGB(2, 0, WHITE);
        image.setRGB(3, 0, WHITE);
        image.setRGB(4, 0, WHITE);

        image.setRGB(0, 1, BLACK);
        image.setRGB(1, 1, WHITE);
        image.setRGB(2, 1, WHITE);
        image.setRGB(3, 1, WHITE);
        image.setRGB(4, 1, BLACK);

        image.setRGB(0, 2, BLACK);
        image.setRGB(1, 2, BLACK);
        image.setRGB(2, 2, BLACK);
        image.setRGB(3, 2, BLACK);
        image.setRGB(4, 2, BLACK);

        image.setRGB(0, 3, WHITE);
        image.setRGB(1, 3, WHITE);
        image.setRGB(2, 3, WHITE);
        image.setRGB(3, 3, WHITE);
        image.setRGB(4, 3, WHITE);

        image.setRGB(0, 4, WHITE);
        image.setRGB(1, 4, WHITE);
        image.setRGB(2, 4, WHITE);
        image.setRGB(3, 4, WHITE);
        image.setRGB(4, 4, WHITE);

        return image;
    }

    @Test
    void testProcessWithSimpleImg() {
        BufferedImage img = createTestImage();

        SobelEdgeDetection sobel = new SobelEdgeDetection(new LuminosityGrayscale());
        BufferedImage resultImage = sobel.process(img);

        int centerPixel = resultImage.getRGB(2, 2) & 0xFF;
        assertTrue(centerPixel <= THRESHOLD, "Expected no edge at center");

    }
}
