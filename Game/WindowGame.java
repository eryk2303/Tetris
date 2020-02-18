import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

/**
 * the event handling class and game boards
 */
public class WindowGame<audioStrmObj> extends JFrame implements KeyListener {

    /**
    * client needed in online game
     */
    Client parent;

    /**
     * variable online is true if a server controls the game
     */
    private boolean online;
    /**
    * variable paused is true when the game has been paused
     */
    private boolean paused = false;

    Start startOption = new Start();

    private int countGo = 0;


    public int getCountGo() {
        return countGo;
    }

    public void setCountGo(int countGo) {
        this.countGo = countGo;
    }

    /**
     * variable needed to change boxes
     */
    private int counter = 0;
    private int n = 1;
    /**
     * variables needed to change frequency of A and B boxes
     */

    public void setRectangularBoxes(int rectangularBoxes) {
        this.rectangularBoxes = rectangularBoxes;
    }


    private int rectangularBoxes;

    public void setSquareBoxes(int squareBoxes) {
        this.squareBoxes = squareBoxes;
    }

    private int squareBoxes;

    public void setObstacles(int obstacles) {
        this.obstacles = obstacles;
    }

    private int obstacles;

    public void setTreatboxes(int treatboxes) {
        this.treatboxes = treatboxes;
    }

    private int treatboxes;


    public void setSpeed(String speed) {
        this.speed = speed;

    }

    /**
     * falling speed
     */
    private String speed;
    /**
     * coordinates of falling elements
     */
    private int oneX = 0;
    private int oneY = 1;
    /**
     * main frame
     */
    JFrame frame;
    DrawPanel boardPanel;
    Label points, level, go, pause, results;


    public void setTmpSpeed(String tmpSpeed) {
        this.tmpSpeed = tmpSpeed;
    }

    /**
     * variable need to charge speed
     */
    private String tmpSpeed;

    /**
     * table to write whether the field on the board is occupied
     */
    private int point[][][] = new int[30][30][1];

    public int getSum() {
        return sum;
    }

    /**
     * variable need to sum points
     */
    private int sum = 0;

    public void setEnding(int ending) {
        this.ending = ending;
    }

    /**
     * variable defining when to change level
     */
    private int ending;

    /**
     * variables needed to general changing level methods
     */
    private String levelName;
    private boolean lastLevel;
    private String nextFileName;



    /**
     * variables need to add a box at the bottom
     */
    private int filling;
    private int countFilling;
    private int location;
    private int quantity;
    /**
     * method to obtain data from a configuration file
     */
    public void readLevelProperties(String number) {

        PropertiesRead level = new PropertiesRead();
        level.fileRead(number);
        level.loadProperties();
        speed = level.GetProperties().getProperty("speed");
        tmpSpeed = speed;
        rectangularBoxes = parseInt(level.GetProperties().getProperty("rectangularBoxes"));
        squareBoxes = parseInt(level.GetProperties().getProperty("squareBoxes"));
        treatboxes = parseInt(level.GetProperties().getProperty("treatBoxes"));
        obstacles = parseInt(level.GetProperties().getProperty("obstacles"));
        ending = parseInt(level.GetProperties().getProperty("ending"));
        filling = parseInt(level.GetProperties().getProperty("filling"));
        lastLevel=Boolean.parseBoolean("lastlevel");
        levelName = level.GetProperties().getProperty("levelname");
        nextFileName =level.GetProperties().getProperty("nextfilename");
        if(0!=filling)
            location = Integer.parseInt(level.GetProperties().getProperty("location"));
        quantity = parseInt(level.GetProperties().getProperty("quantity"));

        System.out.println("nextLevelName: "+ nextFileName);
    }

    /**
     * rank-variable setter
     * @param rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }
    /**
     * String variable for displaying the rank at the end of the game
     */
    private String rank="0";

    /**
     * constructor for server-controlled game
     * @param parent defines the client in online game
     */
    public WindowGame(Client parent) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {

        online = true;
        quantity=15;
        //this.speed = speed;
        this.parent = parent;
        frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.addKeyListener(this);
        points = new Label("Punkty: " + sum);
        points.setBounds(400, 100, 100, 30);
        level = new Label("Poziom latwy");
        level.setBounds(400, 150, 100, 30);
        results = new Label("ALT -> wyniki");
        results.setBounds(400, 200, 100, 120);
        go = new Label("Aby rozpoczac gre nacisnij enter");
        go.setBounds(100, 300, 200, 30);
        pause = new Label("Aby zatrzymac, wcisnij spacje");
        pause.setBounds(100, 10, 200, 30);
        frame.add(pause);
        frame.add(points);
        frame.add(results);
        frame.add(level);
        frame.add(go);
        DrawPanel drawPanel = new DrawPanel(quantity);


        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        boardPanel = drawPanel;

        frame.setVisible(true);
        frame.setResizable(true);
        for (int i = 0; i < quantity; i++)
            for (int e = 0; e < quantity; e++)
                point[i][e][0] = 0;

        for (int e = 0; e < quantity; e++)
            point[quantity][e][0] = 1;

        drawNewLevel();

    }


