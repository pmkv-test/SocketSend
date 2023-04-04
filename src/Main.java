import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {
    private static final String IMAGE_PATH = "C:\\Photo\\apimkov.jpeg";
    private static final Integer MAX_SIZE = 1000000;

    public static void main(String[] args) throws IOException {

        //byte[] bytes = Files.readAllBytes(Paths.get(IMAGE_PATH));
        FileInputStream fileInputStream = null;
        try {
            File file = new File(IMAGE_PATH);
            fileInputStream = new FileInputStream(file);
            byte[] bytes = fileInputStream.readAllBytes();

            if (bytes.length <= MAX_SIZE) {
                FileUpload fileUpload = new FileUpload();
                fileUpload.httpUpload(bytes);
            }
        }
        catch (IOException e) {
            fileInputStream.close();
            e.printStackTrace();
        }
        finally {
            fileInputStream.close();
        }


    }
    }



