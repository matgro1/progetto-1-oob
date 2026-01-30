//odio i dialog
package org.example.gui;
import lombok.Getter;
import org.example.controller.modificabachecacontroller.ModificaBachecaController;
import org.example.controller.modificabachecacontroller.ModificaBachecaControllerImpl;
import org.example.model.Bacheca;

import javax.swing.*;

/**
 * The type Modifica bacheca page.
 */
public class ModificaBachecaPage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField titoloField;
    private JTextField descrizioneField;
    private JButton cancella;
    private String titoloModificato;
    private String descrizioneModificata;
    @Getter
    private boolean modificaConfermata = false;
    private static final ModificaBachecaController controller = new ModificaBachecaControllerImpl();

    /**
     * Instantiates a new Modifica bacheca page.
     *
     * @param bacheca the bacheca
     */
    public ModificaBachecaPage(Bacheca bacheca) {
        titoloField.setText(bacheca.getTitolo());
        descrizioneField.setText(bacheca.getDescrizione());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> {
            titoloModificato = titoloField.getText();
            descrizioneModificata = descrizioneField.getText();
            onOK();
        });
        buttonCancel.addActionListener(e -> onCancel());
        pack();
        setLocationRelativeTo(null);
    }
    private void onOK() {
        controller.ok(titoloModificato,descrizioneModificata);
        modificaConfermata=true;
        dispose();
    }
    private void onCancel() {
        dispose();
    }
}