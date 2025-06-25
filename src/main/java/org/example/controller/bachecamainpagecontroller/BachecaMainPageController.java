package org.example.controller.bachecamainpagecontroller;

import org.example.model.ToDo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public interface BachecaMainPageController {
    void returnToMainPage();
    void goToCreaToDoPage();
    void goToToDoDetailsPage(ListSelectionEvent e, ToDo toDoSelezionato);
    void refreshToDoLists();

    void setJLists(JList<ToDo> complete, JList<ToDo> noComplete, JList<ToDo> expired);
    void setDescrizione(JTextArea descrizione);
    void setTitolo(JTextField titolo);
    void defaultListModelCreator(JList<ToDo> complete, JList<ToDo> noComplete,JList<ToDo> expired);
    void modificaBacheca();
    void updateScreen(JTextField titolo, JTextArea descrizione);
}
