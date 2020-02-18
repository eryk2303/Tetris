/*import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static void main(String args[]) {
        int l = 0;
        try {
            Socket socket = new Socket("localhost", 12129);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("Hello server! " + l++);
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is));
            System.out.println(br.readLine());
            socket.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }
    }
}*/