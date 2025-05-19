package org.example.model;
import org.example.model.controller.bachecamainpagecontroller.BachecaMainPageController;

import javax.swing.*;

public class BachecaMainPage {
    private JList<ToDo> toDoList;
    private JPanel bachecaMainPagePanel;
    private JButton aggiungiButton;
    private JButton indietroButton;
    private BachecaMainPageController controller;


    public BachecaMainPage(JFrame frame, Bacheca bacheca, Utente utente){
        DefaultListModel<ToDo> toDoListModel = new DefaultListModel<>();
        for (ToDo todo : bacheca.getToDo()){
            toDoListModel.addElement(todo);
        }
        toDoList.setModel(toDoListModel);
        indietroButton.addActionListener(e-> controller.returnToMainPage(frame,utente));
        aggiungiButton.addActionListener(e-> controller.goToCreaBachecaPage(frame,bacheca,utente));



    }

    public JPanel getBachecaMainPage(){
        return bachecaMainPagePanel;
    }
}
