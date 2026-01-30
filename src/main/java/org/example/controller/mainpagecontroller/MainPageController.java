package org.example.controller.mainpagecontroller;

import org.example.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;


/**
 * The interface Main page controller.
 */
public interface MainPageController {

    /**
     * Return home.
     */
    void returnHome();

    /**
     * Creazione lista default list model.
     *
     * @return the default list model
     */
    DefaultListModel<Bacheca> creazioneLista();

    /**
     * Go to crea bacheca page.
     */
    void goToCreaBachecaPage();

    /**
     * Go to dialog modifica utente.
     */
    void goToDialogModificaUtente();

    /**
     * Go to bacheca main page.
     *
     * @param e                  the e
     * @param bachecaSelezionata the bacheca selezionata
     */
    void goToBachecaMainPage(ListSelectionEvent e, Bacheca bachecaSelezionata);
}
