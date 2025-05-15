package org.example.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaBachecaPage {
    private JPanel CreaBachecaPage;
    private JTextField textField1;
    private JLabel nome;
    private JButton annullaButton;
    private JButton creaButton;
    private JTextField textField2;
    public CreaBachecaPage(JFrame frame, Utente utente){
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                frame.getContentPane().removeAll();
                frame.setContentPane(new MainPage(frame, utente).getMainPage());
                frame.revalidate();
                frame.repaint();
            }
        });
        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputTitolo = textField1.getText();
                String inputDescrizione = textField2.getText();
                if(inputTitolo.isEmpty()){

                    JOptionPane.showMessageDialog(CreaBachecaPage, "titolo non valido!", "Errore di registrazione", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    utente.creaBacheca(inputTitolo,inputDescrizione);
                    frame.setVisible(true);
                    frame.getContentPane().removeAll();
                    frame.setContentPane(new MainPage(frame, utente).getMainPage());
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

    }

    public JPanel getCreaBachecaPage() {
        return CreaBachecaPage;
    }
}
