package org.example.controller;
import org.example.database.DatabaseConnection;
import org.example.model.Bacheca;
import org.example.model.ToDo;
import org.example.model.Utente;
import javax.swing.*;
import java.util.ArrayList;

import org.example.database.UtenteDAO.UtenteDAO;

public class ControllerFather {
    protected ControllerFather(){}
    protected static JFrame frame;
    protected static Utente utente=null;
    protected static Bacheca bacheca=null;
    protected static ToDo todo=null;
    protected static ArrayList<Utente> utenti = UtenteDAO.findAll();
    protected static ArrayList<Bacheca> bacheche;
    protected static ArrayList<ToDo> todos;

    protected static void getBacheche(){
        bacheche= DatabaseConnection.bachecaDB.findByUtenteId(utente.getId());
    }
    protected static void getToDos(){
        todos= DatabaseConnection.todoDB.findByBachecaId(bacheca.getId());
    }
}
