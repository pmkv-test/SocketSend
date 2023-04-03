import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {
    private static final String IMAGE_PATH = "C:\\Photo\\apimkov.jpg";

    public static void main(String[] args) throws IOException {
        File inputFile = new File(IMAGE_PATH);
        BufferedImage image = ImageIO.read(inputFile);
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg", imageStream);
        FileUpload fileUpload = new FileUpload();
        fileUpload.httpUpload(imageStream.toByteArray());
    }

    }



