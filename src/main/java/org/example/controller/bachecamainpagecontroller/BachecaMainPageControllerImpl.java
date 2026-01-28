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
import java.util.List;

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
        List<ToDo> todosLocali = new ArrayList<>();

        if(currentBacheca != null){
            todosLocali.addAll(DatabaseConnection.todoDB.findByBachecaId(currentBacheca.getId()));
            todosLocali.addAll(DatabaseConnection.todoCondivisoDB.findByBachecaID(currentBacheca.getId()));
        }

        DefaultListModel<ToDo> toDoListModelComplete = new DefaultListModel<>();
        DefaultListModel<ToDo> toDoListModelNoComplete = new DefaultListModel<>();
        DefaultListModel<ToDo> toDoListModelExpired = new DefaultListModel<>();

        for(ToDo todo: todosLocali){
            if(todo.isCompletato()){
                toDoListModelComplete.addElement(todo);
            }
            else if (todo.getDataScadenza().isBefore(LocalDate.now())){
                toDoListModelExpired.addElement(todo);
            }
            else {
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