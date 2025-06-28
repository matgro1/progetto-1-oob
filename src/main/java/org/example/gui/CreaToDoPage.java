package org.example.gui;
import org.example.controller.creatodopagecontroller.CreaToDoPageController;
import org.example.controller.creatodopagecontroller.CreaToDoPageControllerImpl;
import org.example.model.Bacheca;
import org.example.model.ChecklistItem;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CreaToDoPage{
    private JPanel creaToDoPagePanel;
    private JTextField titoloField;
    private JSpinner giorno;
    private JSpinner mese;
    private JSpinner anno;
    private JButton annullaButton;
    private JButton creaButton;
    private JCheckBox condivisoCheckBox;
    private JTextField nomeUtenteCondiviso;
    private JLabel condivisoLabel;
    private JList<ChecklistItem> checkList;
    private JButton aggiungiCheckButton;
    private JComboBox<Bacheca> comboBox1;
    private JLabel cLabel;

    CreaToDoPageController controller= new CreaToDoPageControllerImpl();
    public CreaToDoPage() {
        controller.inizializzazione(giorno,mese,anno,nomeUtenteCondiviso,condivisoLabel,checkList,comboBox1,cLabel);

        annullaButton.addActionListener(e-> controller.returnBachecaMainPage());
        condivisoCheckBox.addActionListener(e->controller.updateScreen(condivisoCheckBox,nomeUtenteCondiviso,condivisoLabel,comboBox1,cLabel));
        creaButton.addActionListener(e-> controller.creaToDo(creaToDoPagePanel,condivisoCheckBox,titoloField,nomeUtenteCondiviso,giorno,mese,anno,checkList, comboBox1));
        aggiungiCheckButton.addActionListener(e -> controller.aggiungiChecklistItem(checkList));
        nomeUtenteCondiviso.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controller.updateComboBacheca(comboBox1,nomeUtenteCondiviso);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.updateComboBacheca(comboBox1,nomeUtenteCondiviso);
            }
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
            }
        });
    }

    public JPanel getCreaToDoPage() {
        return creaToDoPagePanel;
    }
}
