package org.example.model;

import java.util.ArrayList;

public class Bacheca {
    private String titolo;
    private String descrizione;
    ArrayList<ToDo> todo= new ArrayList<ToDo>();
    public Bacheca(String t,String desc){
        titolo=t;
        descrizione=desc;
    }

    String getNome(){
        return titolo;
    }
}
