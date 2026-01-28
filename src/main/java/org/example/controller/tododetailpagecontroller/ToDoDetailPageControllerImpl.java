package org.example.controller.tododetailpagecontroller;

import org.example.controller.ControllerFather;
import org.example.controller.SessionManager;
import org.example.dao.UtenteDAO.UtenteDAO;
import org.example.database.DatabaseConnection;
import org.example.model.ChecklistItem;
import org.example.model.ToDo;
import org.example.model.ToDoCondiviso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ToDoDetailPageControllerImpl extends ControllerFather implements ToDoDetailPageController {

    private DefaultListModel<ChecklistItem> listModel;
    private JList<ChecklistItem> currentChecklistJList;
    private JCheckBox currentCompletaCheckBox;
    private JPanel mainContentPanel;

    public ToDoDetailPageControllerImpl() {
        if (SessionManager.getInstance().getCurrentToDo() == null) {
            throw new IllegalStateException("ToDo non impostato nella Sessione prima di creare ToDoDetailPageControllerImpl.");
        }
        this.listModel = new DefaultListModel<>();
    }

    @Override
    public void initializeGui(JList<ChecklistItem> checklistJList, JCheckBox completaCheckBox, JLabel nomeToDoLabel, JPanel contentPanel, JLabel dataScadenza, JLabel ultimaModifica, JLabel utenteCodiviso) {
        this.currentChecklistJList = checklistJList;
        this.currentCompletaCheckBox = completaCheckBox;
        this.mainContentPanel = contentPanel;

        ToDo todo = SessionManager.getInstance().getCurrentToDo();

        ultimaModifica.setVisible(false);
        utenteCodiviso.setVisible(false);

        if (todo.getDataScadenza() != null) {
            dataScadenza.setText("Scadenza: " + todo.getDataScadenza().toString());
        } else {
            dataScadenza.setText("Scadenza: N/A");
        }

        if (todo instanceof ToDoCondiviso tdc) {
            ultimaModifica.setVisible(true);
            utenteCodiviso.setVisible(true);
            ultimaModifica.setText("Ultima modifica da : " + UtenteDAO.getNameById(tdc.getUltimoModificatoreId()));
            if( tdc.getUtenteCreatoreId() != SessionManager.getInstance().getCurrentUser().getId())
                utenteCodiviso.setText("Condiviso con : " + UtenteDAO.getNameById(tdc.getUtenteCreatoreId()));
            else
                utenteCodiviso.setText("Condiviso con : " + UtenteDAO.getNameById(tdc.getUtenteCondivisoId()));
        }
        nomeToDoLabel.setText(todo.getTitolo());

        List<ChecklistItem> checklist = DatabaseConnection.checklistItemDB.findByToDoId(todo.getId());

        Component centerComponent = ((BorderLayout) mainContentPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) {
            mainContentPanel.remove(centerComponent);
        }

        if (checklist != null && !checklist.isEmpty()) {
            listModel.clear();
            for (ChecklistItem item : checklist) {
                listModel.addElement(item);
            }
            currentChecklistJList.setModel(listModel);

            currentChecklistJList.setCellRenderer(new ListCellRenderer<ChecklistItem>() {
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

            currentChecklistJList.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JList<ChecklistItem> sourceList = (JList<ChecklistItem>) e.getSource();
                    int index = sourceList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        toggleChecklistItemAction(index, sourceList);
                    }
                }
            });

            mainContentPanel.add(new JScrollPane(currentChecklistJList), BorderLayout.CENTER);

        } else {
            currentCompletaCheckBox.setSelected(todo.isCompletato());

            currentCompletaCheckBox.addActionListener(e -> {
                todo.setCompletato(currentCompletaCheckBox.isSelected());
            });
            mainContentPanel.add(currentCompletaCheckBox, BorderLayout.CENTER);
        }

        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    @Override
    public void toggleChecklistItemAction(int index, JList<ChecklistItem> checklistJList) {
        ChecklistItem item = listModel.getElementAt(index);
        item.setStato(!item.isCompletato());

        DatabaseConnection.checklistItemDB.update(item);

        listModel.setElementAt(item, index);
        checklistJList.repaint();

        verificaStatoToDoChecklist();
    }

    private void verificaStatoToDoChecklist() {
        boolean tuttiCompletati = true;
        for (int i = 0; i < listModel.size(); i++) {
            if (!listModel.get(i).isCompletato()) {
                tuttiCompletati = false;
                break;
            }
        }
        ToDo todo = SessionManager.getInstance().getCurrentToDo();
        todo.setCompletato(tuttiCompletati);
    }

    @Override
    public void onOkAction() {
        ToDo todo = SessionManager.getInstance().getCurrentToDo();

        if(todo instanceof ToDoCondiviso) {
            DatabaseConnection.todoCondivisoDB.update((ToDoCondiviso) todo);
        } else {
            DatabaseConnection.todoDB.update(todo);
        }

        System.out.println("Salvataggio ToDo: " + todo.getTitolo() + " - Completato: " + todo.isCompletato());
    }

    @Override
    public void onCancelAction() {
        System.out.println("Modifiche annullate.");
    }
}