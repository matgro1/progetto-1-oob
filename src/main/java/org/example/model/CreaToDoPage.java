package org.example.model;
import java.time.LocalDate;
import javax.swing.*;
//todo usare una finestra modale invece di sta merda
public class CreaToDoPage {
    private JPanel creaToDoPagePanel;
    private JTextField titoloField;
    private JSpinner giorno;
    private JSpinner mese;
    private JSpinner anno;
    private JButton annullaButton;
    private JButton creaButton;

    public CreaToDoPage(JFrame frame,Bacheca bacheca,Utente returnUtente) {
        SpinnerNumberModel giornoModel = new SpinnerNumberModel(1, 1, 31, 1);
        giorno.setModel(giornoModel);
        SpinnerNumberModel meseModel = new SpinnerNumberModel(1, 1, 12, 1);
        mese.setModel(meseModel);
        SpinnerNumberModel annoModel = new SpinnerNumberModel(2025, 2025, 2050, 1);
        anno.setModel(annoModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(anno, "#");
        anno.setEditor(editor);
        annullaButton.addActionListener(e-> {
                frame.getContentPane().removeAll();
                frame.setContentPane(new BachecaMainPage(frame, bacheca, returnUtente).getBachecaMainPage());
                frame.revalidate();
                frame.repaint();
        });
        creaButton.addActionListener(e->{
            int g = (int) giorno.getValue();
            int m = (int) mese.getValue();
            int a = (int) anno.getValue();

            LocalDate data = LocalDate.of(a, m, g);
            bacheca.getToDo().add(new ToDo(titoloField.getText(),data));
            frame.getContentPane().removeAll();
            frame.setContentPane(new BachecaMainPage(frame, bacheca, returnUtente).getBachecaMainPage());
            frame.revalidate();
            frame.repaint();
        });



    }

    public JPanel getCreaToDoPage() {
        return creaToDoPagePanel;
    }
}
