package org.example;

public class Utente {

    private String login=new String();
    private String password=new String();

    public Utente(String loginIniz, String passwordIniz){
        login=loginIniz;
        password=passwordIniz;
    }
    private boolean successoAccesso=false;


    void accesso(String loginM, String passwordM) {
        if (login.equals(loginM)&&password.equals(passwordM))
            successoAccesso=true;
    }
    void logout() {
        successoAccesso=false;
    }



}
