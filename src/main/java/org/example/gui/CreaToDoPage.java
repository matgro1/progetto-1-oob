package org.example.gui;
import org.example.controller.creatodopagecontroller.CreaToDoPageController;
import org.example.controller.creatodopagecontroller.CreaToDoPageControllerImpl;
import org.example.model.ChecklistItem;

import javax.swing.*;
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

    CreaToDoPageController controller= new CreaToDoPageControllerImpl();
    public CreaToDoPage() {
        controller.inizializzazione(giorno,mese,anno,nomeUtenteCondiviso,condivisoLabel,checkList);

        annullaButton.addActionListener(e-> controller.returnBachecaMainPage());
        condivisoCheckBox.addActionListener(e->controller.updateScreen(condivisoCheckBox,nomeUtenteCondiviso,condivisoLabel));
        creaButton.addActionListener(e-> controller.creaToDo(creaToDoPagePanel,condivisoCheckBox,titoloField,nomeUtenteCondiviso,giorno,mese,anno,checkList));
        aggiungiCheckButton.addActionListener(e -> controller.aggiungiChecklistItem(checkList));
    }

    public JPanel getCreaToDoPage() {
        return creaToDoPagePanel;
    }
}
