package org.example.gui;
import org.example.controller.creabachecapagecontroller.CreaBachecaPageController;
import org.example.controller.creabachecapagecontroller.CreaBachecaPageControllerImpl;
import javax.swing.*;
public class CreaBachecaPage {
    private JPanel creaBachecaPagePanel;
    private JTextField textField1;
    private JButton annullaButton;
    private JButton creaButton;
    private JTextField textField2;
    private final CreaBachecaPageController controller = new CreaBachecaPageControllerImpl();
    public CreaBachecaPage(){
        annullaButton.addActionListener(e->controller.returnMainPage());
        creaButton.addActionListener(e-> controller.creaBacheca(creaBachecaPagePanel,textField1,textField2));
    }

    public JPanel getCreaBachecaPage() {
        return creaBachecaPagePanel;
    }
}
