package org.example.gui;
import org.example.controller.bachecamainpagecontroller.BachecaMainPageController;
import org.example.controller.bachecamainpagecontroller.BachecaMainPageControllerImpl;
import org.example.model.ToDo;

import javax.swing.*;
//credo finito
// fo sho
//hell naw
public class BachecaMainPage {
    private JList<ToDo> complete;
    private JPanel bachecaMainPagePanel;
    private JButton aggiungiButton;
    private JButton indietroButton;
    private JButton modificaButton;
    private JTextArea descrizione;
    private JTextField titolo;
    private JList<ToDo> noComplete;
    private JList<ToDo> expired;
    private final BachecaMainPageController controller= new BachecaMainPageControllerImpl();
    private boolean isCalled=false;

    public BachecaMainPage(){
        controller.setDescrizione(descrizione);
        controller.setTitolo(titolo);
        controller.setJLists(complete, noComplete, expired);
        controller.defaultListModelCreator(complete, noComplete, expired);
        indietroButton.addActionListener(e-> controller.returnToMainPage());
        aggiungiButton.addActionListener(e-> controller.goToCreaToDoPage());
        modificaButton.addActionListener(e -> controller.updateScreen(titolo,descrizione));
        complete.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && complete.getSelectedValue() != null) {
                    controller.goToToDoDetailsPage(e, complete.getSelectedValue());
                    controller.refreshToDoLists();
                }
        });
        noComplete.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && noComplete.getSelectedValue() != null) {
                controller.goToToDoDetailsPage(e, noComplete.getSelectedValue());
                controller.refreshToDoLists();
            }
        });
        expired.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && expired.getSelectedValue() != null) {
                controller.goToToDoDetailsPage(e, expired.getSelectedValue());
                controller.refreshToDoLists();
            }
        });
    }
    public JPanel getBachecaMainPage(){
        return bachecaMainPagePanel;
    }
}
