import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class FileUpload {

    private static final String CRLF = "\r\n";
    private static final String CHARSET = "UTF-8";
    private static final Integer MAX_SIZE = 1000000;
    private static final String BOUNDARY = "===apimkov===";
    private static final String HOST = "194.99.21.219";
    // private static final String HOST = "127.0.0.1";
    private static final String HOST_FILE = " /upload.php ";

    public void httpUpload(byte[] byteStream) throws IOException {
        Socket socketClient;
        Charset UTF8Charset = Charset.forName("UTF8");
        socketClient = new Socket(HOST,80);
       // socketClient = new Socket(HOST,8080);//"127.0.0.1"
        OutputStream directOutput = socketClient.getOutputStream();
        PrintWriter body = new PrintWriter(new OutputStreamWriter(directOutput, CHARSET), true);

        String strImageData = addImageData(body, directOutput, byteStream, BOUNDARY);
        int lenImage = strImageData.getBytes(UTF8Charset).length;
        String strDelimeter = addCloseDelimiter(body,BOUNDARY);
        int lenDelimeter = strDelimeter.getBytes(UTF8Charset).length;
        int lenContent = lenImage+byteStream.length+lenDelimeter;

        if (lenContent<= MAX_SIZE) {
            //client send
            body.append(CRLF);
            body.flush();
            addHeader(body, BOUNDARY, lenContent);
            body.append(strImageData);
            body.flush();
            //body.append("BODY");
            //body.flush();
            directOutput.write(byteStream);
            directOutput.flush();
            body.append(strDelimeter);
            body.flush();
        }

        //server response
        InputStreamReader in = new InputStreamReader(socketClient.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String serverResponse = null;
        while ((serverResponse = bf.readLine()) != null)
            System.out.println("server: " + serverResponse);

        socketClient.close();
    }

    private static void addHeader(PrintWriter body, final String boundary, int lenContent) {
        body.append("POST"+HOST_FILE+"HTTP/1.1");
        body.append(CRLF).append("Host: "+HOST).append(CRLF);
        body.append("Cache-Control: no-cache").append(CRLF);
        body.append("Content-Length: "+lenContent+"").append(CRLF);
        body.append("Content-Type: "+"multipart/form-data; boundary="+boundary+"").append(CRLF);
        body.append(CRLF);
        body.flush();
    }


    private String addImageData(PrintWriter body, OutputStream directOutput, byte[] byteStream, final String boundary) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append(boundary).append(CRLF);
        bodyBuilder.append("Content-Disposition: form-data; inline; filename=\"apimkov.jpg\"").append(CRLF);
        bodyBuilder.append("Content-Type: image/jpeg").append(CRLF);
        bodyBuilder.append(CRLF);
        return bodyBuilder.toString();

    }

    private String addCloseDelimiter(PrintWriter body, final String boundary) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append(CRLF).append(boundary).append("--").append(CRLF);
        return bodyBuilder.toString();
    }

}
