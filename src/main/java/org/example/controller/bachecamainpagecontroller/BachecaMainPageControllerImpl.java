package org.example.controller.bachecamainpagecontroller;

import org.example.controller.SessionManager;
import org.example.database.DatabaseConnection;
import org.example.gui.*;
import org.example.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Bacheca main page controller.
 */
public class BachecaMainPageControllerImpl  implements BachecaMainPageController {
    private JList<ToDo> completeList;
    private JList<ToDo> noCompleteList;
    private JList<ToDo> expiredList;

    @Override
    public void setJLists(JList<ToDo> complete, JList<ToDo> noComplete, JList<ToDo> expired) {
        this.completeList = complete;
        this.noCompleteList = noComplete;
        this.expiredList = expired;
        defaultListModelCreator(completeList, noCompleteList, expiredList);
    }

    @Override
    public void refreshToDoLists() {
        defaultListModelCreator(completeList, noCompleteList, expiredList);
    }

    public void returnToMainPage() {
        SessionManager.getInstance().setCurrentBacheca(null); // Pulisce selezione
        JFrame frame = SessionManager.getInstance().getMainFrame();
        frame.getContentPane().removeAll();
        frame.setContentPane(new MainPage().getMainPage());
        frame.revalidate();
        frame.repaint();
    }

    public void setDescrizione(JTextArea descrizione) {
        Bacheca b = SessionManager.getInstance().getCurrentBacheca();
        if (b != null) {
            descrizione.setText(b.getDescrizione());
        }
        descrizione.setEditable(false);
    }

    @Override
    public void setTitolo(JTextField titolo) {
        Bacheca b = SessionManager.getInstance().getCurrentBacheca();
        if (b != null) {
            titolo.setText(b.getTitolo());
        }
        titolo.setEditable(false);
    }

    @Override
    public void defaultListModelCreator(JList<ToDo> complete, JList<ToDo> noComplete, JList<ToDo> expired) {
        Bacheca currentBacheca = SessionManager.getInstance().getCurrentBacheca();
        int bachecaId = currentBacheca.getId();

        ArrayList<ToDo> listaTotale = DatabaseConnection.todoDB.findByBachecaId(bachecaId);
        ArrayList<ToDoCondiviso> listaCondivisi = recuperaCondivisi(bachecaId);

        unisciListe(listaTotale, listaCondivisi);

        ordinaLista(listaTotale);

        DefaultListModel<ToDo> modelComplete = new DefaultListModel<>();
        DefaultListModel<ToDo> modelNoComplete = new DefaultListModel<>();
        DefaultListModel<ToDo> modelExpired = new DefaultListModel<>();

        for (ToDo todo : listaTotale) {
            smistaInModello(todo, modelComplete, modelNoComplete, modelExpired);
        }

        complete.setModel(modelComplete);
        noComplete.setModel(modelNoComplete);
        expired.setModel(modelExpired);
    }

    private ArrayList<ToDoCondiviso> recuperaCondivisi(int bachecaId) {
        ArrayList<ToDoCondiviso> condivisi = new ArrayList<>();
        condivisi.addAll(DatabaseConnection.todoCondivisoDB.findByBachecaID(bachecaId));
        condivisi.addAll(DatabaseConnection.todoCondivisoDB.findByBachecaCreatoreId(bachecaId));
        return condivisi;
    }

    private void unisciListe(ArrayList<ToDo> principale, ArrayList<ToDoCondiviso> condivisi) {
        for (ToDoCondiviso cond : condivisi) {
            int indiceEsistente = trovaIndice(principale, cond.getId());

            if (indiceEsistente != -1) {
                principale.set(indiceEsistente, cond);
            } else {
                principale.add(cond);
            }
        }
    }

    private int trovaIndice(ArrayList<ToDo> lista, int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private void ordinaLista(ArrayList<ToDo> lista) {
        lista.sort((t1, t2) -> {
            if (t1.getDataScadenza() == null) return 1;
            if (t2.getDataScadenza() == null) return -1;
            return t1.getDataScadenza().compareTo(t2.getDataScadenza());
        });
    }

    private void smistaInModello(ToDo t, DefaultListModel<ToDo> c, DefaultListModel<ToDo> nc, DefaultListModel<ToDo> e) {
        if (t.isCompletato()) {
            c.addElement(t);
        } else if (isScaduto(t)) {
            e.addElement(t);
        } else {
            nc.addElement(t);
        }
    }

    private boolean isScaduto(ToDo t) {
        return t.getDataScadenza() != null && t.getDataScadenza().isBefore(LocalDate.now());
    }


    @Override
    public void modificaBacheca() {
        Bacheca bacheca = SessionManager.getInstance().getCurrentBacheca();
        ModificaBachecaPage dialog = new ModificaBachecaPage(bacheca);
        dialog.setVisible(true);

        if (dialog.isModificaConfermata()) {
            JFrame frame = SessionManager.getInstance().getMainFrame();
            frame.setTitle("Bacheca: " + bacheca.getTitolo());
        }
    }

    @Override
    public void updateScreen(JTextField titolo, JTextArea descrizione) {
        this.modificaBacheca();
        this.setDescrizione(descrizione);
        this.setTitolo(titolo);
    }

    @Override
    public void goToCreaToDoPage() {
        JFrame frame = SessionManager.getInstance().getMainFrame();
        frame.getContentPane().removeAll();
        frame.setContentPane(new CreaToDoPage().getCreaToDoPage());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void goToToDoDetailsPage(ListSelectionEvent e, ToDo toDoSelezionato) {
        if (!e.getValueIsAdjusting() && toDoSelezionato != null) {
            SessionManager.getInstance().setCurrentToDo(toDoSelezionato);

            ToDoDetailPage dialog= new ToDoDetailPage();
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }
}