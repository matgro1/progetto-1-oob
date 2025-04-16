package org.example.model;

import java.util.ArrayList;

public class Bacheca {
    String titolo;
    String descrizione;
    ArrayList<ToDo> todo= new ArrayList<ToDo>();
    public Bacheca(String t,String desc){
        titolo=t;
        descrizione=desc;
    }
}
