package org.example.controller.creatodopagecontroller;

import org.example.gui.BachecaMainPage;
import org.example.model.*;
import org.example.controller.ControllerFather;

import javax.swing.*;
import java.time.LocalDate;

public class CreaToDoPageControllerImpl extends ControllerFather implements CreaToDoPageController{

    @Override
    public  void inizializzazione(JSpinner giorno, JSpinner mese, JSpinner anno, JTextField nomeUtenteCondiviso, JLabel condivisoLabel, JList<ChecklistItem> checkList, JComboBox<Bacheca> comboBacheca){
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
            // Ottieni il modello della lista
            DefaultListModel<ChecklistItem> model;

            if (checkList.getModel() instanceof DefaultListModel) {
                // Se esiste già un modello, usalo
                model = (DefaultListModel<ChecklistItem>) checkList.getModel();
            } else {
                // Altrimenti crea un nuovo modello
                model = new DefaultListModel<>();
                checkList.setModel(model);
            }

            // Aggiungi il nuovo elemento al modello
            model.addElement(new ChecklistItem(descrizione));
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
    public void updateScreen(JCheckBox condivisoCheckBox, JTextField nomeUtenteCondiviso, JLabel condivisoLabel,JComboBox<Bacheca> comboBacheca) {
       if (condivisoCheckBox.isSelected()){
           condivisoLabel.setVisible(true);
           nomeUtenteCondiviso.setVisible(true);
           comboBacheca.setVisible(true);
       }
       else{
           condivisoLabel.setVisible(false);
           nomeUtenteCondiviso.setVisible(false);
           comboBacheca.setVisible(false);
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
            for(Bacheca bachecal:utenteProvvisorio.getBacheche()){
                comboBacheca.addItem(bachecal);
            }
        }
    }

    public void creaToDo(JPanel creaToDoPagePanel, JCheckBox condivisoCheckBox, JTextField titoloField, JTextField nomeUtenteCondiviso, JSpinner giorno, JSpinner mese, JSpinner anno, JList<ChecklistItem> checkList, JComboBox<Bacheca> comboBacheca) {
        int g = (int) giorno.getValue();
        int m = (int) mese.getValue();
        int a = (int) anno.getValue();
        LocalDate data = LocalDate.of(a, m, g);

        ToDo nuovoToDo = null;

        if (!condivisoCheckBox.isSelected()) {
            nuovoToDo = new ToDo(titoloField.getText(), data);
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
                for(Bacheca bachecal:utente.getBacheche()){
                    assert bachecaSelezionata != null;
                    if (bachecal.getNome().equals(bachecaSelezionata.getNome())) {
                        nuovoToDo=new ToDoCondiviso(titoloField.getText(),data,utente,data);
                        DefaultListModel<ChecklistItem> model = (DefaultListModel<ChecklistItem>) checkList.getModel();
                        if (model != null) {
                            for (int i = 0; i < model.getSize(); i++) {
                                ChecklistItem originalItem = model.getElementAt(i);
                                ChecklistItem copiedItem = new ChecklistItem(originalItem.getDescrizione());
                                copiedItem.setStato(originalItem.getStato());
                                nuovoToDo.aggiungiChecklistItem(copiedItem);

                            }
                        }
                        bachecal.getToDo().add(nuovoToDo);
                        bacheca.getToDo().add(nuovoToDo);
                        frame.getContentPane().removeAll();
                        frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
                        frame.revalidate();
                        frame.repaint();
                        return;
                    }
                }

                JOptionPane.showMessageDialog(creaToDoPagePanel, "Boh", "Errore di Condivisione", JOptionPane.ERROR_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(creaToDoPagePanel, "Nome utente non valido!", "Errore di Condivisione", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        DefaultListModel<ChecklistItem> model = (DefaultListModel<ChecklistItem>) checkList.getModel();
        if (model != null) {
            for (int i = 0; i < model.getSize(); i++) {
                ChecklistItem originalItem = model.getElementAt(i);
                ChecklistItem copiedItem = new ChecklistItem(originalItem.getDescrizione());
                copiedItem.setStato(originalItem.getStato());
                nuovoToDo.aggiungiChecklistItem(copiedItem);
            }
        }

        bacheca.getToDo().add(nuovoToDo);

        // Torna alla pagina principale
        frame.getContentPane().removeAll();
        frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
        frame.revalidate();
        frame.repaint();
    }
}
