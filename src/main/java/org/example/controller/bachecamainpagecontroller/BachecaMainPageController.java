package org.example.controller.bachecamainpagecontroller;

import org.example.model.ToDo;

import javax.swing.*;

public interface BachecaMainPageController {
    void returnToMainPage();
    void goToCreaToDoPage();

    void setDescrizione(JTextArea descrizione);
    void setTitolo(JTextField titolo);
    DefaultListModel<ToDo> defaultListModelCreator();
    void modificaBacheca();
    void updateScreen(JTextField titolo, JTextArea descrizione);
}
