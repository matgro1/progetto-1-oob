package org.example.model;
import org.example.model.controller.bachecamainpagecontroller.BachecaMainPageController;
import org.example.model.controller.bachecamainpagecontroller.BachecaMainPageControllerImpl;

import javax.swing.*;
//credo finito
public class BachecaMainPage {
    private JList<ToDo> toDoList;
    private JPanel bachecaMainPagePanel;
    private JButton aggiungiButton;
    private JButton indietroButton;
    private JButton modificaButton;
    private JTextArea descrizione;
    private JTextField titolo;
    private final BachecaMainPageController controller= new BachecaMainPageControllerImpl();


    public BachecaMainPage(JFrame frame, Bacheca bacheca, Utente utente){
        controller.setDescrizione(descrizione,bacheca);
        controller.setTitolo(titolo,bacheca);
        toDoList.setModel(controller.defaultListModelCreator(bacheca.getToDo()));
        indietroButton.addActionListener(e-> controller.returnToMainPage(frame,utente));
        aggiungiButton.addActionListener(e-> controller.goToCreaToDoPage(frame,bacheca,utente));
        modificaButton.addActionListener(e -> controller.updateScreen(titolo,descrizione,frame,bacheca));
    }
    public JPanel getBachecaMainPage(){
        return bachecaMainPagePanel;
    }
}
