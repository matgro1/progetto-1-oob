package org.example.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Bacheca;
import org.example.model.ToDo;
import org.example.model.Utente;

import javax.swing.*;

/**
 * The type Session manager.
 */
public class SessionManager {
    private static SessionManager instance;

    @Getter @Setter
    private Utente currentUser;

    @Getter @Setter
    private Bacheca currentBacheca;

    @Getter @Setter
    private ToDo currentToDo;

    @Getter @Setter
    private JFrame mainFrame;

    private SessionManager() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
}