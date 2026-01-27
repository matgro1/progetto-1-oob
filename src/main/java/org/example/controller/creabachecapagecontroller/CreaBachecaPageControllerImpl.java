package org.example.controller.creabachecapagecontroller;


import org.example.database.DatabaseConnection;
import org.example.gui.MainPage;
import org.example.controller.ControllerFather;
import org.example.model.Bacheca;

import javax.swing.*;

public class CreaBachecaPageControllerImpl extends ControllerFather implements CreaBachecaPageController {
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
            DatabaseConnection.bachecaDB.save(new Bacheca(inputTitolo, inputDescrizione, utente.getId()));
            frame.setVisible(true);
            frame.getContentPane().removeAll();
            frame.setContentPane(new MainPage().getMainPage());
            frame.revalidate();
            frame.repaint();
        }
    }
}
