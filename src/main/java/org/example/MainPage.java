package org.example;

import org.example.model.*;

import javax.swing.*;
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

    }
    public JPanel getMainPage() {
        return mainPage;
    }

}
