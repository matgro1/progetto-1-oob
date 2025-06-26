package org.example.gui;

import lombok.Getter;
import org.example.controller.tododetailpagecontroller.ToDoDetailPageController;
import org.example.controller.tododetailpagecontroller.ToDoDetailPageControllerImpl;
import org.example.model.ChecklistItem; // Still needed for JList type

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToDoDetailPage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    @Getter
    private JLabel nomeToDoLabel;
    private JList<ChecklistItem> list1; // Declared, but added conditionally
    private JCheckBox completaCheckBox; // Declared, but added conditionally
    private JLabel dataScadenza;
    private JLabel ultimaModifica;
    private JLabel utenteCodiviso;
    private ToDoDetailPageController controller;

    public ToDoDetailPage() {
        this.controller = new ToDoDetailPageControllerImpl();

        contentPane = new JPanel(new BorderLayout(5, 5));
        nomeToDoLabel = new JLabel();
        nomeToDoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nomeToDoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(nomeToDoLabel, BorderLayout.NORTH);

        list1 = new JList<>();
        completaCheckBox = new JCheckBox("Completa ToDo");
        completaCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        completaCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Annulla");
        buttonPanel.add(buttonOK);
        buttonPanel.add(buttonCancel);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        controller.initializeGui(list1, completaCheckBox, nomeToDoLabel, contentPane,dataScadenza,ultimaModifica, utenteCodiviso);


        buttonOK.addActionListener(e -> {
            controller.onOkAction(); // Controller will handle the state based on which component was active
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

    public JList<ChecklistItem> getChecklistJList() { return list1; }
    public JCheckBox getCompletaCheckBox() { return completaCheckBox; }

    public void closeDialog() { dispose(); }
}