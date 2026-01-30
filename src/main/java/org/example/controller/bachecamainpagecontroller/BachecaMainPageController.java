package org.example.controller.bachecamainpagecontroller;

import org.example.model.ToDo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

/**
 * The interface Bacheca main page controller.
 */
public interface BachecaMainPageController {
    /**
     * Return to main page.
     */
    void returnToMainPage();

    /**
     * Go to crea to do page.
     */
    void goToCreaToDoPage();

    /**
     * Go to to do details page.
     *
     * @param e               the e
     * @param toDoSelezionato the to do selezionato
     */
    void goToToDoDetailsPage(ListSelectionEvent e, ToDo toDoSelezionato);

    /**
     * Refresh to do lists.
     */
    void refreshToDoLists();

    /**
     * Sets j lists.
     *
     * @param complete   the complete
     * @param noComplete the no complete
     * @param expired    the expired
     */
    void setJLists(JList<ToDo> complete, JList<ToDo> noComplete, JList<ToDo> expired);

    /**
     * Sets descrizione.
     *
     * @param descrizione the descrizione
     */
    void setDescrizione(JTextArea descrizione);

    /**
     * Sets titolo.
     *
     * @param titolo the titolo
     */
    void setTitolo(JTextField titolo);

    /**
     * Default list model creator.
     *
     * @param complete   the complete
     * @param noComplete the no complete
     * @param expired    the expired
     */
    void defaultListModelCreator(JList<ToDo> complete, JList<ToDo> noComplete,JList<ToDo> expired);

    /**
     * Modifica bacheca.
     */
    void modificaBacheca();

    /**
     * Update screen.
     *
     * @param titolo      the titolo
     * @param descrizione the descrizione
     */
    void updateScreen(JTextField titolo, JTextArea descrizione);
}
