import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * class witch options
 */
public class Start {

    /**
     * amount of points
     */
    private int pkt = 0;
    /**
     * value to determine when it's game over
     */
    private boolean end = false;

    public Start() throws FileNotFoundException {
    }

    public boolean isOnline() {
        return online;
    }

    private boolean online=false;
    private String manual;



   // public StartOption getChosenOption() {
    //    return chosenOption;
    //}

    //private StartOption chosenOption;
    Data dataB = new Data();

    /**
     * constructor to create button with options
     */
    public void startVisible() {
        JFrame frameStart = new JFrame("Start");
        JButton instruction = new JButton("Instrukcja");
        frameStart.setSize(500, 500);
        instruction.setBounds(125, 250, 250, 30);
        frameStart.add(instruction);
        instruction.addActionListener(e -> {
            //chosenOption = StartOption.INSTRUKCJA;
            try {
                dataB.showManual();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        JButton localData = new JButton("Graj od razu");
        localData.setBounds(125, 100, 250, 30);
        frameStart.add(localData);
        localData.addActionListener(e->{
            online=false;
           // new Thread(()->{
            dataB.giveNick();
            //dataB.play();
            frameStart.dispose();});
            /*try {
                new WindowGame();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }*/

        //});
        /*JButton data = new JButton("Nazwa gracza (gra sieciowa)");
        data.setBounds(125, 150, 250, 30);
        frameStart.add(data);
        data.addActionListener(e -> {
            dataB.giveNick();
            //if(dataB.isOnline())
            online = true;

            frameStart.dispose();
        });*/
        /*JButton online=new JButton("Nowy serwer");
        online.setBounds(125, 150, 250, 30);
        frameStart.add(online);
        online.addActionListener(e -> {
            Properties p = new Properties();
            String pName = "Server.properties";
            try {
                p.load(new FileInputStream(pName));
            } catch (Exception ex) {
                p.put("port", "40000");
                p.put("width", "250");
                p.put("height", "250");
            }
            try {
                p.store(new FileOutputStream(pName), null);
            } catch (Exception exc) {
            }
            new Server(p, "Tetris Server");
        });*/
        JButton application=new JButton("Gra sieciowa");
        application.setBounds(125, 150, 250, 30);
        frameStart.add(application);
        application.addActionListener(e -> {
            frameStart.dispose();
            new Application();
        });

        JButton results = new JButton("Wyniki");
        results.setBounds(125, 200, 250, 30);
        frameStart.add(results);
        results.addActionListener(e -> {

            try {
                dataB.results();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }


        });

        frameStart.setLayout(null);
        frameStart.setVisible(true);

    }

    /**
     * Method to set points
     *
     * @param pkt - points
     */
    public void setPkt(int pkt) {
        this.pkt = pkt;
    }

    /**
     * method to communicate when to start the game
     *
     * @return when to start
     */
    public boolean getStart() {
        return dataB.start();
    }

    /**
     * method to transfer value
     *
     * @param end
     * @throws FileNotFoundException
     */
    public void canEnd(boolean end) throws IOException {
        this.end = end;
        if (end) {
            dataB.setPoints(pkt);
            dataB.end();
        }
    }

}