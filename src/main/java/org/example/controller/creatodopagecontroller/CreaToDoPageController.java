package org.example.controller.creatodopagecontroller;


import org.example.model.Bacheca;
import org.example.model.ChecklistItem;

import javax.swing.*;
import java.time.LocalDate;

/**
 * The interface Crea to do page controller.
 */
public interface CreaToDoPageController {
    /**
     * The type Date spinners.
     */
    public record DateSpinners(JSpinner giorno, JSpinner mese, JSpinner anno) {}

    /**
     * The type Condivisione ui.
     */
    public record CondivisioneUI(JTextField nomeUtente, JLabel utenteLabel, JComboBox<Bacheca> combo, JLabel comboLabel) {}

    /**
     * Inizializzazione.
     *
     * @param dateSpinners   the date spinners
     * @param condivisioneUI the condivisione ui
     * @param checkList      the check list
     */
    void inizializzazione(DateSpinners dateSpinners, CondivisioneUI condivisioneUI, JList<ChecklistItem> checkList);

    /**
     * The type To do form data.
     */
    public record ToDoFormData(
            String titolo,
            LocalDate data,
            boolean isCondiviso,
            String utenteTarget,
            Bacheca bachecaTarget
    ) {}

    /**
     * Crea to do.
     *
     * @param creaToDoPagePanel the crea to do page panel
     * @param formData          the form data
     * @param checkList         the check list
     */
    void creaToDo(JPanel creaToDoPagePanel, ToDoFormData formData, JList<ChecklistItem> checkList);

    /**
     * Aggiungi checklist item.
     *
     * @param checkList the check list
     */
    void aggiungiChecklistItem(JList<ChecklistItem> checkList);

    /**
     * Return bacheca main page.
     */
    void returnBachecaMainPage();

    /**
     * Update screen.
     *
     * @param condivisoCheckBox   the condiviso check box
     * @param nomeUtenteCondiviso the nome utente condiviso
     * @param condivisoLabel      the condiviso label
     * @param comboBacheca        the combo bacheca
     * @param clabel              the clabel
     */
    void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel,JComboBox<Bacheca> comboBacheca, JLabel clabel);

    /**
     * Update combo bacheca.
     *
     * @param comboBacheca        the combo bacheca
     * @param nomeUtenteCondiviso the nome utente condiviso
     */
    void updateComboBacheca(JComboBox<Bacheca> comboBacheca, JTextField nomeUtenteCondiviso);
}
