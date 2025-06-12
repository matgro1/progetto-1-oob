package org.example.controller.bachecamainpagecontroller;

import org.example.gui.CreaToDoPage;
import org.example.gui.MainPage;
import org.example.gui.ModificaBachecaPage;
import org.example.model.*;
import org.example.controller.ControllerFather;

import javax.swing.*;
import java.util.List;

public class BachecaMainPageControllerImpl extends ControllerFather implements BachecaMainPageController{
    public void returnToMainPage() {
        frame.getContentPane().removeAll();
        frame.setContentPane(new MainPage().getMainPage());
        frame.revalidate();
        frame.repaint();
    }
    public void setDescrizione(JTextArea descrizione) {
        descrizione.setText(bacheca.getDescrizione());
        descrizione.setEditable(false);
    }
    @Override
    public void setTitolo(JTextField titolo) {
        titolo.setText(bacheca.getNome());
        titolo.setEditable(false);
    }
    @Override
    public DefaultListModel<ToDo> defaultListModelCreator() {
        List<ToDo> todos= bacheca.getToDo();
        DefaultListModel<ToDo> toDoListModel = new DefaultListModel<>();
        for (ToDo todo : todos){
            toDoListModel.addElement(todo);
        }
        return toDoListModel;
    }
    @Override
    public void modificaBacheca() {
        ModificaBachecaPage dialog = new ModificaBachecaPage(bacheca);
        dialog.setVisible(true);

        if (dialog.isModificaConfermata()) {
            frame.setTitle("Bacheca: " + bacheca.getNome());
        }
    }
    @Override
    public void updateScreen(JTextField titolo, JTextArea descrizione) {

        this.modificaBacheca();
        this.setDescrizione(descrizione);
        this.setTitolo(titolo);
    }
    @Override
    public void goToCreaToDoPage() {
        frame.getContentPane().removeAll();
        frame.setContentPane(new CreaToDoPage().getCreaToDoPage());
        frame.revalidate();
        frame.repaint();

    }
}
