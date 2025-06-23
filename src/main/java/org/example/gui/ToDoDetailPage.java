// src/main/java/org/example/gui/ToDoDetailPage.java
package org.example.gui;

import org.example.controller.tododetailpagecontroller.ToDoDetailPageController;
import org.example.controller.tododetailpagecontroller.ToDoDetailPageControllerImpl;
import org.example.model.ChecklistItem; // Necessario per il tipo della JList

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToDoDetailPage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel nomeToDoLabel;
    private JList<ChecklistItem> list1;
    private ToDoDetailPageController controller;

    public ToDoDetailPage() {
        this.controller = new ToDoDetailPageControllerImpl(); // Controller creato senza passare il ToDo

        // Inizializzazione Componenti GUI (da adattare al tuo designer o inizializzazione manuale)
        contentPane = new JPanel(new BorderLayout(5, 5));
        nomeToDoLabel = new JLabel();
        nomeToDoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nomeToDoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(nomeToDoLabel, BorderLayout.NORTH);

        list1 = new JList<>();
        JScrollPane scrollPane = new JScrollPane(list1);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Annulla");
        buttonPanel.add(buttonOK);
        buttonPanel.add(buttonCancel);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // La View passa i suoi componenti al Controller per l'inizializzazione
        controller.initializeGui(list1, nomeToDoLabel);

        // Gestione Eventi
        buttonOK.addActionListener(e -> {
            controller.onOkAction();
            dispose();
        });
        buttonCancel.addActionListener(e -> {
            controller.onCancelAction();
            dispose();
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.onCancelAction();
                dispose();
            }
        });
        contentPane.registerKeyboardAction(e -> {
            controller.onCancelAction();
            dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setLocationRelativeTo(null);
    }

    public JLabel getNomeToDoLabel() { return nomeToDoLabel; }
    public JList<ChecklistItem> getChecklistJList() { return list1; }
    public void closeDialog() { dispose(); }
}