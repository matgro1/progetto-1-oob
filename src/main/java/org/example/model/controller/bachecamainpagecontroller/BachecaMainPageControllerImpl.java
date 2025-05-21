package org.example.model.controller.bachecamainpagecontroller;

import org.example.model.*;

import javax.swing.*;
import java.util.List;

public class BachecaMainPageControllerImpl implements BachecaMainPageController{
    public void returnToMainPage(JFrame frame, Utente utente) {
        frame.getContentPane().removeAll();
        frame.setContentPane(new MainPage(frame,utente).getMainPage());
        frame.revalidate();
        frame.repaint();
    }

    public void setDescrizione(JTextArea descrizione, Bacheca bacheca) {
        descrizione.setText(bacheca.getDescrizione());
        descrizione.setEditable(false);
    }

    @Override
    public void setTitolo(JTextField titolo, Bacheca bacheca) {

    }

    @Override
    public DefaultListModel<ToDo> defaultListModelCreator(List<ToDo> todos) {

        DefaultListModel<ToDo> toDoListModel = new DefaultListModel<>();
        for (ToDo todo : todos){
            toDoListModel.addElement(todo);
        }
        return toDoListModel;
    }

    @Override
    public void modificaBacheca(Bacheca bacheca, JFrame frame) {
        ModificaBacheca dialog = new ModificaBacheca(bacheca, frame);
        dialog.setVisible(true);

        // Se l'utente ha confermato le modifiche, aggiorna l'interfaccia
        if (dialog.isModificaConfermata()) {
            // Aggiorna l'interfaccia se necessario
            // Ad esempio, aggiorna il titolo della finestra o altri componenti
            frame.setTitle("Bacheca: " + bacheca.getNome());
        }
    }

    @Override
    public void goToCreaToDoPage(JFrame frame, Bacheca bacheca, Utente utente) {
        frame.getContentPane().removeAll();
        frame.setContentPane(new CreaToDoPage(frame,bacheca,utente).getCreaToDoPage());
        frame.revalidate();
        frame.repaint();

    }
}
