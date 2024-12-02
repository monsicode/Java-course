package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LuminosityGrayscale implements GrayscaleAlgorithm {

    private static final double RED_SCALE = 0.21;
    private static final double GREEN_SCALE = 0.72;
    private static final double BLUE_SCALE = 0.07;
    private static final int WHITE = 255;

    public LuminosityGrayscale() {
    }

    @Override
    public BufferedImage process(BufferedImage image) {

        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Color originalColor = new Color(image.getRGB(x, y));

                double red = (originalColor.getRed() * RED_SCALE);
                double green = (originalColor.getGreen() * GREEN_SCALE);
                double blue = (originalColor.getBlue() * BLUE_SCALE);

                int sum = (int) (red + green + blue);
                sum = Math.min(WHITE, Math.max(0, sum));

                Color grayColor = new Color(sum, sum, sum);

                image.setRGB(x, y, grayColor.getRGB());
            }
        }
        return image;
    }

}
