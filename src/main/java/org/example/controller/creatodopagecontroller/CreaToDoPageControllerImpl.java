package org.example.controller.creatodopagecontroller;

import org.example.controller.ControllerFather;
import org.example.controller.SessionManager;
import org.example.database.DatabaseConnection;
import org.example.dao.UtenteDAO.UtenteDAO;
import org.example.gui.BachecaMainPage;
import org.example.model.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreaToDoPageControllerImpl extends ControllerFather implements CreaToDoPageController {

    @Override
    public void inizializzazione(JSpinner giorno, JSpinner mese, JSpinner anno, JTextField nomeUtenteCondiviso, JLabel condivisoLabel, JList<ChecklistItem> checkList, JComboBox<Bacheca> comboBacheca, JLabel cLabel){
        SpinnerNumberModel giornoModel = new SpinnerNumberModel(1, 1, 31, 1);
        giorno.setModel(giornoModel);
        SpinnerNumberModel meseModel = new SpinnerNumberModel(1, 1, 12, 1);
        mese.setModel(meseModel);
        SpinnerNumberModel annoModel = new SpinnerNumberModel(2025, 2025, 2050, 1);
        anno.setModel(annoModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(anno, "#");
        anno.setEditor(editor);

        condivisoLabel.setVisible(false);
        nomeUtenteCondiviso.setVisible(false);
        comboBacheca.setVisible(false);
        cLabel.setVisible(false);

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

    public void creaToDo(JPanel creaToDoPagePanel, JCheckBox condivisoCheckBox, JTextField titoloField, JTextField nomeUtenteCondiviso, JSpinner giorno, JSpinner mese, JSpinner anno, JList<ChecklistItem> checkList, JComboBox<Bacheca> comboBacheca) {
        int g = (int) giorno.getValue();
        int m = (int) mese.getValue();
        int a = (int) anno.getValue();
        LocalDate data = LocalDate.of(a, m, g);

        ToDo nuovoToDo = null;
        Bacheca currentBacheca = SessionManager.getInstance().getCurrentBacheca();

        if (!condivisoCheckBox.isSelected()) {
            nuovoToDo = DatabaseConnection.todoDB.save(
                    new ToDo(
                            titoloField.getText(),
                            data,
                            currentBacheca.getId()
                    )
            );
        } else {
            Utente currentUser = SessionManager.getInstance().getCurrentUser();
            ArrayList<Utente> allUsers = UtenteDAO.findAll();
            Utente utenteTarget = null;

            for (Utente u : allUsers) {
                if (u.getLogin().equals(nomeUtenteCondiviso.getText())) {
                    utenteTarget = u;
                    break;
                }
            }

            if (utenteTarget == null) {
                JOptionPane.showMessageDialog(creaToDoPagePanel, "Nome utente non valido!", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Bacheca bachecaTarget = (Bacheca) comboBacheca.getSelectedItem();
            if (bachecaTarget == null) {
                JOptionPane.showMessageDialog(creaToDoPagePanel, "Seleziona una bacheca valida", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            nuovoToDo = DatabaseConnection.todoCondivisoDB.save(
                    new ToDoCondiviso(
                            titoloField.getText(),
                            data,
                            bachecaTarget.getId(),
                            currentUser.getId(),
                            LocalDate.now(),
                            currentBacheca.getId()
                    )
            );
        }

        if (nuovoToDo != null) {
            DefaultListModel<ChecklistItem> model = (DefaultListModel<ChecklistItem>) checkList.getModel();
            if (model != null) {
                for (int i = 0; i < model.getSize(); i++) {
                    ChecklistItem originalItem = model.getElementAt(i);
                    ChecklistItem newItem = new ChecklistItem(originalItem.getDescrizione(), nuovoToDo.getId());
                    newItem.setStato(originalItem.getStato());
                    DatabaseConnection.checklistItemDB.save(newItem);
                }
            }

            returnBachecaMainPage();
        }
    }
}