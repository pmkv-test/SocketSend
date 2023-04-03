import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {
    private static final String IMAGE_PATH = "C:\\Photo\\apimkov.jpg";
    private static final Integer IMAGE_MAX_SIZE = 1000000;

    public static void main(String[] args) throws IOException {
        File inputFile = new File(IMAGE_PATH);
        BufferedImage image = ImageIO.read(inputFile);
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg", imageStream);

        if (imageStream.toByteArray().length <= IMAGE_MAX_SIZE) {
            FileUpload fileUpload = new FileUpload();
            fileUpload.httpUpload(imageStream.toByteArray());
        }
    }

    }



