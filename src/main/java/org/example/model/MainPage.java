package org.example.model;

import javax.swing.*;
import org.example.model.controller.mainpagecontroller.MainPageControllerImpl;
// todo implementare modifica utente
public class MainPage {

    private JPanel mainPagePanel;
    private JList<Bacheca> bachecheList;
    private JButton logoutButton;
    private JButton creaBachecaButton;
    MainPageControllerImpl controller= new MainPageControllerImpl();
    public MainPage(JFrame frame, Utente utente){


        bachecheList.setModel(controller.creazioneLista(utente.getBacheche()));

        logoutButton.addActionListener(e->controller.returnHome(frame));

        creaBachecaButton.addActionListener(e->controller.goToCreaBachecaPage(frame,utente));
        bachecheList.addListSelectionListener(e->controller.goToBachecaMainPage(e,frame, bachecheList.getSelectedValue(),utente));

    }
    public JPanel getMainPage() {
        return mainPagePanel;
    }

}
