import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/**
 * client class needed in online game. Client sends messages to service and receives messages from it. Each online game is connected with its own client class
 */
@SuppressWarnings("serial")
public class Client extends Frame implements Runnable {

    private Socket socket = null;
    private WindowGame tetris;
    private BufferedReader input;
    private PrintWriter output;
    private String nick;

    /**
     * constructor to create a new client. In this constructor new WindowGame object is created
     * @param host-parameter given by Application object
     * @param port-parameter given by Application object
     * @throws Exception
     */
    public Client(String host, String port, String nick) throws Exception {
        this.nick=nick;
        try {
            socket = new Socket(host, Integer.parseInt(port));
        } catch (UnknownHostException e) {
            throw new Exception("Unknown host.");
        } catch (IOException e) {
            throw new Exception("IO exception while connecting to the server.");
        } catch (NumberFormatException e) {
            throw new Exception("Port value must be a number.");
        }
        try {
            input = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            throw new Exception("Cannot get input/output connection stream.");
        }

        Thread thread = new Thread(this);
        thread.start();
        sendStartChosen();
    }

    synchronized boolean isDisconnected() {
        return socket == null;
    }


    /**
     *method to inform the service a new game has been started
     */
    void sendStartChosen() {
        send(Protocol.STARTCHOSEN);
    }
    /**
     *method to inform the service a level has been accomplished
     */
    void sendLevelAccomplished() {
        send(Protocol.LEVELACCOMPLISHED + " " + (currentLevel));
    }


    void sendFinishedGamedata() {
        send(Protocol.FINISHEDGAMEDATA+" "+ tetris.getSum()+" "+ nick);
    }

    void sendBestGames() {
        send(Protocol.BESTGAMES);
    }

    /**
     * general method used to send any message
     * @param command-message name
     */
    void send(String command) {
        if (output != null)
            output.println(command);
        System.out.println("Tu client. Probuje wyslac wiadomosc: "+command);
    }
    /**
     * continuous method listening to messages from the service
     */
    public void run() {
        if (tetris == null) {
            //System.out.println("Tu Client. tetris==null");
            new Thread(() -> {
                try {
                    tetris = new WindowGame(this);
                    tetris.setSpeed(speed);
                    setAllParameters();
                    System.out.println("Tu Client. tetris:."+tetris+" otworzone okno");
                    tetris.moveIt();

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        while (true) {
            try {
                System.out.println("Tu Client "+nick+". Zaczynam czytac");
                String command = input.readLine();
                System.out.println("INPUT CLIENT: " + command);

                if (!handleCommand(command)) {
                    output.close();
                    input.close();
                    socket.close();
                    setVisible(false);
                    dispose();
                    System.out.println("Jestem w ifie");
                    break;
                }
                //  else {

                //}
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        output = null;
        input = null;
        tetris = null;//
        synchronized (this) {
            socket = null;
        }
    }



    /**
     * set of variables to be received from the service, corresponding to game parameters in WindowGame
     */
    private int currentLevel;


    private int ending;

    public String getSpeed() {
        return speed;
    }

    private String speed;


    private int squareBoxes;
    private int rectangularBoxes;


    private int obstacles;


    private int treatBoxes;

    /**
     * method setting all level parameters in the game
     */
    private void setAllParameters(){
        tetris.setSpeed(speed);
        tetris.setTmpSpeed(speed);
        tetris.setNextLevelCount(currentLevel -1);
        tetris.setEnding(ending);
        tetris.setObstacles(obstacles);
        tetris.setRectangularBoxes(rectangularBoxes);
        tetris.setSquareBoxes(squareBoxes);
        tetris.setTreatboxes(treatBoxes);
    }

    /**
     * method describing how to handle messages from the service
     * @param command-message name
     * @return true when a message has been caught
     * @throws FileNotFoundException
     */

    private boolean handleCommand(String command) throws FileNotFoundException {
        StringTokenizer st = new StringTokenizer(command);
        String cd = st.nextToken();
       // System.out.println("Tu CLient. Mam wiadomosc. " + command);
        if (cd.equals(Protocol.START)) {
            currentLevel = Integer.parseInt(st.nextToken());
            ending = Integer.parseInt(st.nextToken());
            speed = st.nextToken();
            rectangularBoxes = Integer.parseInt(st.nextToken());
            squareBoxes = Integer.parseInt(st.nextToken());
            obstacles = Integer.parseInt(st.nextToken());
            treatBoxes = Integer.parseInt(st.nextToken());
        } else if (cd.equals(Protocol.NEXTLEVEL)) {
            currentLevel = Integer.parseInt(st.nextToken());
            ending = Integer.parseInt(st.nextToken());
            speed = st.nextToken();
            rectangularBoxes = Integer.parseInt(st.nextToken());
            squareBoxes = Integer.parseInt(st.nextToken());
            obstacles = Integer.parseInt(st.nextToken());
            treatBoxes = Integer.parseInt(st.nextToken());
            setAllParameters();
            //System.out.println("Tu CLient. DOstalem nowe parametry: poziom " + levelNo + " speed " + speed + " ending " + pointsNeeded);

        } else if (cd.equals(Protocol.BESTGAME)) {
            String results="";
            String nickLocal = null;
            for(int i=0; i<3; ++i) {
                results= new StringBuilder().append(results).append(i+1+". ").toString();
                nickLocal=st.nextToken();
                if(!nickLocal.equals("null"))
                    results = String.valueOf(new StringBuilder().append(results).append(nickLocal));
                results= new StringBuilder().append(results).append(" ").toString();
                results = new StringBuilder().append(results).append(st.nextToken()).toString();
                results= new StringBuilder().append(results).append(" ").toString();

                results= new StringBuilder().append(results).append(" \n").toString();
            }
            tetris.setBestResultsOnline(results);

        } else if (cd.equals(Protocol.RANK)) {
            String rank=st.nextToken();
            tetris.setRank(rank);
            System.out.println("rank: "+rank);
        }


        return true;
    }


}
