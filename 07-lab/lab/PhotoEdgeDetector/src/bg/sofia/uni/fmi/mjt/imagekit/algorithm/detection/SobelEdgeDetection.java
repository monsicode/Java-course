package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {

    private static final int[][] HORIZONTAL_SOBEL_KERNEL = {
        {-1, 0, 1},
        {-2, 0, 2},
        {-1, 0, 1}
    };
    private static final int[][] VERTICAL_SOBEL_KERNEL = {
        {-1, -2, -1},
        {0, 0, 0},
        {1, 2, 1}
    };

    private static final int THRESHOLDING = 90;
    private static final int BLACK = 0;
    private static final int WHITE = 255;


    private final ImageAlgorithm grayscaleAlgorithm;

    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
        this.grayscaleAlgorithm = grayscaleAlgorithm;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Img cannot be null");
        }

        BufferedImage greyImg = grayscaleAlgorithm.process(image);

        return applySobelKernel(greyImg);
    }

    private BufferedImage applySobelKernel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                int gradientX = calculateGradientX(image, x, y);
                int gradientY = calculateGradientY(image, x, y);

                int gradient = (int) Math.sqrt(gradientX * gradientX + gradientY * gradientY);

                int newPixelValue = (gradient > THRESHOLDING) ? WHITE : BLACK;

                Color newColor = new Color(newPixelValue, newPixelValue, newPixelValue);
                resultImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return resultImage;
    }

    private int calculateGradientX(BufferedImage image, int x, int y) {
        int gradientX = 0;
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                int pixelValue = new Color(image.getRGB(x + i, y + j)).getRed();
                gradientX += HORIZONTAL_SOBEL_KERNEL[j + 1][i + 1] * pixelValue;
            }
        }
        return gradientX;
    }

    private int calculateGradientY(BufferedImage image, int x, int y) {
        int gradientY = 0;
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                int pixelValue = new Color(image.getRGB(x + i, y + j)).getRed();
                gradientY += VERTICAL_SOBEL_KERNEL[j + 1][i + 1] * pixelValue;
            }
        }
        return gradientY;
    }

}
