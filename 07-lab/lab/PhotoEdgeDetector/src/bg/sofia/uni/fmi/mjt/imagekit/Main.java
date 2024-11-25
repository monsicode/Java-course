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


//  try {
//            // Създаване на File обект за директорията
//            File imagesDirectory =
//            new File("C:\\Users\\Monika\\Documents\\JAVA\\07-lab\\lab\\PhotoEdgeDetector\\imgFiles");
//
//            // Зареждане на изображенията чрез вашия fsImageManager
//            List<BufferedImage> imgs = fsImageManager.loadImagesFromDirectory(imagesDirectory);
//
//            // Обхождане и обработка на всяко изображение
//            for (int i = 0; i < imgs.size(); i++) {
//                BufferedImage image = imgs.get(i);
//
//                System.out.println("Processing image " + (i + 1) + " with size: " +
//                    image.getWidth() + "x" + image.getHeight());
//
//                // Пример за обработка - записваме обработеното изображение
//                File outputFile = new File(imagesDirectory, "processed_image_" + (i + 1) + ".png");
//                ImageIO.write(image, "png", outputFile);
//
//                System.out.println("Image saved to: " + outputFile.getAbsolutePath());
//            }
//        } catch (IOException e) {
//            System.err.println("Error while processing images: " + e.getMessage());
//        }