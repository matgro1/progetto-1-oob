package org.example.controller.creatodopagecontroller;

import org.example.gui.BachecaMainPage;
import org.example.model.*;
import org.example.controller.ControllerFather;

import javax.swing.*;
import java.time.LocalDate;

public class CreaToDoPageControllerImpl extends ControllerFather implements CreaToDoPageController{

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

            // Usa il costruttore con descrizione e todoId (0 perché il ToDo non è ancora creato)
            model.addElement(new ChecklistItem(descrizione, 0));
        }
    }

    @Override
    public void returnBachecaMainPage() {
        frame.getContentPane().removeAll();
        frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel, JComboBox<Bacheca> comboBacheca, JLabel cLabel) {
        if (condivisoCheckBox.isSelected()){
            condivisoLabel.setVisible(true);
            nomeUtenteCondiviso.setVisible(true);
            comboBacheca.setVisible(true);
            cLabel.setVisible(true);
        } else {
            condivisoLabel.setVisible(false);
            nomeUtenteCondiviso.setVisible(false);
            comboBacheca.setVisible(false);
            cLabel.setVisible(false);
        }
    }

    @Override
    public void updateComboBacheca(JComboBox<Bacheca> comboBacheca, JTextField nomeUtenteCondiviso) {
        comboBacheca.removeAllItems();
        Utente utenteProvvisorio = null;
        for(Utente u: utenti){
            if(u.getLogin().equals(nomeUtenteCondiviso.getText())){
                utenteProvvisorio = u;
                break;
            }
        }
        if(utenteProvvisorio != null){
            // TODO: Devi implementare un metodo per recuperare le bacheche dell'utente dal DB
            // Esempio: List<Bacheca> bacheche = bachecaDAO.findByUtenteId(utenteProvvisorio.getId());
            // for(Bacheca bachecal : bacheche){
            //     comboBacheca.addItem(bachecal);
            // }
        }
    }

    public void creaToDo(JPanel creaToDoPagePanel, JCheckBox condivisoCheckBox, JTextField titoloField, JTextField nomeUtenteCondiviso, JSpinner giorno, JSpinner mese, JSpinner anno, JList<ChecklistItem> checkList, JComboBox<Bacheca> comboBacheca) {
        int g = (int) giorno.getValue();
        int m = (int) mese.getValue();
        int a = (int) anno.getValue();
        LocalDate data = LocalDate.of(a, m, g);

        ToDo nuovoToDo;

        if (!condivisoCheckBox.isSelected()) {
            // Usa il costruttore con titolo, dataScadenza e bachecaId
            nuovoToDo = new ToDo(titoloField.getText(), data, bacheca.getId());
        } else {
            Utente utente = null;
            for (Utente u : utenti) {
                if (u.getLogin().compareTo(nomeUtenteCondiviso.getText()) == 0) {
                    utente = u;
                    break;
                }
            }
            if (utente != null) {
                Bacheca bachecaSelezionata = (Bacheca)comboBacheca.getSelectedItem();
                if (bachecaSelezionata != null) {
                    // Usa il costruttore di ToDoCondiviso con i parametri corretti
                    nuovoToDo = new ToDoCondiviso(
                            titoloField.getText(),
                            data,
                            bachecaSelezionata.getId(),
                            utente.getId(),
                            data
                    );

                    // TODO: Salva il ToDo nel DB prima di aggiungere i ChecklistItem
                    // nuovoToDo = toDoDAO.save(nuovoToDo);

                    DefaultListModel<ChecklistItem> model = (DefaultListModel<ChecklistItem>) checkList.getModel();
                    if (model != null) {
                        for (int i = 0; i < model.getSize(); i++) {
                            ChecklistItem originalItem = model.getElementAt(i);
                            ChecklistItem copiedItem = new ChecklistItem(originalItem.getDescrizione(), nuovoToDo.getId());
                            copiedItem.setStato(originalItem.getStato());
                            // TODO: Salva nel DB con ChecklistItemDAO.save(copiedItem)
                        }
                    }

                    // TODO: Aggiorna le liste tramite DAO invece di manipolare direttamente
                    frame.getContentPane().removeAll();
                    frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
                    frame.revalidate();
                    frame.repaint();
                    return;
                }
                JOptionPane.showMessageDialog(creaToDoPagePanel, "Seleziona una bacheca valida", "Errore di Condivisione", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(creaToDoPagePanel, "Nome utente non valido!", "Errore di Condivisione", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // TODO: Salva il ToDo nel DB
        // nuovoToDo = toDoDAO.save(nuovoToDo);

        DefaultListModel<ChecklistItem> model = (DefaultListModel<ChecklistItem>) checkList.getModel();
        if (model != null) {
            for (int i = 0; i < model.getSize(); i++) {
                ChecklistItem originalItem = model.getElementAt(i);
                ChecklistItem copiedItem = new ChecklistItem(originalItem.getDescrizione(), nuovoToDo.getId());
                copiedItem.setStato(originalItem.getStato());
                // TODO: Salva nel DB con ChecklistItemDAO.save(copiedItem)
            }
        }

        // TODO: Invece di manipolare direttamente la lista, usa il DAO
        // toDoDAO.save(nuovoToDo);

        frame.getContentPane().removeAll();
        frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
        frame.revalidate();
        frame.repaint();
    }
}
