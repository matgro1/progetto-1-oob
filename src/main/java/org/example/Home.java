package org.example;

import org.example.controller.homecontroller.HomeController;
import org.example.controller.homecontroller.HomeControllerImpl;

import javax.swing.*;


import static java.util.logging.Level.*;

/**
 * The type Home.
 */
public class Home {


    private JPanel login;
    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    private static final HomeController controller = new HomeControllerImpl();

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(SEVERE, null, e);
        }
        controller.inizializzazione();
    }

    /**
     * Instantiates a new Home.
     */
    public Home() {

        loginButton.addActionListener(e->controller.goToMainPage(username,password,login));
        registerButton.addActionListener(e-> controller.addUser(username,password,login));

    }

    /**
     * Gets login panel.
     *
     * @return the login panel
     */
// Da aggiungere nella classe Home
    public JPanel getLoginPanel() {
        return login;
    }

}
