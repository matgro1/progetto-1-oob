package org.example.gui;

import javax.swing.*;
import org.example.controller.mainpagecontroller.MainPageControllerImpl;
import org.example.model.Bacheca;


public class MainPage {

    private JPanel mainPagePanel;
    private JList<Bacheca> bachecheList;
    private JButton logoutButton;
    private JButton creaBachecaButton;
    private JButton modificaUtenteButton;
    MainPageControllerImpl controller= new MainPageControllerImpl();
    public MainPage(){


        bachecheList.setModel(controller.creazioneLista());

        logoutButton.addActionListener(e->controller.returnHome());

        modificaUtenteButton.addActionListener(e ->controller.goToDialogModificaUtente());
        creaBachecaButton.addActionListener(e->controller.goToCreaBachecaPage());
        bachecheList.addListSelectionListener(e->controller.goToBachecaMainPage(e, bachecheList.getSelectedValue()));

    }
    public JPanel getMainPage() {
        return mainPagePanel;
    }

}