    File  clipFile = new File("piosenka.wav"); // path to your clip
    AudioInputStream audioStrmObj = AudioSystem.getAudioInputStream(clipFile);
    AudioFormat format = audioStrmObj.getFormat();
    DataLine.Info info = new DataLine.Info(Clip.class, format);
    Clip audioClip = (Clip) AudioSystem.getLine(info);



    /**
     * constructor for creating graphics and starting main method
     */
    public WindowGame() throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {
        audioClip.open(audioStrmObj);
        audioClip.start();

        readLevelProperties("level_1.properties");
        online = false;
        frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.addKeyListener(this);
        points = new Label("Punkty: " + sum);
        points.setBounds(400, 100, 100, 30);
        level = new Label("Poziom latwy");
        level.setBounds(400, 150, 100, 30);
        go = new Label("Aby rozpoczac gre nacisnij enter");
        go.setBounds(100, 300, 200, 30);
        pause = new Label("Aby zatrzymac, wcisnij spacje");
        pause.setBounds(100, 10, 200, 30);
        frame.add(pause);
        frame.add(points);
        frame.add(level);
        frame.add(go);
        DrawPanel drawPanel = new DrawPanel(quantity);


        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        boardPanel = drawPanel;

        frame.setVisible(true);
        frame.setResizable(true);




        for (int i = 0; i < quantity; i++)
            for (int e = 0; e < quantity; e++)
                point[i][e][0] = 0;

        for (int e = 0; e < quantity; e++)
            point[quantity][e][0] = 1;

        moveIt();
        drawNewLevel();

    }

    public void setBestResultsOnline(String bestResultsOnline) {
        this.bestResultsOnline = bestResultsOnline;
    }

