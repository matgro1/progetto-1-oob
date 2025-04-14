package org.example;

import java.time.LocalDate;

public class ToDoCondiviso extends ToDo {

    Utente creatore;
    Utente ultimoModificatore;
    LocalDate dataCondivisione;
    public ToDoCondiviso(Utente uC,LocalDate dC){
        creatore=uC;
        ultimoModificatore=uC; //perchè perforza il creatore  se appena creato è anche l'ultimo modificatore
        dataCondivisione=dC;
    }
}
