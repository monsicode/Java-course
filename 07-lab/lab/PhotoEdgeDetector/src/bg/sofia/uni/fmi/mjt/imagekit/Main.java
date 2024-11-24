package bg.sofia.uni.fmi.mjt.imagekit;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection.SobelEdgeDetection;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import bg.sofia.uni.fmi.mjt.imagekit.filesystem.FileSystemImageManager;
import bg.sofia.uni.fmi.mjt.imagekit.filesystem.LocalFileSystemImageManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FileSystemImageManager fsImageManager = new LocalFileSystemImageManager();

        BufferedImage image = fsImageManager.loadImage(new File("mike-tyson-powerful-punch.jpg"));

        ImageAlgorithm grayscaleAlgorithm = new LuminosityGrayscale();
        BufferedImage grayscaleImage = grayscaleAlgorithm.process(image);

        ImageAlgorithm sobelEdgeDetection = new SobelEdgeDetection(grayscaleAlgorithm);
        BufferedImage edgeDetectedImage = sobelEdgeDetection.process(image);

        fsImageManager.saveImage(grayscaleImage, new File("mike-tyson-powerful-punch-grayscale.jpg"));
        fsImageManager.saveImage(edgeDetectedImage, new File("mike-tyson-powerful-punch-edge-detected.jpg"));
    }

}
