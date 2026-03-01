package org.example.controller.creatodopagecontroller;

import org.example.controller.SessionManager;
import org.example.database.DatabaseConnection;
import org.example.dao.utentedao.UtenteDAO;
import org.example.gui.BachecaMainPage;
import org.example.model.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Crea to do page controller.
 */
public class CreaToDoPageControllerImpl  implements CreaToDoPageController {

    @Override
    public void inizializzazione(CreaToDoPageController.DateSpinners dateSpinners,
                                 CreaToDoPageController.CondivisioneUI condivisioneUI,
                                 JList<ChecklistItem> checkList) {
        SpinnerNumberModel giornoModel = new SpinnerNumberModel(1, 1, 31, 1);
        dateSpinners.giorno().setModel(giornoModel);
        SpinnerNumberModel meseModel = new SpinnerNumberModel(1, 1, 12, 1);
        dateSpinners.mese().setModel(meseModel);
        SpinnerNumberModel annoModel = new SpinnerNumberModel(2025, 2025, 2050, 1);
        dateSpinners.anno().setModel(annoModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(dateSpinners.anno(), "#");
        dateSpinners.anno().setEditor(editor);

        condivisioneUI.utenteLabel().setVisible(false);
        condivisioneUI.nomeUtente().setVisible(false);
        condivisioneUI.combo().setVisible(false);
        condivisioneUI.comboLabel().setVisible(false);

        DefaultListModel<ChecklistItem> model = new DefaultListModel<>();
        checkList.setModel(model);
    }

    @Override
    public void aggiungiChecklistItem(JList<ChecklistItem> checkList) {
        JFrame frame = SessionManager.getInstance().getMainFrame();
        String descrizione = JOptionPane.showInputDialog(frame,
                "Inserisci descrizione dell'attività:",
                "Aggiungi Checklist Item",
                JOptionPane.PLAIN_MESSAGE);

        if (descrizione != null && !descrizione.trim().isEmpty()) {
            DefaultListModel<ChecklistItem> model;
            if (checkList.getModel() instanceof DefaultListModel) {
                model = (DefaultListModel<ChecklistItem>) checkList.getModel();
            } else {
                model = new DefaultListModel<>();
                checkList.setModel(model);
            }
            model.addElement(new ChecklistItem(descrizione, 0));
        }
    }

    @Override
    public void returnBachecaMainPage() {
        JFrame frame = SessionManager.getInstance().getMainFrame();
        frame.getContentPane().removeAll();
        frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel, JComboBox<Bacheca> comboBacheca, JLabel cLabel) {
        boolean selected = condivisoCheckBox.isSelected();
        condivisoLabel.setVisible(selected);
        nomeUtenteCondiviso.setVisible(selected);
        comboBacheca.setVisible(selected);
        cLabel.setVisible(selected);
    }

    @Override
    public void updateComboBacheca(JComboBox<Bacheca> comboBacheca, JTextField nomeUtenteCondiviso) {
        comboBacheca.removeAllItems();

        ArrayList<Utente> allUsers = UtenteDAO.findAll();
        Utente utenteProvvisorio = null;

        for(Utente u: allUsers){
            if(u.getLogin().equals(nomeUtenteCondiviso.getText())){
                utenteProvvisorio = u;
                break;
            }
        }

        if(utenteProvvisorio != null){
            List<Bacheca> bachecheUtente = DatabaseConnection.bachecaDB.findByUtenteId(utenteProvvisorio.getId());
            for (Bacheca b : bachecheUtente) {
                comboBacheca.addItem(b);
            }
        }
    }

    @Override
    public void creaToDo(JPanel creaToDoPagePanel, CreaToDoPageController.ToDoFormData formData, JList<ChecklistItem> checkList) {
        Bacheca currentBacheca = SessionManager.getInstance().getCurrentBacheca();
        ToDo nuovoToDo;

        if (!formData.isCondiviso()) {
            nuovoToDo = salvaToDoNormale(formData, currentBacheca);
        } else {
            nuovoToDo = salvaToDoCondiviso(creaToDoPagePanel, formData, currentBacheca);
        }

        if (nuovoToDo != null) {
            salvaChecklist(checkList, nuovoToDo.getId());
            returnBachecaMainPage();
        }
    }


    private ToDo salvaToDoNormale(CreaToDoPageController.ToDoFormData formData, Bacheca currentBacheca) {
        return DatabaseConnection.todoDB.save(
                new ToDo(
                        formData.titolo(),
                        formData.data(),
                        currentBacheca.getId()
                )
        );
    }

    private ToDo salvaToDoCondiviso(JPanel panel, CreaToDoPageController.ToDoFormData formData, Bacheca currentBacheca) {
        Utente utenteTarget = trovaUtente(formData.utenteTarget());

        if (utenteTarget == null) {
            JOptionPane.showMessageDialog(panel, "Nome utente non valido!", "Errore", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Bacheca bachecaTarget = formData.bachecaTarget();
        if (bachecaTarget == null) {
            JOptionPane.showMessageDialog(panel, "Seleziona una bacheca valida", "Errore", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Utente currentUser = SessionManager.getInstance().getCurrentUser();

        return DatabaseConnection.todoCondivisoDB.save(
                new ToDoCondiviso(
                        formData.titolo(),
                        formData.data(),
                        bachecaTarget.getId(),
                        currentUser.getId(),
                        utenteTarget.getId(),
                        LocalDate.now(),
                        currentBacheca.getId()
                )
        );
    }

    private Utente trovaUtente(String usernameTarget) {
        for (Utente u : UtenteDAO.findAll()) {
            if (u.getLogin().equals(usernameTarget)) {
                return u;
            }
        }
        return null;
    }

    private void salvaChecklist(JList<ChecklistItem> checkList, int idNuovoToDo) {
        if (checkList.getModel() instanceof DefaultListModel<ChecklistItem> model) {
            for (int i = 0; i < model.getSize(); i++) {
                ChecklistItem originalItem = model.getElementAt(i);
                ChecklistItem newItem = new ChecklistItem(originalItem.getDescrizione(), idNuovoToDo);
                newItem.setStato(originalItem.getStato());

                DatabaseConnection.checklistItemDB.save(newItem);
            }
        }
    }
}