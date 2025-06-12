package org.example.controller.mainpagecontroller;

import org.example.Home;
import org.example.gui.ModificaUserPage;
import org.example.model.Bacheca;
import org.example.gui.BachecaMainPage;
import org.example.gui.CreaBachecaPage;
import org.example.controller.ControllerFather;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class MainPageControllerImpl extends ControllerFather implements MainPageController{
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
    public void goToDialogModificaUtente() {
        ModificaUserPage dialog= new ModificaUserPage();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }


    @Override
    public void goToBachecaMainPage(ListSelectionEvent e, Bacheca bachecaSelezionata) {
        if (!e.getValueIsAdjusting() && bachecaSelezionata != null) {
            assegnaBachecaFattaAPartePercheSeNoSolarLintMiCagaIlCazzoCheLaFunzioneNonEStaticaAncheSeProvieneDaUnImplementazione(bachecaSelezionata);
            frame.getContentPane().removeAll();
            frame.setContentPane(new BachecaMainPage().getBachecaMainPage());
            frame.revalidate();
            frame.repaint();
        }
    }
    static void assegnaBachecaFattaAPartePercheSeNoSolarLintMiCagaIlCazzoCheLaFunzioneNonEStaticaAncheSeProvieneDaUnImplementazione(Bacheca b){
        bacheca=b;
    }
}
