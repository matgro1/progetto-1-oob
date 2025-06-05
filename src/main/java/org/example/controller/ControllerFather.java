package org.example.controller;
import org.example.model.Bacheca;
import org.example.model.Utente;
import javax.swing.*;
import java.util.ArrayList;

public class ControllerFather {
    protected ControllerFather(){}
    protected static JFrame frame;
    protected static Utente utente=null;
    protected static Bacheca bacheca=null;
    protected static ArrayList<Utente> utenti = new ArrayList<>();
}
