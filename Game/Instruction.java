import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * class with the instuction interface*
 */
public class Instruction extends javax.swing.JFrame {

    /**c
     * lass constructor calling methods to create ext place and panel
     */
    public Instruction() {
        initComponents();

    }




    /**
     *  methods to create text place and panel
     */
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        instructionText = new javax.swing.JTextPane();


        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane1.setViewportView(instructionText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );

        pack();
    }
    /**
     * methods to set instruction text
     */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {

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
    }



    private javax.swing.JTextPane instructionText;
    private javax.swing.JScrollPane jScrollPane1;

}
