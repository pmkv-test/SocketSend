import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    private static final String IMAGE_PATH = "C:\\Photo\\apimkov.jpg";
    private static final Integer MAX_SIZE = 1000000;

    public static void main(String[] args) throws IOException {
/*
        File inputFile = new File(IMAGE_PATH);
        BufferedImage image = ImageIO.read(inputFile);
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg", imageStream);
*/
        final byte[] bytes = Files.readAllBytes(Paths.get(IMAGE_PATH));
        if (bytes.length <= MAX_SIZE) {
            FileUpload fileUpload = new FileUpload();
            fileUpload.httpUpload(bytes);
        }
    }
    }



