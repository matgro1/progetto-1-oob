package org.example.model;

import lombok.Getter;
import lombok.Setter;


/**
 * The type Utente.
 */
public class Utente {

    // Getters
    @Getter @Setter
    private String login;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private int id;


    /**
     * Instantiates a new Utente.
     *
     * @param login    the login
     * @param password the password
     */
    public Utente(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Instantiates a new Utente.
     *
     * @param id       the id
     * @param login    the login
     * @param password the password
     */
    public Utente(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }


    /**
     * Change user.
     *
     * @param username the username
     * @param pss      the pss
     */
    public void changeUser(String username, String pss){
        login = username;
        password = pss;
    }


}
