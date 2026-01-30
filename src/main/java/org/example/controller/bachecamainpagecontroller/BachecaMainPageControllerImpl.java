package org.example.controller.bachecamainpagecontroller;

import org.example.controller.ControllerFather;
import org.example.controller.SessionManager;
import org.example.database.DatabaseConnection;
import org.example.gui.*;
import org.example.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The type Bacheca main page controller.
 */
public class BachecaMainPageControllerImpl extends ControllerFather implements BachecaMainPageController {
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

        ArrayList<ToDo> listaPrincipale = DatabaseConnection.todoDB.findByBachecaId(currentBacheca.getId());

        ArrayList<ToDoCondiviso> listaCondivisi = new ArrayList<>();
        listaCondivisi.addAll(DatabaseConnection.todoCondivisoDB.findByBachecaID(currentBacheca.getId()));
        listaCondivisi.addAll(DatabaseConnection.todoCondivisoDB.findByBachecaCreatoreId(currentBacheca.getId()));

        for (ToDoCondiviso condiviso : listaCondivisi) {
            boolean trovato = false;

            for (int i = 0; i < listaPrincipale.size(); i++) {
                if (listaPrincipale.get(i).getId() == condiviso.getId()) {
                    listaPrincipale.set(i, condiviso);
                    trovato = true;
                    break;
                }
            }

            if (!trovato) {
                listaPrincipale.add(condiviso);
            }
        }

        DefaultListModel<ToDo> toDoListModelComplete = new DefaultListModel<>();
        DefaultListModel<ToDo> toDoListModelNoComplete = new DefaultListModel<>();
        DefaultListModel<ToDo> toDoListModelExpired = new DefaultListModel<>();

        listaPrincipale.sort((t1, t2) -> {
            if (t1.getDataScadenza() == null) return 1;
            if (t2.getDataScadenza() == null) return -1;
            return t1.getDataScadenza().compareTo(t2.getDataScadenza());
        });

        for (ToDo todo : listaPrincipale) {
            if (todo.isCompletato()) {
                toDoListModelComplete.addElement(todo);
            } else if (todo.getDataScadenza() != null && todo.getDataScadenza().isBefore(LocalDate.now())) {
                toDoListModelExpired.addElement(todo);
            } else {
                toDoListModelNoComplete.addElement(todo);
            }
        }

        complete.setModel(toDoListModelComplete);
        noComplete.setModel(toDoListModelNoComplete);
        expired.setModel(toDoListModelExpired);
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