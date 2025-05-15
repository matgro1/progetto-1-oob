package org.example.model;

import javax.swing.*;

public class BachecaMainPage {
    private JList<ToDo> ToDoList;
    private JPanel bachecaMainPage;
    private JButton aggiungiButton;
    private JButton indietroButton;

    public BachecaMainPage(JFrame frame, Bacheca bacheca){
        DefaultListModel<ToDo> ToDoListModel = new DefaultListModel<>();
        for (ToDo todo : bacheca.getToDo()){
            ToDoListModel.addElement(todo);
        }
        ToDoList.setModel(ToDoListModel);

    }

    public JPanel getBachecaMainPage(){
        return bachecaMainPage;
    }
}
