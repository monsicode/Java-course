package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalFileSystemImageManagerTest {

    private final LocalFileSystemImageManager imgManager = new LocalFileSystemImageManager();

    @TempDir
    File tempFolder;

    @Test
    void testLoadImgWithNullFile() {
        File nullFile = null;
        assertThrows(IllegalArgumentException.class, () -> imgManager.loadImage(nullFile));
    }

    @Test
    void testLoadImgWithTxtFile() throws IOException {
        File txtFile = new File(tempFolder, "test.txt");
        assertTrue(txtFile.createNewFile());

        assertThrows(IOException.class, () -> imgManager.loadImage(txtFile));

    }

    @Test
    void testLoadImgWithDirectory() {
        File dir = new File(tempFolder, "testDir");
        assertTrue(dir.mkdir(), "Temp dir should be created");

        assertThrows(IOException.class, () -> imgManager.loadImage(dir));
    }

    @Test
    void testLoadImgWithValidFile() throws IOException {
        File img = new File(tempFolder, "img.png");
        assertTrue(img.createNewFile(), "File should be created");

        BufferedImage imgData = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < imgData.getHeight(); y++) {
            for (int x = 0; x < imgData.getWidth(); x++) {
                imgData.setRGB(x, y, Color.BLACK.getRGB());  // Запълваме с черен цвят
            }
        }
        ImageIO.write(imgData, "png", img);

        BufferedImage imgReturn = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

        //check pixel by pixel if they are equal
        for (int y = 0; y < imgReturn.getHeight(); y++) {
            for (int x = 0; x < imgReturn.getWidth(); x++) {
                assertEquals(imgReturn.getRGB(x, y), imgManager.loadImage(img).getRGB(x, y));
            }
        }
    }

    @Test
    void testLoadImagesFromDirectoryWithNullDir() {
        File dir = null;
        assertThrows(IllegalArgumentException.class, () -> imgManager.loadImagesFromDirectory(dir));
    }

    @Test
    void testLoadImagesFromDirectoryWithRegularFile() throws IOException {
        File regularFile = new File(tempFolder, "file.txt");
        assertTrue(regularFile.createNewFile(), "Should creat regular file");

        assertThrows(IOException.class, () -> imgManager.loadImagesFromDirectory(regularFile));
    }

    @Test
    void testLoadImagesFromDirectoryWithNonExistingDir() {
        File nonExistingDir = new File(tempFolder, "dir");
        assertFalse(nonExistingDir.exists(), "Directory should not exist");

        assertThrows(IOException.class, () -> imgManager.loadImagesFromDirectory(nonExistingDir));
    }

    @Test
    void testLoadImagesFromDirectoryWithWrongFormatedFiles() throws IOException {
        File dir = new File(tempFolder, "dir");
        assertTrue(dir.mkdir(), "Directory should be created");

        File txtFile = new File(dir, "file.txt");
        assertTrue(txtFile.createNewFile(), "File should be created");

        assertThrows(IOException.class, () -> imgManager.loadImagesFromDirectory(dir));
    }

    @Test
    void testLoadImagesFromDirectoryWithValidData() throws IOException {
        File dir = new File(tempFolder, "dir");
        assertTrue(dir.mkdir(), "Directory should be created");

        File img1 = new File(dir, "img1.png");
        assertTrue(img1.createNewFile(), "File should be created");

        File img2 = new File(dir, "img2.png");
        assertTrue(img2.createNewFile(), "File should be created");

        BufferedImage imgData = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < imgData.getHeight(); y++) {
            for (int x = 0; x < imgData.getWidth(); x++) {
                imgData.setRGB(x, y, Color.BLACK.getRGB());  // Запълваме с черен цвят
            }
        }

        ImageIO.write(imgData, "png", img1);
        ImageIO.write(imgData, "png", img2);

        BufferedImage imgReturn = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < imgReturn.getHeight(); y++) {
            for (int x = 0; x < imgReturn.getWidth(); x++) {
                assertEquals(imgReturn.getRGB(x, y), imgManager.loadImagesFromDirectory(dir).get(0).getRGB(x, y));
                assertEquals(imgReturn.getRGB(x, y), imgManager.loadImagesFromDirectory(dir).get(1).getRGB(x, y));
            }
        }
    }

    @Test
    void testSaveImageWithNullImg() {
        BufferedImage img = null;
        File fileToSave = new File(tempFolder, "newImg.png");

        assertThrows(IllegalArgumentException.class, () -> imgManager.saveImage(img, fileToSave));
    }

    @Test
    void testSaveImageWithNullFile() {
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        File fileToSave = null;

        assertThrows(IllegalArgumentException.class, () -> imgManager.saveImage(img, fileToSave));
    }

    @Test
    void testSaveImageWithAlreadyExistingFile() throws IOException {
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

        File fileToSave = new File(tempFolder, "file.png");
        assertTrue(fileToSave.createNewFile(), "File should be created");

        assertThrows(IOException.class, () -> imgManager.saveImage(img, fileToSave));
    }

    @Test
    void testSaveImageWithNonExistingParentDir() throws IOException {
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

        File nonExistingDir = new File(tempFolder, "dir");
        assertFalse(nonExistingDir.exists(), "This dir should not exist");

        File fileToSave = new File(nonExistingDir, "file.png");

        assertThrows(IOException.class, () -> imgManager.saveImage(img, fileToSave));

    }

    @Test
    void testSaveImageWithValidData() throws IOException {
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                img.setRGB(x, y, Color.BLACK.getRGB());
            }
        }


        File dir = new File(tempFolder, "dir");
        assertTrue(dir.mkdir(), "This dir should be created");

        File fileToSave = new File(dir, "file.png");

        imgManager.saveImage(img, fileToSave);

        assertTrue(fileToSave.exists(), "The img file should exist");

        BufferedImage imgReturn = ImageIO.read(fileToSave);

        for (int y = 0; y < imgReturn.getHeight(); y++) {
            for (int x = 0; x < imgReturn.getWidth(); x++) {
                assertEquals(imgReturn.getRGB(x, y), imgManager.loadImage(fileToSave).getRGB(x, y));
            }
        }
    }


}

