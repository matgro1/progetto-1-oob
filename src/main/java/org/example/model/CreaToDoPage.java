package org.example.model;
import org.example.model.controller.creatodopagecontroller.CreaToDoPageController;
import org.example.model.controller.creatodopagecontroller.CreaToDoPageControllerImpl;

import java.time.LocalDate;
import javax.swing.*;
public class CreaToDoPage {
    private JPanel creaToDoPagePanel;
    private JTextField titoloField;
    private JSpinner giorno;
    private JSpinner mese;
    private JSpinner anno;
    private JButton annullaButton;
    private JButton creaButton;
    private JCheckBox condivisoCheckBox;
    private JTextField nomeUtenteCondiviso;
    private JLabel condivisoLabel;

    CreaToDoPageController controller= new CreaToDoPageControllerImpl();
    public CreaToDoPage(JFrame frame,Bacheca bacheca,Utente returnUtente) {
        controller.inizializzazione(giorno,mese,anno,nomeUtenteCondiviso,condivisoLabel);

        annullaButton.addActionListener(e-> controller.returnBachecaMainPage(frame, bacheca, returnUtente));
        condivisoCheckBox.addActionListener(e->controller.updateScreen(condivisoCheckBox,nomeUtenteCondiviso,condivisoLabel));
        creaButton.addActionListener(e->{
            //todo implementare condiviso o no
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
