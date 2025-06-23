package org.example.gui;
import org.example.controller.bachecamainpagecontroller.BachecaMainPageController;
import org.example.controller.bachecamainpagecontroller.BachecaMainPageControllerImpl;
import org.example.model.ToDo;

import javax.swing.*;
//credo finito
// fo sho
public class BachecaMainPage {
    private JList<ToDo> toDoList;
    private JPanel bachecaMainPagePanel;
    private JButton aggiungiButton;
    private JButton indietroButton;
    private JButton modificaButton;
    private JTextArea descrizione;
    private JTextField titolo;
    private final BachecaMainPageController controller= new BachecaMainPageControllerImpl();


    public BachecaMainPage(){
        controller.setDescrizione(descrizione);
        controller.setTitolo(titolo);
        toDoList.setModel(controller.defaultListModelCreator());
        indietroButton.addActionListener(e-> controller.returnToMainPage());
        aggiungiButton.addActionListener(e-> controller.goToCreaToDoPage());
        modificaButton.addActionListener(e -> controller.updateScreen(titolo,descrizione));
        toDoList.addListSelectionListener(e -> controller.goToToDoDetailsPage(e, toDoList.getSelectedValue()));
    }
    public JPanel getBachecaMainPage(){
        return bachecaMainPagePanel;
    }
}
