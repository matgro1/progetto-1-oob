package org.example.gui;

import org.example.controller.modificauserpagecontroller.ModificaUserPageController;
import org.example.controller.modificauserpagecontroller.ModificaUserPageControllerImpl;

import javax.swing.*;
import java.awt.event.*;

public class ModificaUserPage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField username;
    private JTextField password;
    private  static ModificaUserPageController controller = new ModificaUserPageControllerImpl();
    public ModificaUserPage() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        controller.inizializazione(username,password);

        buttonOK.addActionListener(e->onOK());

        buttonCancel.addActionListener(e->onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });//

        contentPane.registerKeyboardAction(e->onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        controller.modificaUtente(username,password);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
