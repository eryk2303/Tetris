import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * class communicating directly with the client
 */

public class Service implements Runnable {
    /**
     * set of variables describing game parameters, corresponding to those in WindowGame
     */
    private int id;
    private int currentLevel = 1;
    private String speed;
    private int ending;
    private int filling;
    private int rectangularBoxes;
    private int squareBoxes, treatboxes, obstacles;

    private Server server;
    private Socket clientSocket;

    /**
     * variables needed to create input and output for communication with the client
     */

    private BufferedReader input;
    private PrintWriter output;

    /**
     * constructor needed to create a new service by the server
     * @param clientSocket
     * @param server
     */
    public Service(Socket clientSocket, Server server) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    /**
     * method defining input and output
     * @throws IOException
     */
    void init() throws IOException {
        Reader reader = new InputStreamReader(clientSocket.getInputStream());
        input = new BufferedReader(reader);
        output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    void close() {
        try {
            output.close();
            input.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client (" + id + ").");
        } finally {
            output = null;
            input = null;
            clientSocket = null;
        }
    }
    private int score;
    private String nick;
    /**
     * continuous method needed to catch messages from the client and respond to them. This method describes how to handle any method from the client
     */
    public void run() {
        while (true) {
            System.out.println("Tu Service input ");
            String request = receive();
            StringTokenizer st = new StringTokenizer(request);
            String command = st.nextToken();
            System.out.println("Tu Service. Dostalem wiadomosc " + command);
            if (command.equals(Protocol.MANUALCHOSEN)) {
                send(Protocol.MANUAL + " " + "Przesuwaj klocki.");
            } else if (command.equals(Protocol.STARTCHOSEN)) {
                System.out.println("Tu service. Wysylam parametry start");
                System.out.println("Tu service. NextFileName: "+nextFileName);
                readLevelProperties("level_1.properties");
                send(Protocol.START + " " + "1" + " " + ending + " " + speed + " " + rectangularBoxes + " " + squareBoxes + " " + obstacles + " " + treatboxes+" "+filling+" "+location+" "+levelName+" "+lastLevel);
            } else if (command.equals(Protocol.LEVELACCOMPLISHED)) {
                currentLevel = Integer.parseInt(st.nextToken());
                currentLevel++;
                //readLevelProperties(currentLevel);
                System.out.println("st: "+st);
                System.out.println("Tu service. NextFileName: "+nextFileName);
                System.out.println("Tu Service. Dostalem wiadomosc, ze chcesz przejsc na wyzszy poziom");
                System.out.println("Przechodzisz z poziomu " + (currentLevel-1) + " na " + currentLevel);
                //readLevelProperties(nextFileName);
                if (currentLevel == 2)
                    readLevelProperties("level_2.properties"); //UOGOLNIC NA N POZIOMOW
                else
                    readLevelProperties("level_3.properties");
                String toSend = Protocol.NEXTLEVEL + " " + (currentLevel) + " " + ending + " " + speed + " " + rectangularBoxes + " " + squareBoxes + " " + obstacles + " " + treatboxes;
                System.out.println(toSend);
                send(toSend);
                System.out.println("Nowy poziom " + currentLevel + " speed " + speed + "pointsNeeded/ending " + ending);

            } else if (command.equals(Protocol.FINISHEDGAMEDATA)) {

                System.out.println("st: "+st);
                score = Integer.parseInt(st.nextToken());
                System.out.println("score: "+score);
                nick = st.nextToken();
                System.out.println("nick: "+nick);
                int rank=server.endOnline(nick, score);
                send(Protocol.RANK+" "+rank);
            } else if (command.equals(Protocol.BESTGAMES)) {
                String[]nicks=server.getNicks();
                int[]scores=server.getScores();
                send(Protocol.BESTGAME + " "+nicks[0]+" "+scores[0]+" "+nicks[1]+" "+scores[1]+" "+nicks[2]+" "+scores[2]);
            } else {
                server.removeClientService(this);
                break;
            }
        }
    }


    private String receive() {
        try {
            return input.readLine();
        } catch (Exception e) {
            System.err.println("Error reading client (" + id + ").");
        }
        return "NULLCommand";
    }
    private String nextFileName;
    private boolean lastLevel;
    private String levelName;
    private int location;


    /**
     * method reading the parameters of any level
     * @param number
     */
    public void readLevelProperties(String number) {
        System.out.println("Tu service. Odczytuje plik: "+ nextFileName);
        PropertiesRead level = new PropertiesRead();
        level.fileRead(number);
        level.loadProperties();
        speed = level.GetProperties().getProperty("speed");
        rectangularBoxes = Integer.parseInt(level.GetProperties().getProperty("rectangularBoxes"));
        squareBoxes = Integer.parseInt(level.GetProperties().getProperty("squareBoxes"));
        treatboxes = Integer.parseInt(level.GetProperties().getProperty("treatBoxes"));
        obstacles = Integer.parseInt(level.GetProperties().getProperty("obstacles"));
        ending = Integer.parseInt(level.GetProperties().getProperty("ending"));
        filling = Integer.parseInt(level.GetProperties().getProperty("filling"));
        lastLevel=Boolean.parseBoolean("lastlevel");
        levelName = level.GetProperties().getProperty("levelname");
        if(0!=filling)
            location = Integer.parseInt(level.GetProperties().getProperty("location"));
        nextFileName = String.format("level_%s.properties", currentLevel+1);
      //nextFileName=level.GetProperties().getProperty("nextfilename");
        System.out.println("Tu service. Nastepny plik: "+ nextFileName+" currentLevel: "+currentLevel);
    }

    /**
     * general method needed to send any message
     * @param command-message name
     */
    void send(String command) {
        output.println(command);
        //System.out.println("Tu Service. Wysylam wiadomosc: " + command);
    }
}