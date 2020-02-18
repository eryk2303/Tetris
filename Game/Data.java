import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/**
 * class witch options to create nick
 */
class Data extends JFrame implements ActionListener {

    /**
     * all points and nicks
     */
    String resultAll = "";
    String resultString[] = new String[3];
    String resultNick[] = new String[3];

    /**
     * number of points
     */
    private int pkt = 0;
    /**
     * label need to presentation results
     *
     */
    Label results[] = new Label[3];

    /**
     * TextField need to add nick
     */
    JTextField nick;
    /**
     * Frame need to add nick
     */
    JFrame frameDate;
    /**
     * add nick
     */
    JButton send;
    /**
     * Frame need to presentation results
     */
    JFrame frameResults;
    /**
     * when start
     */
    private boolean start = false;

    //public boolean isOnline() {
        //return online;
    //}

    //private boolean online;
    /**
     * string need to add nick
     */
    private String nickPlayer = "";
private int count = 0;
    /**
     * constructor to read results
     * @throws FileNotFoundException
     */
    public Data() throws FileNotFoundException {

//        file = new BufferedReader(new FileReader("resources/Result.txt"));
        InputStream file = getClass().getResourceAsStream("Result.txt");
        
if(file!=null)
{
	Scanner in = new Scanner(file);

        String result = "";

        while (in.hasNextLine())
        {

            result = in.nextLine();
            resultString[count] =  result;
            ++count;
            if(count > 2)
                break;
        }
        in.close();
}
    }
    /**
     * constructor witch options to create nick
     */
    public void giveNick()
    {
        frameDate = new JFrame("Nick");
        send = new JButton("wyslij");
        send.addActionListener(this);
        nick = new JTextField(16);

        JPanel panelData = new JPanel();

        panelData.add(nick);
        panelData.add(send);
        frameDate.add(panelData);
        frameDate.setSize(500, 500);
        frameDate.show();
    }

    public void play()
    {
        frameDate = new JFrame("Tetris");
        send = new JButton("Graj od razu");
        Label description=new Label("Nie bedziesz mial(a) mozliwosci rywalizacji z innymi graczami");
        description.setBounds(80, 100, 400, 30);
        frameDate.add(description);
        send.addActionListener(this);

        JPanel panelData = new JPanel();

        panelData.add(send);
        frameDate.add(panelData);
        frameDate.setSize(500, 500);
        frameDate.show();
    }

    /**
     * method to show instruction
     * @throws IOException
     */
    public void showManual() throws IOException {
        frameDate = new JFrame("Instrukcja");
        JPanel panelData = new JPanel();
        JTextPane instructionText = new JTextPane();
        instructionText.setContentType("text/html");
        instructionText.setText("<html>\n Zadaniem Użytkownika jest układanie opadających klocków w studni. Dopóki klocek spada,\n" +
                "można go obracać, używając klawisza klawiatury (strzałki w górę), a także przesuwać na boki,\n" +
                "używając klawiszy strzałek w bok. Kiedy na jednym poziomie cała szerokość studni zostanie\n" +
                "zapełniona, elementy ją stanowiące znikają, a Użytkownik otrzymuje 10 punktów. Po uzyskaniu 10\n" +
                "punktów Użytkownik przechodzi na średni poziom trudności, a po uzyskaniu 20 punktów – na poziom\n" +
                "najtrudniejszy. Czarnych klocków nie da się przesuwać - są dodatkowym utrudnieniem dla Gracza.\n" +
                "Gra kończy się, kiedy cała wysokość studni zostanie zapełniona.\n" +
                "Aby rozpocząć grę, należy uruchomić aplikację, a następnie kliknąć klawisz \"enter\" i z menu\n" +
                "głównego wybrać opcję „start”. Należy podać swoją nazwę i kliknąć „wyślij\".\n" +
                "Możliwa jest również gra sieciowa - wówczas z menu głównego należy wybrać opcję \"gra sieciowa\",\n" +
                "podać swój login i kliknąć przycisk \"login\".\n" +
                "Uruchamianie gry:\n" +
                "1. Proszę zapisać plik tetris_new. jar na pulpicie.\n" +
                "2. Proszę uruchomić terminal (konsolę).\n" +
                "3. Proszę w wierszu poleceń wpisać komendę cd Desktop i kliknąć \"enter\".\n" +
                "4. Proszę w wierszu poleceń wpisać komendę java -jar tetris_new.jar i kliknąć \"enter\".</html>");
        frameDate.add(panelData);
        frameDate.add(instructionText);
        frameDate.setSize(1100, 200);
        frameDate.show();
    }

    /**
     * method to add nick
     * @param e
     */
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("wyslij")) {
            nickPlayer = nick.getText();
            frameDate.dispose();
            start = true;
        }
        else if (s.equals("Graj od razu")){
            frameDate.dispose();
            start = true;
        }

    }

    /**
     * method to show results
     * @throws FileNotFoundException
     */
    public void results() throws FileNotFoundException {
        frameResults = new JFrame("Wyniki");
        for(int i = 0; i<3; ++i) {
            if (resultString[i] != null) {


                results[i] = new Label();
                results[i].setFont(new Font("Verdana", Font.PLAIN, 20));
                results[i].setBounds(200, i * 50, 100, 50);
                results[i].setText(String.valueOf(i + 1) + "." + " " + resultString[i]);
                frameResults.add(results[i]);
            }
        }
        Label tmp = new Label();
        frameResults.add(tmp);
        frameResults.setSize(500, 500);
        frameResults.show();
    }


    /**
     * method to set points
     * @param pkt
     */
    public void setPoints(int pkt){this.pkt = pkt;}

    /**
     * when start play
     * @return start
     */
    public boolean start()
    {
        return start;
    }
    String[] nickAll = new String[3];
    String[] resultPlay = new String[3];
    int countP = 0;
    /**
     * method to write new result
     * @throws IOException
     */
    public void end() throws IOException {

        if (countP == 0) {
            FileWriter fileWriter = null;
            resultAll = "";
            for (int i = 0; i < 3; i++) {

                if(resultString[i] == null)
                    break;
                resultAll += resultString[i] + " ";
                nickAll[i] = "";
                resultPlay[i] = "0";
            }
            String[] All = resultAll.split(" ");
            if(All.length>=1) {
                nickAll[0] = All[0];
                resultPlay[0] = All[1];
            }

            if(All.length>=3) {
                nickAll[1] = All[2];
                resultPlay[1] = All[3];
            }

            if(All.length>=4) {
                resultPlay[2] = All[5];
                nickAll[2] = All[4];
            }
        for(int i = 0; i<3; i++) {
            if ( Integer.parseInt(resultPlay[i]) <= pkt)
            {
                if(i != 2)
                {
                    for(int e = 2; e>0; --e) {
                        nickAll[e] = nickAll[e-1];
                        resultPlay[e] = resultPlay[e-1];
                    }

                }
                    nickAll[i] = nickPlayer;
                    resultPlay[i] = Integer.toString(pkt);
                    break;
            }
        }
       resultAll = "";
        for(int i = 0; i<3; i++) {
            resultAll +=  nickAll[i] + " " + resultPlay[i] + "\n";


        }

            try {
                fileWriter = new FileWriter("Result.txt");

                fileWriter.write(resultAll + "\n");
                ++countP;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            }
        }

    }

}
