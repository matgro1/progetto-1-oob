// src/main/java/org/example/controller/tododetailpagecontroller/ToDoDetailPageController.java
package org.example.controller.tododetailpagecontroller;

import org.example.model.ChecklistItem;
import javax.swing.*;


/**
 * The interface To do detail page controller.
 */
public interface ToDoDetailPageController {

    /**
     * Initialize gui.
     *
     * @param checklistJList   the checklist j list
     * @param completaCheckBox the completa check box
     * @param nomeToDoLabel    the nome to do label
     * @param contentPanel     the content panel
     * @param dataScadenza     the data scadenza
     * @param ultimaModifica   the ultima modifica
     * @param utenteCodiviso   the utente codiviso
     */
    void initializeGui(JList<ChecklistItem> checklistJList, JCheckBox completaCheckBox, JLabel nomeToDoLabel, JPanel contentPanel,JLabel dataScadenza,JLabel ultimaModifica,JLabel utenteCodiviso);

    /**
     * Toggle checklist item action.
     *
     * @param index          the index
     * @param checklistJList the checklist j list
     */
    void toggleChecklistItemAction(int index, JList<ChecklistItem> checklistJList);

    /**
     * On ok action.
     */
    void onOkAction();

    /**
     * On cancel action.
     */
    void onCancelAction();
}