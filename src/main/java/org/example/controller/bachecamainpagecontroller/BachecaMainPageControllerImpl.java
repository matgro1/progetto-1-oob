package org.example.controller.bachecamainpagecontroller;

import org.example.gui.*;
import org.example.model.*;
import org.example.controller.ControllerFather;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.time.LocalDate;
import java.util.List;

public class BachecaMainPageControllerImpl extends ControllerFather implements BachecaMainPageController{
    private JList<ToDo> completeList;
    private JList<ToDo> noCompleteList;
    private JList<ToDo> expiredList;
    @Override
    public void setJLists(JList<ToDo> complete, JList<ToDo> noComplete, JList<ToDo> expired) {
        this.completeList = complete;
        this.noCompleteList = noComplete;
        this.expiredList = expired;
        defaultListModelCreator(completeList, noCompleteList, expiredList); // Popola all'inizio
    }
    @Override
    public void refreshToDoLists() {
        defaultListModelCreator(completeList, noCompleteList, expiredList);
    }

    public void returnToMainPage() {
        frame.getContentPane().removeAll();
        frame.setContentPane(new MainPage().getMainPage());
        frame.revalidate();
        frame.repaint();
    }
    public void setDescrizione(JTextArea descrizione) {
        descrizione.setText(bacheca.getDescrizione());
        descrizione.setEditable(false);
    }
    @Override
    public void setTitolo(JTextField titolo) {
        titolo.setText(bacheca.getNome());
        titolo.setEditable(false);
    }
    @Override
    public void defaultListModelCreator(JList<ToDo> complete, JList<ToDo> noComplete,JList<ToDo> expired) {
        List<ToDo> todos= bacheca.getToDo();
        DefaultListModel<ToDo> toDoListModelComplete = new DefaultListModel<>();
        DefaultListModel<ToDo> toDoListModelNoComplete = new DefaultListModel<>();
        DefaultListModel<ToDo> toDoListModelExpired = new DefaultListModel<>();
        for(ToDo todo: todos){
            if(!todo.getChecklist().isEmpty()){
                todo.verificaChecklist();
            }

            if(todo.getStato()){
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
        ModificaBachecaPage dialog = new ModificaBachecaPage(bacheca);
        dialog.setVisible(true);

        if (dialog.isModificaConfermata()) {
            frame.setTitle("Bacheca: " + bacheca.getNome());
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
        frame.getContentPane().removeAll();
        frame.setContentPane(new CreaToDoPage().getCreaToDoPage());
        frame.revalidate();
        frame.repaint();

    }

    @Override
    public void goToToDoDetailsPage(ListSelectionEvent e, ToDo toDoSelezionato) {
        if (!e.getValueIsAdjusting() && toDoSelezionato != null) {
            setToDo(toDoSelezionato);
            ToDoDetailPage dialog= new ToDoDetailPage();
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }
    private static void setToDo(ToDo td){
        todo=td;
    }
}