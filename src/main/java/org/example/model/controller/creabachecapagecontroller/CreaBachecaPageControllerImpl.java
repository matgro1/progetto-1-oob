package org.example.model.controller.creabachecapagecontroller;


import org.example.model.MainPage;
import org.example.model.controller.Controller;

import javax.swing.*;

public class CreaBachecaPageControllerImpl extends Controller implements CreaBachecaPageController {
    @Override
    public void returnMainPage() {
        frame.setVisible(true);
        frame.getContentPane().removeAll();
        frame.setContentPane(new MainPage().getMainPage());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void creaBacheca(JPanel creaBachecaPagePanel, JTextField textField1, JTextField textField2) {
        String inputTitolo = textField1.getText();
        String inputDescrizione = textField2.getText();
        if(inputTitolo.isEmpty()){

            JOptionPane.showMessageDialog(creaBachecaPagePanel, "titolo non valido!", "Errore di registrazione", JOptionPane.ERROR_MESSAGE);
        }
        else{
            utente.creaBacheca(inputTitolo,inputDescrizione);
            frame.setVisible(true);
            frame.getContentPane().removeAll();
            frame.setContentPane(new MainPage().getMainPage());
            frame.revalidate();
            frame.repaint();
        }    }
}
