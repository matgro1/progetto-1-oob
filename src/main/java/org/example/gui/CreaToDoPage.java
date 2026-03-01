package org.example.gui;

import org.example.controller.creatodopagecontroller.CreaToDoPageController;
import org.example.controller.creatodopagecontroller.CreaToDoPageControllerImpl;
import org.example.model.Bacheca;
import org.example.model.ChecklistItem;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.time.LocalDate;

/**
 * The type Crea to do page.
 */
public class CreaToDoPage {
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

    /**
     * The Controller.
     */
    CreaToDoPageController controller = new CreaToDoPageControllerImpl();

    /**
     * Instantiates a new Crea to do page.
     */
    public CreaToDoPage() {
        CreaToDoPageController.DateSpinners dateSpinners = new CreaToDoPageController.DateSpinners(giorno, mese, anno);
        CreaToDoPageController.CondivisioneUI condivisioneUI = new CreaToDoPageController.CondivisioneUI(nomeUtenteCondiviso, condivisoLabel, comboBox1, cLabel);

        controller.inizializzazione(dateSpinners, condivisioneUI, checkList);

        annullaButton.addActionListener(e -> controller.returnBachecaMainPage());
        condivisoCheckBox.addActionListener(e -> controller.updateScreen(condivisoCheckBox, nomeUtenteCondiviso, condivisoLabel, comboBox1, cLabel));

        creaButton.addActionListener(e -> {
            int g = (int) giorno.getValue();
            int m = (int) mese.getValue();
            int a = (int) anno.getValue();
            LocalDate dataInserita = LocalDate.of(a, m, g);

            CreaToDoPageController.ToDoFormData formData = new CreaToDoPageController.ToDoFormData(
                    titoloField.getText(),
                    dataInserita,
                    condivisoCheckBox.isSelected(),
                    nomeUtenteCondiviso.getText(),
                    (Bacheca) comboBox1.getSelectedItem()
            );

            controller.creaToDo(creaToDoPagePanel, formData, checkList);
        });

        aggiungiCheckButton.addActionListener(e -> controller.aggiungiChecklistItem(checkList));

        nomeUtenteCondiviso.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controller.updateComboBacheca(comboBox1, nomeUtenteCondiviso);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.updateComboBacheca(comboBox1, nomeUtenteCondiviso);
            }
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                // Metodo lasciato vuoto intenzionalmente:
                // changedUpdate viene attivato solo per modifiche di stile (es. JTextPane o StyledDocument).
                // Per un normale JTextField (PlainDocument) ci interessano solo insertUpdate e removeUpdate.
            }
        });
    }

    /**
     * Gets crea to do page.
     *
     * @return the crea to do page
     */
    public JPanel getCreaToDoPage() {
        return creaToDoPagePanel;
    }
}