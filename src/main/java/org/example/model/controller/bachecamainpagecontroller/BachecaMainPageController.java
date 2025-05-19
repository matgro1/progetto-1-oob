package org.example.model.controller.bachecamainpagecontroller;

import org.example.model.Bacheca;
import org.example.model.Utente;

import javax.swing.*;

public interface BachecaMainPageController {
    public void returnToMainPage(JFrame frame, Utente utente);
    public void goToCreaBachecaPage(JFrame frame, Bacheca bacheca, Utente utente);
}
