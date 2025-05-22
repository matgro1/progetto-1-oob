package org.example.model.controller.mainpagecontroller;

import org.example.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;


public interface MainPageController {

    void returnHome();
    DefaultListModel<Bacheca> creazioneLista();
    void goToCreaBachecaPage();
    void goToBachecaMainPage(ListSelectionEvent e, Bacheca bachecaSelezionata);
}
