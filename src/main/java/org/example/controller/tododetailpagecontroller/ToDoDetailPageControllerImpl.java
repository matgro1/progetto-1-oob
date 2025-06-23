
package org.example.controller.tododetailpagecontroller;

import org.example.controller.ControllerFather;
import org.example.model.ChecklistItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToDoDetailPageControllerImpl extends ControllerFather implements ToDoDetailPageController {

    private DefaultListModel<ChecklistItem> listModel;

    public ToDoDetailPageControllerImpl() {
        if (ControllerFather.todo == null) {
            throw new IllegalStateException("ToDo non impostato in ControllerFather.todo prima di creare ToDoDetailPageControllerImpl.");
        }
        this.listModel = new DefaultListModel<>();
    }

    @Override
    public void initializeGui(JList<ChecklistItem> checklistJList, JLabel nomeToDoLabel) {
        nomeToDoLabel.setText(ControllerFather.todo.getTitolo());

        listModel.clear();
        if (ControllerFather.todo.getChecklist() != null) {
            for (ChecklistItem item : ControllerFather.todo.getChecklist()) {
                listModel.addElement(item);
            }
        }
        checklistJList.setModel(listModel);

        checklistJList.setCellRenderer(new ListCellRenderer<ChecklistItem>() {
            private final JPanel panel = new JPanel(new BorderLayout());
            private final JCheckBox checkBox = new JCheckBox();

            {
                checkBox.setOpaque(false);
                checkBox.setFocusPainted(false);
                checkBox.setBorderPainted(false);
                panel.add(checkBox, BorderLayout.WEST);
                panel.setOpaque(true);
                panel.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2));
            }

            @Override
            public Component getListCellRendererComponent(JList<? extends ChecklistItem> list,
                                                          ChecklistItem value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                checkBox.setText(value.getDescrizione());
                checkBox.setSelected(value.isCompletato());
                checkBox.setEnabled(list.isEnabled());
                checkBox.setFont(list.getFont());

                if (isSelected) {
                    panel.setBackground(list.getSelectionBackground());
                    checkBox.setForeground(list.getSelectionForeground());
                } else {
                    panel.setBackground(list.getBackground());
                    checkBox.setForeground(list.getForeground());
                }
                return panel;
            }
        });

        checklistJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JList<ChecklistItem> sourceList = (JList<ChecklistItem>) e.getSource();
                int index = sourceList.locationToIndex(e.getPoint());
                if (index != -1) {
                    toggleChecklistItemAction(index, sourceList);
                }
            }
        });
    }

    @Override
    public void toggleChecklistItemAction(int index, JList<ChecklistItem> checklistJList) {
        if (index >= 0 && index < ControllerFather.todo.getChecklist().size()) {
            ChecklistItem item = ControllerFather.todo.getChecklist().get(index);
            boolean oldState = item.isCompletato();
            item.setStato(!oldState);


            listModel.setElementAt(item, index);



            ControllerFather.todo.verificaChecklist();
        }
    }

    @Override
    public void onOkAction() {
        System.out.println("Controller: Azione OK eseguita. Stato finale ToDo: " + ControllerFather.todo.getTitolo() + " -> " + ControllerFather.todo.getStato());
    }

    @Override
    public void onCancelAction() {
        System.out.println("Controller: Azione Annulla eseguita. Nessuna modifica salvata.");
    }
}