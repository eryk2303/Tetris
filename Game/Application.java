

import javax.swing.*;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * class needed to add a new player to the online game
 */

@SuppressWarnings("serial")
public class Application extends Frame {
    /**
     * window elements needed to add a player
     */
    private Label info;
    private TextField host, port, player;
    private Button login;
    /**
     * array in which clients are kept
     */
    private ArrayList<Client> clients = new ArrayList<>();

    /**
     * constructor describing a window in which new players can be added
     */

    Application() {
        super("Tetris - dodaj gracza");
        info = new Label("gracze", Label.CENTER);
        host = new TextField("localhost", 30);
        player = new TextField("player", 30);
        port = new TextField("40000", 8);
        login = new Button("login");
        login.addActionListener((actionEvent) -> {
            host.setEnabled(false);
            port.setEnabled(false);
            login.setEnabled(false);
            player.setEnabled(false);
            try {
                clients.add(new Client(host.getText(), port.getText(), player.getText()));
            } catch (Exception e) {
                info.setText(e.toString());
                JFrame frame = new JFrame("Ups!");
                frame.setSize(200, 200);
                Label description=new Label("Brak dostepnego serwera");
                description.setBounds(80, 100, 100, 30);
                frame.add(description);
                frame.setVisible(true);
                System.out.println("Brak dostepnego serwera");
                pack();
            }
            login.setEnabled(true);
            player.setEnabled(true);
            port.setEnabled(true);
            host.setEnabled(true);
        });

        setBackground(Color.lightGray);
        setLayout(new GridLayout(0, 1));
        add(info);
        Panel p100 = new Panel();
        p100.add(new Label(" host: ", Label.RIGHT));
        p100.add(host);
        add(p100);
        Panel p200 = new Panel();
        p200.add(new Label(" port: ", Label.RIGHT));
        p200.add(port);
        add(p200);
        Panel p300 = new Panel();
        p300.add(new Label(" nick: ", Label.RIGHT));
        p300.add(player);
        add(p300);
        Panel p400 = new Panel();
        p400.add(login);
        add(p400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                //destroy();
                System.exit(1);
            }
        });

        pack();
        EventQueue.invokeLater(() -> setVisible(true));
        new Thread(() -> {
            while (true) {
                //cleanHouse();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }

/*
    public void destroy() {
        for (Client c : clients) {
            c.forceLogout();
        }
    }

    void cleanHouse() {
        for (IBClient c : clients) {
            if (c.isDisconnected())
                clients.remove(c);// czy tak można? tj. czy można
            // usuwać elementy kolekcji w trakcie jej iterowania?
            // oczywiście, że nie można tak... napraw to!
        }
    }
*/
    }

       // public static void main (String[]args){
         //   new Application();
        //}

