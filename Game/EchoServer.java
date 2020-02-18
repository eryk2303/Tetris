/*import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    EchoServer(Socket socket) throws Exception {
    }

    public static void main(String args[]) throws Exception {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("localHost.getHostAddress() = " +
                localHost.getHostAddress());
        System.out.println("localHost.getHostName() = " +
                localHost.getHostName());

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(12129);
        } catch (Exception e) {
            System.err.println("Create server socket: " + e);
            return;
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                String fromClient = br.readLine();
                System.out.println("From client: [" + fromClient + "]");
                pw.println("Echo: " + fromClient);
                socket.close();
            } catch (Exception e) {
                System.err.println("Server exception: " + e);
            }
        }
    }
}*/