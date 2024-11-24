package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LuminosityGrayscale implements GrayscaleAlgorithm {

    private static final double RED_SCALE = 0.21;
    private static final double GREEN_SCALE = 0.72;
    private static final double BLUE_SCALE = 0.07;

    public LuminosityGrayscale() {
    }

    @Override
    public BufferedImage process(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Color originalColor = new Color(image.getRGB(x, y));

                int red = (int) (originalColor.getRed() * RED_SCALE);
                int green = (int) (originalColor.getGreen() * GREEN_SCALE);
                int blue = (int) (originalColor.getBlue() * BLUE_SCALE);

                int sum = red + green + blue;

                Color grayColor = new Color(sum, sum, sum);

                image.setRGB(x, y, grayColor.getRGB());
            }
        }

        return image;

    }

}
