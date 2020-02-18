import java.awt.Button;
import java.awt.Frame;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

@SuppressWarnings("serial")
/**
 * server class
 */
public class Server extends Frame implements Runnable {
    private ServerSocket serverSocket;

    private ArrayList<Service> clients = new ArrayList<>();

    private Properties props;

   // protected Data data;

    public Server(Properties p, String title) {
        super(title);
        /*
        try {
            data = new Data("src/OnlineResults.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Tu Server. Wyjatek przy tworzeniu data");
        }*/
        props = p;
        int port = Integer.parseInt(props.getProperty("port"));
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Error starting Server.");
            System.exit(1);
        }
        Button b = new Button("stop and exit");           // System.out.println(results);
          //  System.out.println(tetris);
        b.addActionListener((actionEvent) -> {
            send(Protocol.STOP);
            /*while (clients.size() != 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }*/
            System.exit(0);
        });
        add(b);
        pack();
        setVisible(true);
        new Thread(this).start();

    }

    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                Service clientService = new Service(clientSocket, this);
                addClientService(clientService);
            } catch (IOException e) {
                System.err.println("Error accepting connection. "
                        + "Client will not be served...");
            }
        }
    }

    synchronized void addClientService(Service clientService)
            throws IOException {
        clientService.init();
        clients.add(clientService);
        new Thread(clientService).start();
        System.out.println("Client added. Nc=" + clients.size());
    }

    synchronized void removeClientService(Service clientService) {
        clients.remove(clientService);
        clientService.close();
        System.out.println("Client removed. Nc=" + clients.size());
    }

    synchronized void send(String msg) {
        for (Service s : clients) {
            s.send(msg);
        }
    }

    synchronized void send(String msg, Service skip) {
        for (Service s : clients) {
            if (s != skip) {
                s.send(msg);
            }
        }
    }


    public String[] getNicks() {
        return nicks;
    }

    private String[] nicks=new String [3];

    public int[] getScores() {
        return scores;
    }

    private int [] scores=new int[3];

    public int endOnline(String nick, int score){

        if(score>scores[0]){
            scores[2]=scores[1];
            scores[1]=scores[0];
            scores[0]=score;
            nicks[2]=nicks[1];
            nicks[1]=nicks[0];
            nicks[0]=nick;
            System.out.println("1. "+nicks[0]+" "+scores[0]+"2. "+nicks[1]+" "+scores[1]+"3. "+nicks[2]+" "+scores[2]);
            return 1;
        }if(score>scores[1]){
            scores[2]=scores[1];
            scores[1]=score;
            nicks[2]=nicks[1];
            nicks[1]=nick;
            System.out.println("1. "+nicks[0]+" "+scores[0]+"2. "+nicks[1]+" "+scores[1]+"3. "+nicks[2]+" "+scores[2]);
            return 2;
        }if(score>scores[2]){
            scores[2]=score;
            nicks[2]=nick;
            System.out.println("1. "+nicks[0]+" "+scores[0]+"2. "+nicks[1]+" "+scores[1]+"3. "+nicks[2]+" "+scores[2]);
            return 2;
        }
        System.out.println("1. "+nicks[0]+" "+scores[0]+"2. "+nicks[1]+" "+scores[1]+"3. "+nicks[2]+" "+scores[2]);
        return 0;
    }

    public static void main(String args[]) {
        Properties p = new Properties();
        String pName = "Server.properties";
        try {
            p.load(new FileInputStream(pName));
        } catch (Exception e) {
            p.put("port", "40000");
            p.put("width", "250");
            p.put("height", "250");
        }
        try {
            p.store(new FileOutputStream(pName), null);
        } catch (Exception e) {
        }
        new Server(p, "Tetris Server");
    }
}