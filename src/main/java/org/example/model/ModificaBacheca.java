//odio i dialog
package org.example.model;
import org.example.model.controller.modificabachecacontroller.ModificaBachecaController;
import org.example.model.controller.modificabachecacontroller.ModificaBachecaControllerImpl;
import javax.swing.*;
public class ModificaBacheca extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField titoloField;
    private JTextField descrizioneField;
    private String titoloModificato;
    private String descrizioneModificata;
    private boolean modificaConfermata = false;
    private ModificaBachecaController controller = new ModificaBachecaControllerImpl();

    public ModificaBacheca(Bacheca bacheca) {
        titoloField.setText(bacheca.getNome());
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
    public boolean isModificaConfermata() {
        return modificaConfermata;
    }
}