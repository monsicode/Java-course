package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

public class LocalFileSystemImageManager implements FileSystemImageManager {

    public LocalFileSystemImageManager() {
    }

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        try (var fileInputStream = new FileInputStream(imageFile)) {

            BufferedImage bufferedImg = ImageIO.read(fileInputStream);

            if (bufferedImg == null) {
                throw new IllegalArgumentException("The file has invalid img!");
            }

            return bufferedImg;

        } catch (IOException err) {
            throw new UncheckedIOException("A problem occurred while loading the img", err);
        }
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {

        return List.of();
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {

        if (image == null || imageFile == null) {
            throw new IllegalArgumentException("The image or file is null ");
        }

        try (var fileOutputStream = new FileOutputStream(imageFile)) {

            ImageIO.write(image, "png", fileOutputStream);

        } catch (IOException err) {
            throw new UncheckedIOException("A problem occurred while saving the img", err);
        }
    }

}

