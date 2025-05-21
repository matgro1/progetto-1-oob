package org.example.model.controller.mainpagecontroller;

import org.example.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;


public interface MainPageController {

    void returnHome(JFrame frame);
    DefaultListModel<Bacheca> creazioneLista(List<Bacheca> bacheche);
    void goToCreaBachecaPage(JFrame frame, Utente utente);
    void goToBachecaMainPage(ListSelectionEvent e, JFrame frame, Bacheca bachecaSelezionata,Utente utente);
}
