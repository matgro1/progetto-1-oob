package org.example.controller.tododetailpagecontroller;

import org.example.controller.SessionManager;
import org.example.dao.utentedao.UtenteDAO;
import org.example.database.DatabaseConnection;
import org.example.model.ChecklistItem;
import org.example.model.ToDo;
import org.example.model.ToDoCondiviso;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The type To do detail page controller.
 */
public class ToDoDetailPageControllerImpl implements ToDoDetailPageController {

    private static final Logger LOGGER = Logger.getLogger(ToDoDetailPageControllerImpl.class.getName());
    private final DefaultListModel<ChecklistItem> listModel;
    private JCheckBox currentCompletaCheckBox;

    /**
     * Instantiates a new To do detail page controller.
     */
    public ToDoDetailPageControllerImpl() {
        if (SessionManager.getInstance().getCurrentToDo() == null) {
            throw new IllegalStateException("ToDo non impostato nella Sessione prima di creare ToDoDetailPageControllerImpl.");
        }
        this.listModel = new DefaultListModel<>();
    }

    @Override
    public void initializeGui(DetailComponents ui) {
        this.currentCompletaCheckBox = ui.completaCheckBox();
        ToDo todo = SessionManager.getInstance().getCurrentToDo();

        pulisciListenerCancella(ui.cancellaButton());
        ui.nomeToDoLabel().setText(todo.getTitolo());

        impostaDataScadenza(ui.dataScadenza(), todo);
        impostaInfoCondivisione(ui, todo);
        configuraPannelloCentrale(ui, todo);
    }

    private void pulisciListenerCancella(JButton cancellaButton) {
        for (java.awt.event.ActionListener al : cancellaButton.getActionListeners()) {
            cancellaButton.removeActionListener(al);
        }
    }

    private void impostaDataScadenza(JLabel dataScadenzaLabel, ToDo todo) {
        if (todo.getDataScadenza() != null) {
            dataScadenzaLabel.setText("Scadenza: " + todo.getDataScadenza());
        } else {
            dataScadenzaLabel.setText("Scadenza: N/A");
        }
    }

    private void impostaInfoCondivisione(DetailComponents ui, ToDo todo) {
        ui.ultimaModifica().setVisible(false);
        ui.utenteCodiviso().setVisible(false);

        if (todo instanceof ToDoCondiviso tdc) {
            ui.ultimaModifica().setVisible(true);
            ui.utenteCodiviso().setVisible(true);
            ui.ultimaModifica().setText("Ultima modifica da : " + UtenteDAO.getNameById(tdc.getUltimoModificatoreId()));

            int currentUserId = SessionManager.getInstance().getCurrentUser().getId();
            if (tdc.getUtenteCreatoreId() != currentUserId) {
                ui.utenteCodiviso().setText("Condiviso con : " + UtenteDAO.getNameById(tdc.getUtenteCreatoreId()));
            } else {
                ui.utenteCodiviso().setText("Condiviso con : " + UtenteDAO.getNameById(tdc.getUtenteCondivisoId()));
            }
        }
    }

    private void configuraPannelloCentrale(DetailComponents ui, ToDo todo) {
        Component centerComponent = ((BorderLayout) ui.contentPanel().getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) {
            ui.contentPanel().remove(centerComponent);
        }

        List<ChecklistItem> checklist = DatabaseConnection.checklistItemDB.findByToDoId(todo.getId());

        if (checklist != null && !checklist.isEmpty()) {
            mostraChecklist(ui, checklist);
        } else {
            mostraSingolaCheckBox(ui, todo);
        }

        ui.contentPanel().revalidate();
        ui.contentPanel().repaint();
    }

    private void mostraChecklist(DetailComponents ui, List<ChecklistItem> checklist) {
        listModel.clear();
        for (ChecklistItem item : checklist) {
            listModel.addElement(item);
        }
        ui.checklistJList().setModel(listModel);
        ui.checklistJList().setCellRenderer(new ChecklistRenderer());
        ui.checklistJList().addMouseListener(creaMouseListenerChecklist());

        ui.contentPanel().add(new JScrollPane(ui.checklistJList()), BorderLayout.CENTER);
    }

    private void mostraSingolaCheckBox(DetailComponents ui, ToDo todo) {
        currentCompletaCheckBox.setSelected(todo.isCompletato());
        currentCompletaCheckBox.addActionListener(e -> todo.setCompletato(currentCompletaCheckBox.isSelected()));
        ui.contentPanel().add(currentCompletaCheckBox, BorderLayout.CENTER);
    }

    private MouseAdapter creaMouseListenerChecklist() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                @SuppressWarnings("unchecked")
                JList<ChecklistItem> sourceList = (JList<ChecklistItem>) e.getSource();
                int index = sourceList.locationToIndex(e.getPoint());
                if (index != -1) {
                    toggleChecklistItemAction(index, sourceList);
                }
            }
        };
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

        if (todo instanceof ToDoCondiviso) {
            DatabaseConnection.todoCondivisoDB.update((ToDoCondiviso) todo);
        } else {
            DatabaseConnection.todoDB.update(todo);
        }

        LOGGER.info("Salvataggio ToDo: " + todo.getTitolo() + " - Completato: " + todo.isCompletato());
    }

    @Override
    public void onCancelAction() {
        LOGGER.info("Modifiche annullate.");
    }

    @Override
    public void onCancellaAction() {
        ToDo todo = SessionManager.getInstance().getCurrentToDo();

        int confirm = JOptionPane.showConfirmDialog(null,
                "Sei sicuro di voler eliminare questo ToDo?",
                "Conferma eliminazione",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (todo instanceof ToDoCondiviso) {
                DatabaseConnection.todoCondivisoDB.delete(todo.getId());
            } else {
                DatabaseConnection.todoDB.delete(todo.getId());
            }
            SessionManager.getInstance().setCurrentToDo(null);
        }
    }

    private static class ChecklistRenderer implements ListCellRenderer<ChecklistItem> {
        private final JPanel panel;
        private final JCheckBox checkBox;

        public ChecklistRenderer() {
            panel = new JPanel(new BorderLayout());
            checkBox = new JCheckBox();
            checkBox.setOpaque(false);
            checkBox.setFocusPainted(false);
            checkBox.setBorderPainted(false);
            panel.add(checkBox, BorderLayout.WEST);
            panel.setOpaque(true);
            panel.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ChecklistItem> list, ChecklistItem value, int index, boolean isSelected, boolean cellHasFocus) {
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
    }
}