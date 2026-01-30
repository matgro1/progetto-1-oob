package org.example.controller.creabachecapagecontroller;


import javax.swing.*;

/**
 * The interface Crea bacheca page controller.
 */
public interface CreaBachecaPageController {
    /**
     * Return main page.
     */
    void returnMainPage();

    /**
     * Crea bacheca.
     *
     * @param creaBachecaPagePanel the crea bacheca page panel
     * @param textField1           the text field 1
     * @param textField2           the text field 2
     */
    void creaBacheca(JPanel creaBachecaPagePanel, JTextField textField1, JTextField textField2);

}
