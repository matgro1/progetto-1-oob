package org.example.controller.tododetailpagecontroller;

import org.example.controller.ControllerFather;
import org.example.model.ChecklistItem;
import org.example.model.ToDo;
import org.example.model.ToDoCondiviso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controller per la pagina di dettaglio di un ToDo.
 * Gestisce la visualizzazione e l'interazione con checklist o un semplice stato di completamento.
 */
public class ToDoDetailPageControllerImpl extends ControllerFather implements ToDoDetailPageController {

    private DefaultListModel<ChecklistItem> listModel;
    private JList<ChecklistItem> currentChecklistJList;
    private JCheckBox currentCompletaCheckBox;
    private JPanel mainContentPanel;

    /**
     * Costruttore del controller. Inizializza il modello della lista e verifica che un ToDo sia stato impostato.
     *
     * @throws IllegalStateException se il ToDo non è stato impostato prima della creazione del controller.
     */
    public ToDoDetailPageControllerImpl() {
        if (ControllerFather.todo == null) {
            throw new IllegalStateException("ToDo non impostato in ControllerFather.todo prima di creare ToDoDetailPageControllerImpl.");
        }
        this.listModel = new DefaultListModel<>();
    }


    @Override
    public void initializeGui(JList<ChecklistItem> checklistJList, JCheckBox completaCheckBox, JLabel nomeToDoLabel, JPanel contentPanel,JLabel dataScadenza,JLabel ultimaModifica,JLabel utenteCodiviso){
        this.currentChecklistJList = checklistJList;
        this.currentCompletaCheckBox = completaCheckBox;
        this.mainContentPanel = contentPanel;
        ultimaModifica.setVisible(false);
        utenteCodiviso.setVisible(false);

        dataScadenza.setText(todo.getDataScadenza().toString());
        System.out.println("Classe di todo: " + todo.getClass().getName());

        if(todo instanceof ToDoCondiviso tdc){
            ultimaModifica.setVisible(true);
            utenteCodiviso.setVisible(true);
            ultimaModifica.setText("ultima modifica: " + tdc.getUltimoModificatore());
            utenteCodiviso.setText("condiviso con: "+ tdc.getUltimoModificatore());
        }
        nomeToDoLabel.setText(ControllerFather.todo.getTitolo());

        List<ChecklistItem> checklist = ControllerFather.todo.getChecklist();

        // Rimuove qualsiasi componente precedentemente aggiunto per un rendering pulito.
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

            // Imposta un renderer personalizzato per visualizzare ogni elemento della checklist come una JCheckBox.
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

            // Aggiunge un listener per gestire il click sugli elementi della checklist.
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

            // Aggiunge la JList al centro del pannello.
            mainContentPanel.add(new JScrollPane(currentChecklistJList), BorderLayout.CENTER);

        } else {
            // Caso 2: Il ToDo non ha una checklist, visualizza solo una JCheckBox per il suo stato.
            currentCompletaCheckBox.setSelected(ControllerFather.todo.getStato());
            // Aggiunge un listener per aggiornare lo stato del ToDo quando la checkbox viene cliccata.
            currentCompletaCheckBox.addActionListener(e -> {
                ControllerFather.todo.setStato(currentCompletaCheckBox.isSelected());
                System.out.println("Stato ToDo aggiornato via checkbox: " + ControllerFather.todo.getStato());
            });
            // Aggiunge la JCheckBox al centro del pannello.
            mainContentPanel.add(currentCompletaCheckBox, BorderLayout.CENTER);
        }

        // Rende visibili i nuovi componenti.
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    /**
     * Inverte lo stato di completamento di un elemento della checklist e aggiorna lo stato generale del ToDo.
     *
     * @param index L'indice dell'elemento della checklist da modificare.
     * @param checklistJList La JList contenente la checklist.
     */
    @Override
    public void toggleChecklistItemAction(int index, JList<ChecklistItem> checklistJList) {
        if (ControllerFather.todo.getChecklist() != null && index >= 0 && index < ControllerFather.todo.getChecklist().size()) {
            ChecklistItem item = ControllerFather.todo.getChecklist().get(index);
            item.setStato(!item.isCompletato()); // Inverte lo stato.

            // Aggiorna il modello per riflettere il cambiamento visivamente.
            listModel.setElementAt(item, index);

            // Controlla se tutti gli elementi sono completati e aggiorna lo stato generale del ToDo.
            ControllerFather.todo.verificaChecklist();
            System.out.println("ChecklistItem '" + item.getDescrizione() + "' completato: " + item.isCompletato());
            System.out.println("Stato ToDo dopo verifica checklist: " + ControllerFather.todo.getStato());
        }
    }

    /**
     * Gestisce l'azione quando il pulsante "OK" viene premuto.
     * Applica le modifiche allo stato del ToDo in base a ciò che è stato visualizzato (checklist o checkbox singola).
     */
    @Override
    public void onOkAction() {
        if (ControllerFather.todo.getChecklist() != null && !ControllerFather.todo.getChecklist().isEmpty()) {
            // Se c'era una checklist, gli stati degli elementi sono già aggiornati da toggleChecklistItemAction.
            System.out.println("Controller: Azione OK eseguita. ToDo con checklist. Stato finale ToDo: " + ControllerFather.todo.getTitolo() + " -> " + ControllerFather.todo.getStato());
        } else {
            // Se c'era una JCheckBox, lo stato del ToDo è già stato aggiornato dall'ActionListener.
            System.out.println("Controller: Azione OK eseguita. ToDo senza checklist. Stato finale ToDo: " + ControllerFather.todo.getTitolo() + " -> " + ControllerFather.todo.getStato());
        }
    }

    /**
     * Gestisce l'azione quando il pulsante "Annulla" viene premuto.
     * Attualmente non esegue alcuna azione di ripristino.
     */
    @Override
    public void onCancelAction() {
        System.out.println("Controller: Azione Annulla eseguita. Nessuna modifica salvata.");
    }
}