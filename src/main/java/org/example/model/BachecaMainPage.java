package org.example.model;

import javax.swing.*;

public class BachecaMainPage {
    private JList<ToDo> toDoList;
    private JPanel bachecaMainPagePanel;
    private JButton aggiungiButton;
    private JButton indietroButton;

    public BachecaMainPage(JFrame frame, Bacheca bacheca, Utente utente){
        DefaultListModel<ToDo> toDoListModel = new DefaultListModel<>();
        for (ToDo todo : bacheca.getToDo()){
            toDoListModel.addElement(todo);
        }
        toDoList.setModel(toDoListModel);
        indietroButton.addActionListener(e-> {
            frame.getContentPane().removeAll();
            frame.setContentPane(new MainPage(frame,utente).getMainPage());
            frame.revalidate();
            frame.repaint();
        });
        aggiungiButton.addActionListener(e-> {
            frame.getContentPane().removeAll();
            frame.setContentPane(new CreaToDoPage(frame,bacheca,utente).getCreaToDoPage());
            frame.revalidate();
            frame.repaint();
        });



    }

    public JPanel getBachecaMainPage(){
        return bachecaMainPagePanel;
    }
}
