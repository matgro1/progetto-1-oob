package org.example.model;

import org.example.Home;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {

    private JPanel mainPage;
    private JList<Bacheca> bachecheList;
    private JButton logoutButton;
    private JButton creaBachecaButton;
    public MainPage(JFrame frame, Utente utente){

        DefaultListModel<Bacheca> bachecheModel = new DefaultListModel<>();
        for (Bacheca bacheca : utente.getBacheche()) {
            bachecheModel.addElement(bacheca);
        }
        bachecheList.setModel(bachecheModel);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setContentPane(new Home().getLoginPanel());
                frame.revalidate();
                frame.repaint();
            }
        });

        creaBachecaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setContentPane(new CreaBachecaPage(frame, utente).getCreaBachecaPage());
                frame.revalidate();
                frame.repaint();

            }
        });
        bachecheList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Bacheca bachecaSelezionata = bachecheList.getSelectedValue();
                    if (bachecaSelezionata != null) {
                        System.out.println("Hai selezionato: " + bachecaSelezionata);
                    }
                }

            }
        });
    }
    public JPanel getMainPage() {
        return mainPage;
    }

}
