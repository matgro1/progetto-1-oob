package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Utente {

    private String login=new String();
    private String password=new String();
    ArrayList<Bacheca> bacheche= new ArrayList<Bacheca>();

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


    void creaBacheca(String nome, String desc){//
        if (successoAccesso == true){
            bacheche.add(new Bacheca(nome,desc));
        }
    }
    void creaToDo(String nomeB, String nome, LocalDate dataScadenza){
        if(bacheche.isEmpty());
        else{
            for(Bacheca b:bacheche){
                if (b.titolo.compareTo(nomeB)==0) b.todo.add(new ToDo(nome,dataScadenza));
            }
        }
    }


}
