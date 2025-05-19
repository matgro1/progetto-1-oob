package org.example.model;

import org.example.Home;

import javax.swing.*;

public class MainPage {

    private JPanel mainPagePanel;
    private JList<Bacheca> bachecheList;
    private JButton logoutButton;
    private JButton creaBachecaButton;
    public MainPage(JFrame frame, Utente utente){

        DefaultListModel<Bacheca> bachecheModel = new DefaultListModel<>();
        for (Bacheca bacheca : utente.getBacheche()) {
            bachecheModel.addElement(bacheca);
        }
        bachecheList.setModel(bachecheModel);

        logoutButton.addActionListener(e->{
                frame.getContentPane().removeAll();
                frame.setContentPane(new Home().getLoginPanel());
                frame.revalidate();
                frame.repaint();
        });

        creaBachecaButton.addActionListener(e-> {
                frame.getContentPane().removeAll();
                frame.setContentPane(new CreaBachecaPage(frame, utente).getCreaBachecaPage());
                frame.revalidate();
                frame.repaint();
        });
        bachecheList.addListSelectionListener(e->{
                if (!e.getValueIsAdjusting()) {
                    Bacheca bachecaSelezionata = bachecheList.getSelectedValue();
                    if (bachecaSelezionata != null) {
                        frame.getContentPane().removeAll();
                        frame.setContentPane(new BachecaMainPage(frame, bachecaSelezionata, utente).getBachecaMainPage());
                        frame.revalidate();
                        frame.repaint();
                    }
                }
        });
    }
    public JPanel getMainPage() {
        return mainPagePanel;
    }

}
