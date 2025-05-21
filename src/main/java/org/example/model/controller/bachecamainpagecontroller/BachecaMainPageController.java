package org.example.model.controller.bachecamainpagecontroller;

import org.example.model.Bacheca;
import org.example.model.ToDo;
import org.example.model.Utente;

import javax.swing.*;
import java.util.List;

public interface BachecaMainPageController {
    void returnToMainPage(JFrame frame, Utente utente);
    void goToCreaToDoPage(JFrame frame, Bacheca bacheca, Utente utente);

    void setDescrizione(JTextArea descrizione, Bacheca bacheca);
    void setTitolo(JTextField titolo, Bacheca bacheca);
    DefaultListModel<ToDo> defaultListModelCreator(List<ToDo> todos);
    void modificaBacheca(Bacheca bacheca,JFrame frame);
    void updateScreen(JTextField titolo, JTextArea descrizione,JFrame frame, Bacheca bacheca);
}
