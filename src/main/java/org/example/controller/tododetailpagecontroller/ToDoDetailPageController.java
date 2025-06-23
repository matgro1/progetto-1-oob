// src/main/java/org/example/controller/tododetailpagecontroller/ToDoDetailPageController.java
package org.example.controller.tododetailpagecontroller;

import org.example.model.ChecklistItem;
import javax.swing.*;


public interface ToDoDetailPageController {
    void initializeGui(JList<ChecklistItem> checklistJList, JLabel nomeToDoLabel);
    void toggleChecklistItemAction(int index, JList<ChecklistItem> checklistJList);
    void onOkAction();
    void onCancelAction();
}