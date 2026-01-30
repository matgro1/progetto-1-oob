package org.example.gui;
import org.example.controller.creabachecapagecontroller.CreaBachecaPageController;
import org.example.controller.creabachecapagecontroller.CreaBachecaPageControllerImpl;
import javax.swing.*;

/**
 * The type Crea bacheca page.
 */
public class CreaBachecaPage {
    private JPanel creaBachecaPagePanel;
    private JTextField textField1;
    private JButton annullaButton;
    private JButton creaButton;
    private JTextField textField2;
    private final CreaBachecaPageController controller = new CreaBachecaPageControllerImpl();

    /**
     * Instantiates a new Crea bacheca page.
     */
    public CreaBachecaPage(){
        annullaButton.addActionListener(e->controller.returnMainPage());
        creaButton.addActionListener(e-> controller.creaBacheca(creaBachecaPagePanel,textField1,textField2));
    }

    /**
     * Gets crea bacheca page.
     *
     * @return the crea bacheca page
     */
    public JPanel getCreaBachecaPage() {
        return creaBachecaPagePanel;
    }
}
