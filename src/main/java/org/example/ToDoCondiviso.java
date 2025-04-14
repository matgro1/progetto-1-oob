package org.example;

import java.time.LocalDate;

public class ToDoCondiviso extends ToDo {

    Utente creatore;
    Utente ultimoModificatore;
    LocalDate dataCondivisione;
    public ToDoCondiviso(String t,LocalDate dS,Utente uC,LocalDate dC){
        super(t,dS);
        creatore=uC;
        ultimoModificatore=uC; //perchè perforza il creatore  se appena creato è anche l'ultimo modificatore
        dataCondivisione=dC;
    }
}
