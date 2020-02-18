/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * serving class
 */
public class Tetris {


    public static void main(String args[]) throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {
        /*Properties p = new Properties();
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
         }*/
        // new Server(p, "Tetris Server");
        //Start menu=new Start();
        //menu.startVisible();
        //new Application();

        WindowGame firstWindow = new WindowGame();

    }


}
