package org.example.model.controller.bachecamainpagecontroller;

import org.example.model.Bacheca;
import org.example.model.CreaToDoPage;
import org.example.model.MainPage;
import org.example.model.Utente;

import javax.swing.*;

public class BachecaMainPageControllerImpl implements BachecaMainPageController{
    public void returnToMainPage(JFrame frame, Utente utente) {
        frame.getContentPane().removeAll();
        frame.setContentPane(new MainPage(frame,utente).getMainPage());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void goToCreaBachecaPage(JFrame frame, Bacheca bacheca, Utente utente) {
        frame.getContentPane().removeAll();
        frame.setContentPane(new CreaToDoPage(frame,bacheca,utente).getCreaToDoPage());
        frame.revalidate();
        frame.repaint();

    }
}
