package org.example.gui;

import javax.swing.*;
import org.example.controller.mainpagecontroller.MainPageControllerImpl;
import org.example.model.Bacheca;


/**
 * The type Main page.
 */
public class MainPage {

    private JPanel mainPagePanel;
    private JList<Bacheca> bachecheList;
    private JButton logoutButton;
    private JButton creaBachecaButton;
    private JButton modificaUtenteButton;
    /**
     * The Controller.
     */
    MainPageControllerImpl controller= new MainPageControllerImpl();

    /**
     * Instantiates a new Main page.
     */
    public MainPage(){


        bachecheList.setModel(controller.creazioneLista());

        logoutButton.addActionListener(e->controller.returnHome());

        modificaUtenteButton.addActionListener(e ->controller.goToDialogModificaUtente());
        creaBachecaButton.addActionListener(e->controller.goToCreaBachecaPage());
        bachecheList.addListSelectionListener(e->controller.goToBachecaMainPage(e, bachecheList.getSelectedValue()));

    }

    /**
     * Gets main page.
     *
     * @return the main page
     */
    public JPanel getMainPage() {
        return mainPagePanel;
    }

}
