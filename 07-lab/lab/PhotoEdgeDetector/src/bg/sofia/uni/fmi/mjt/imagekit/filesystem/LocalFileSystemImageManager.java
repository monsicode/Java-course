package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import bg.sofia.uni.fmi.mjt.imagekit.NullCheck;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LocalFileSystemImageManager implements FileSystemImageManager {

    private static final Pattern PAT = Pattern.compile(".*\\.(png|jpg|bmp)$");

    public LocalFileSystemImageManager() {
    }

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {

        NullCheck.validateObj(imageFile, "The file has null !");
        isFileRightFormat(imageFile);

        try (var fileInputStream = new FileInputStream(imageFile)) {

            return ImageIO.read(fileInputStream);

        } catch (IOException err) {
            throw new UncheckedIOException("A problem occurred while loading the img", err);
        }
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {

        NullCheck.validateObj(imagesDirectory, "Img directory cannot be null");

        if (!imagesDirectory.exists() || !imagesDirectory.isDirectory()) {
            throw new IOException("Dir does not exists or its not a dir");
        }

        List<BufferedImage> bufferedImages = new ArrayList<>();

        File[] files = imagesDirectory.listFiles();

        NullCheck.validateObj(files, "Unable to read files from directory");

        for (var file : files) {
            if (isFileRightFormat(file)) {
                try {
                    BufferedImage img = ImageIO.read(file);

                    if (img != null) {
                        bufferedImages.add(img);
                    }
                } catch (IOException err) {
                    throw new UncheckedIOException("Problem with reading img", err);
                }
            }
        }

        return bufferedImages;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {

        NullCheck.validateObj(image, "The img is null");
        NullCheck.validateObj(imageFile, "The file is null");

        if (imageFile.exists()) {
            throw new IOException("File already exist");
        }

        if (!imageFile.getParentFile().exists()) {
            throw new IOException("Parent dir does not exist");
        }

        try (var fileOutputStream = new FileOutputStream(imageFile)) {

            ImageIO.write(image, "png", fileOutputStream);

        } catch (IOException err) {
            throw new UncheckedIOException("A problem occurred while saving the img", err);
        }
    }

    private static boolean isFileRightFormat(File file) throws IOException {
        if (!file.isFile() || !PAT.matcher(file.getName()).matches()) {
            throw new IOException("The file is not in the right format");
        }

        return true;
    }

}

