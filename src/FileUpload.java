import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Base64;

public class FileUpload {

    private static final String CRLF = "\r\n";
    private static final String CHARSET = "UTF-8";
    private static final String BOUNDARY = "===apimkov===";
    private static final String HOST = "194.99.21.219";
   //private static final String HOST = "127.0.0.1";
    private static final String HOST_FILE = " /upload.php ";

    public void httpUpload(byte[] byteImageStream) throws IOException {
        Charset UTF8Charset = Charset.forName("UTF8");
        String strBodyData = getBodyData(BOUNDARY, byteImageStream, UTF8Charset);
        int lenBody = strBodyData.getBytes(UTF8Charset).length;
        String strCloseDelimeter = getCloseDelimiter(BOUNDARY);
        int lenDelimeter = strCloseDelimeter.getBytes(UTF8Charset).length;
        int lenContent = lenBody + lenDelimeter;
        String strHeader = getHeader(BOUNDARY, lenContent);

//        DataOutputStream out= new DataOutputStream(new FileOutputStream("C:\\Photo\\saved.txt"));
//        out.writeBytes(strHeader);
//        out.writeBytes(strBodyData);
//        out.writeBytes(strCloseDelimeter);
//        out.close();

        Socket socketClient;
        socketClient = new Socket(HOST,80);
        //socketClient = new Socket(HOST, 8080);//"127.0.0.1"
        OutputStream directOutput = socketClient.getOutputStream();
        //PrintWriter body = new PrintWriter(new OutputStreamWriter(directOutput, CHARSET), false);
        //body.append(strHeader);
        //body.flush();
        directOutput.write(strHeader.getBytes(UTF8Charset));
        directOutput.flush();
        directOutput.write(strBodyData.getBytes(UTF8Charset));
        directOutput.write(strCloseDelimeter.getBytes(UTF8Charset));
        directOutput.flush();

        //server response
        InputStreamReader in = new InputStreamReader(socketClient.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String serverResponse = "";
        while ((serverResponse = bf.readLine()) != null)
            System.out.println("server: " + serverResponse);

        socketClient.close();
    }

    private String getHeader(String boundary, int lenBody) {
        StringBuilder headBuilder = new StringBuilder();
        headBuilder.append(CRLF);
        headBuilder.append("POST" + HOST_FILE + "HTTP/1.1").append(CRLF);
        headBuilder.append("Host: " + HOST).append(CRLF);
        headBuilder.append("Cache-Control: no-cache").append(CRLF);
        headBuilder.append("Content-Length: "+lenBody+"").append(CRLF);
        headBuilder.append("Content-Type: "+"multipart/form-data; boundary="+boundary+"").append(CRLF);
        headBuilder.append(CRLF);
        return headBuilder.toString();
    }


    private String getBodyData(String boundary, byte[] byteImageStream, Charset UTF8Charset) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append(boundary).append(CRLF);
        bodyBuilder.append("Content-Disposition: form-data; name=\"file\"; filename=\"apimkov.jpeg\"").append(CRLF);
        bodyBuilder.append("Content-Type: image/jpeg").append(CRLF);
        bodyBuilder.append(CRLF);
        //bodyBuilder.append("BODY_TEST");
        bodyBuilder.append(new String(byteImageStream));
        return bodyBuilder.toString();
    }

    private String getCloseDelimiter(String boundary) {
        StringBuilder delimBuilder = new StringBuilder();
        delimBuilder.append(CRLF);
        delimBuilder.append(boundary).append("--");
        delimBuilder.append(CRLF);
        return delimBuilder.toString();
    }

}
