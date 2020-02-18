import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * class needed to create a new server window
 */
public class ServerStarter {

    private JButton online;
    private JFrame frame;

    /**
     * constructor describes the window by which a new server is created
     */
    public ServerStarter() {


        JButton online = new JButton("Nowy serwer");
        online.setBounds(125, 150, 250, 30);
        JFrame frame=new JFrame("Uruchamianie serwera");
        frame.setSize(200, 200);
        frame.add(online);
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
        });
        frame.setVisible(true);
    }
        public static void main(String[] args){
            new ServerStarter();


    }

}

