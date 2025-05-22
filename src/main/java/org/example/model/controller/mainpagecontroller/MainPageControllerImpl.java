package org.example.model.controller.mainpagecontroller;

import org.example.Home;
import org.example.model.Bacheca;
import org.example.model.BachecaMainPage;
import org.example.model.CreaBachecaPage;
import org.example.model.controller.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class MainPageControllerImpl extends Controller implements MainPageController{
    @Override
    public void returnHome() {
        frame.getContentPane().removeAll();
        frame.setContentPane(new Home().getLoginPanel());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public DefaultListModel<Bacheca> creazioneLista() {
        List<Bacheca> bacheche = utente.getBacheche();
        DefaultListModel<Bacheca> bachecheModel = new DefaultListModel<>();
        for (Bacheca bacheca : bacheche) {
            bachecheModel.addElement(bacheca);
        }
        return bachecheModel;
    }

    @Override
    public void goToCreaBachecaPage() {
        frame.getContentPane().removeAll();
        frame.setContentPane(new CreaBachecaPage().getCreaBachecaPage());
        frame.revalidate();
        frame.repaint();
    }


    @Override
    public void goToBachecaMainPage(ListSelectionEvent e, Bacheca bachecaSelezionata) {
        if (!e.getValueIsAdjusting() && bachecaSelezionata != null) {
            frame.getContentPane().removeAll();
            frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
            frame.revalidate();
            frame.repaint();
        }
    }
}
