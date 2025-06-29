package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Utente {

    // Getters
    @Getter @Setter
    private String login;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private int id;


    public Utente(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Utente(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }





    



    public void changeUser(String username, String pss){
        login = username;
        password = pss;
    }


}
