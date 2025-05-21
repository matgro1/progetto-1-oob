package org.example.model.controller.mainpagecontroller;

import org.example.Home;
import org.example.model.Bacheca;
import org.example.model.BachecaMainPage;
import org.example.model.CreaBachecaPage;
import org.example.model.Utente;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class MainPageControllerImpl implements MainPageController{
    @Override
    public void returnHome(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setContentPane(new Home().getLoginPanel());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public DefaultListModel<Bacheca> creazioneLista(List<Bacheca> bacheche) {
        DefaultListModel<Bacheca> bachecheModel = new DefaultListModel<>();
        for (Bacheca bacheca : bacheche) {
            bachecheModel.addElement(bacheca);
        }
        return bachecheModel;
    }

    @Override
    public void goToCreaBachecaPage(JFrame frame, Utente utente) {
        frame.getContentPane().removeAll();
        frame.setContentPane(new CreaBachecaPage(frame, utente).getCreaBachecaPage());
        frame.revalidate();
        frame.repaint();
    }


    @Override
    public void goToBachecaMainPage(ListSelectionEvent e, JFrame frame, Bacheca bachecaSelezionata, Utente utente) {
        if (!e.getValueIsAdjusting() && bachecaSelezionata != null) {
            frame.getContentPane().removeAll();
            frame.setContentPane(new BachecaMainPage(frame, bachecaSelezionata, utente).getBachecaMainPage());
            frame.revalidate();
            frame.repaint();
        }
    }
}
