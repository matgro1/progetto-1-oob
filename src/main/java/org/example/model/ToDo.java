package org.example.model;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.time.LocalDate;
public class ToDo {
    protected String titolo=new String();
    protected LocalDate dataScadenza;

    protected String url= new String();
    protected ImageIcon immaggine=new ImageIcon("img/img.png");

    protected ColorUIResource colore=new ColorUIResource((int)(Math.random() * 256),(int)(Math.random() * 256),(int)(Math.random() * 256));
    protected Stato stato = Stato.INCORSO;


    public ToDo(String t, LocalDate dS){
        titolo=t;
        dataScadenza=dS;
    }

}
