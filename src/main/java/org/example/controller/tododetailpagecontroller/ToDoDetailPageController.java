// src/main/java/org/example/controller/tododetailpagecontroller/ToDoDetailPageController.java
package org.example.controller.tododetailpagecontroller;

import org.example.model.ChecklistItem;
import javax.swing.*;


/**
 * The interface To do detail page controller.
 */
public interface ToDoDetailPageController {

    /**
     * The type Detail components.
     */
    record DetailComponents(
            JList<ChecklistItem> checklistJList,
            JCheckBox completaCheckBox,
            JLabel nomeToDoLabel,
            JPanel contentPanel,
            JLabel dataScadenza,
            JLabel ultimaModifica,
            JLabel utenteCodiviso,
            JButton cancellaButton
    ) {}

    /**
     * Initialize gui.
     *
     * @param ui the ui
     */
    void initializeGui(DetailComponents ui);

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

    /**
     * On cancella action.
     */
    void onCancellaAction();
}