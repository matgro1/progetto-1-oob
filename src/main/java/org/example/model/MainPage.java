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
    public MainPage(){


        bachecheList.setModel(controller.creazioneLista());

        logoutButton.addActionListener(e->controller.returnHome());

        creaBachecaButton.addActionListener(e->controller.goToCreaBachecaPage());
        bachecheList.addListSelectionListener(e->controller.goToBachecaMainPage(e, bachecheList.getSelectedValue()));

    }
    public JPanel getMainPage() {
        return mainPagePanel;
    }

}
