import java.io.*;
import java.net.Socket;

public class FileUpload {

    private static final String CRLF = "\r\n";
    private static final String CHARSET = "UTF-8";
    private static final String BOUNDARY = "===apimkov===";
    private static final String HOST = "194.99.21.219";
    //private static final String HOST = "127.0.0.1";
    private static final String HOST_FILE = " /upload.php ";

    public String httpUpload(byte[] byteStream) throws IOException {
        Socket socketClient;
        socketClient = new Socket(HOST,80);
        //socketClient = new Socket(HOST,8080);//"127.0.0.1"
        OutputStream directOutput = socketClient.getOutputStream();
        PrintWriter body = new PrintWriter(new OutputStreamWriter(directOutput, CHARSET), true);
        //client send
        body.append(CRLF);
        body.flush();
        addHeader(body, byteStream, BOUNDARY);
        addImageData(body, directOutput, byteStream, BOUNDARY);
        addCloseDelimiter(body,BOUNDARY);
        //server response
        InputStreamReader in = new InputStreamReader(socketClient.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String str = bf.readLine();
        System.out.println("server: " + str);
        socketClient.close();
        return str;
    }

    private static void addHeader(PrintWriter body, byte[] data,
                                     final String boundary) {
        body.append("POST"+HOST_FILE+"HTTP/1.1");
        body.append(CRLF).append("Host: "+HOST).append(CRLF);
        body.append("Cache-Control: no-cache").append(CRLF);
        body.append("Content-Length: "+data.length+"").append(CRLF);
        body.append("Content-Type: "+"multipart/form-data; boundary="+"\""+boundary+"\""+"").append(CRLF);
        body.append(CRLF);
        body.flush();
    }


    private static void addImageData(PrintWriter body, OutputStream directOutput, byte[] byteStream,
                                          final String boundary) throws IOException {
        body.append(boundary).append(CRLF);
        body.append("Content-Disposition: form-data; inline; filename=\"apimkov.jpg\"").append(CRLF);
        body.append("Content-Type: image/jpeg").append(CRLF);
        body.append(CRLF);
        body.flush();
        directOutput.write(byteStream);
        directOutput.flush();
        body.append(CRLF);
        body.flush();
    }

    private static void addCloseDelimiter(PrintWriter body, final String boundary) {
        body.append(boundary).append("--").append(CRLF);
        body.flush();
    }

}