    /**
     * variable needed to display best scores when the game is online
     */
    private String bestResultsOnline;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * method to trigger an event using the keys
     */
    @Override
    public void keyPressed(KeyEvent e) {
        TetrisBox currentBox = boardPanel.getCurrentBox();

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            startOption.startVisible();

        if (e.getKeyCode() == KeyEvent.VK_ALT){
            if(online){
                parent.sendBestGames();
                paused = true;
                currentBox.setStep(0);
                JFrame frame = new JFrame("Najlepsze wyniki");
                frame.setSize(300, 300);
               // Label description=new Label("Tu beda najlepsze wyniki");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("WindowGame: "+bestResultsOnline);
                Label description=new Label(bestResultsOnline);
                description.setBounds(80, 100, 100, 30);
                frame.add(description);
                frame.setVisible(true);

            }
        }


        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            if(!paused) {
                paused = true;
                currentBox.setStep(0);
            }
            else {
                paused = false;
                currentBox.setStep(frame.getHeight()/400);
            }
        }
        if(!paused)
        if(!(currentBox instanceof BlockBox)){
        if (countGo == 1) {
            if (countFilling >= filling) {

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    currentBox.moveInDirection("right");

                    try {
                        Thread.sleep(10 / parseInt(speed));
                    } catch (Exception exc) {
                    }
                    frame.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    currentBox.moveInDirection("left");
                    try {
                        Thread.sleep(10 / parseInt(speed));
                    } catch (Exception exc) {
                    }
                    frame.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    currentBox.rotate();
                    try {
                        Thread.sleep(10 / parseInt(speed));
                    } catch (Exception exc) {
                    }
                    frame.repaint();
                }
            }
            }

        }

    }


    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * method to choose a box depending on counter and level properties
     *
     * @return - chosen box type (char)
     */
    public char chooseBox() {


        if (counter % 7 == 0) {

            if ((n % obstacles) == 0){
                return 'F';}
            else
                counter++;
        }
        if (counter%7 ==1)    {
            if ((n % treatboxes) == 0) {
                return 'G';

            }else
                counter++;
        }
            if (counter % 7 == 2) {
                if ((n % rectangularBoxes) == 0) {
                    return 'A';
                } else {
                    counter++;
                }
            }
            if (counter % 7 == 3) {
                if ((n % squareBoxes) == 0) {
                    return 'B';
                } else {
                    counter++;
                }
            }

            if (counter % 7 == 4) {
                return 'C';
            } else if (counter % 7 == 5) {
                return 'D';
            } else {
                n++;
                return 'E';
            }


    }
    private int intSpeed;

    /**
     * method responsible for falling items and starting smaller methods
     */
    public void moveIt() throws IOException, InterruptedException {

        if(online) {
            if(speed == null)
            {
                speed ="1";
            }
            intSpeed = 10 / parseInt(speed);

        }
        int counter = 1;

         boardPanel.addBox('A', oneX, oneY, intSpeed, point);

        while (true) {
            if(!online)
                intSpeed = 10 / parseInt(speed);
                    go();


                    TetrisBox currentBox = boardPanel.getCurrentBox();

                    if (currentBox.getSpeed() == 0) {
                        ++countFilling;
                        for (int i = 0; i < 4; i++)
                            point[currentBox.getPointH()[i]][currentBox.getPointW()[i]][0] = 1;
                        this.counter++;
                        char shapeType = this.chooseBox();
                        boardPanel.addBox(shapeType, oneX, oneY, intSpeed, point);
                    }

                    calibrate();

                    endGame();
                    try {
                        Thread.sleep(intSpeed);

                    } catch (Exception exc) {
                        continue;
                    }
                    disappear();
                    points.setBounds(frame.getWidth() - 100, 100, 100, 30);
                    level.setBounds(frame.getWidth() - 100, 150, 100, 30);
                    if(online)
                        results.setBounds(frame.getWidth() - 100, 200, 100, 90);
                    frame.repaint();
                    if(sum>=ending)
                        changeLevel();


        }

    }

    /**
     * method to start playing
     */
    public void go() {
        if (startOption.getStart() || (online && countFilling==0)) {
            if (countGo == 0) {
                countFilling = 0;
                for (int i = 0; i < boardPanel.getBox().size(); ++i)
                    boardPanel.getBox().get(i).end();


                for (int i = 0; i < quantity; i++)
                    for (int e = 0; e <quantity; e++)
                        point[i][e][0] = 0;

                for (int e = 0; e <14; e++)
                    point[quantity][e][0] = 1;
                go.setBounds(0, 3 * frame.getHeight(), 100, 30);
            }
            countGo = 1;

        }
    }

    /**
     * method to calibrate boxes
     */
    public void calibrate() {

        for (int i = 0; i < boardPanel.getBox().size(); ++i) {
            boardPanel.getBox().get(i).setWidth(boardPanel.getWidthG());

        }
        for (int i = 0; i < boardPanel.getBox().size(); ++i)
            boardPanel.getBox().get(i).setHeight(boardPanel.getHeightG());

    }

    /**
     * method to remove boxes when the government is full and awarding points
     */
    public void disappear() {
        int countD = 0;

        for (int i = 0; i < quantity; i++) {
            countD = 0;
            for (int e = 0; e < quantity; e++) {
                if (point[i][e][0] == 1) {
                    ++countD;

                    if (countD == quantity) {

                        sum = sum + 10;
                        points.setText("Punkty: " + sum);
                        if(lastLevel)
                            ending+=40;
                        for (int t = 0; t < quantity; t++)
                            point[i][t][0] = 0;
                        for (int w = 0; w < boardPanel.getBox().size(); ++w) {
                            boardPanel.getBox().get(w).disappear(i);

                        }



                        for (int n = i; n > 0; n--)
                            for (int t = 0; t < quantity; t++)
                                point[n][t][0] = point[n - 1][t][0];

                        try {
                            Thread.sleep(100);

                        } catch (Exception exc) {
                            continue;
                        }
                    }
                }

            }

        }

    }


    /**
     * counter to be awarded the level
     */
    private int nextLevelCount = 0;

    public void setNextLevelCount(int nextLevelCount) {
        this.nextLevelCount = nextLevelCount;
    }

    /**
     * method to go to the next level
     */

    public void changeLevel() throws InterruptedException, IOException {
       System.out.println("jestem w changeLevel()");
            countFilling = 0;
            if (!online) {
                //if (nextLevelCount == 1) {
                    countFilling = 0;
                    readLevelProperties(nextFileName);
                ++nextLevelCount;
                }

             else {
                    countFilling = 0;
                    parent.sendLevelAccomplished();
            }

            Thread.sleep(1000);
            drawNewLevel();
            level.setText(levelName);
    }

    /**
     * method to start a new level
     */
    public void drawNewLevel() {
        for (int i = 1; i < boardPanel.getBox().size(); ++i)
            boardPanel.getBox().get(i).end();

        for (int i = 0; i < quantity; i++)
            for (int e = 0; e <quantity; e++)
                point[i][e][0] = 0;

        for (int e = 0; e <quantity; e++)
            point[quantity][e][0] = 1;

        if(0!=filling){
            for(int i=0; i<filling; ++i)
                boardPanel.addBox('F', location, oneY, intSpeed, point);
        }

    }
    private boolean gameEndedMessageSent=false;

    /**
     * method to end the game
     * @throws IOException
     */
    public void endGame() throws IOException {
        if (countGo == 1) {
            if(online)
                speed="0";
            for (int i = 0; i < 29; i++)
                if (point[1][i][0] == 1 && point[4][i][0] == 1) {
                    if(online)
                        speed = "0";
                    go.setBounds(50, 300, 500, 90);
                    go.setText("Koniec gry");
                if(!online){
                    startOption.setPkt(sum);
                    startOption.canEnd(true);
                }else {
                    go.setText("Koniec gry. Twoje miejsce na liscie najlepszych wynikow: "+rank);
                    frame.setVisible(true);
                    if(!gameEndedMessageSent) {
                        parent.sendFinishedGamedata();
                        startOption.canEnd(true);
                        gameEndedMessageSent=true;
                    }
                }
                }
        } else {
            for (int i = 0; i < 29; i++)
                if (point[1][i][0] == 1) {
                    for (int e = 0; e < boardPanel.getBox().size(); ++e)
                        boardPanel.getBox().get(e).end();


                    for (int k = 0; k < 29; k++)
                        for (int e = 0; e < 29; e++)
                            point[k][e][0] = 0;
                }
        }


    }
}

