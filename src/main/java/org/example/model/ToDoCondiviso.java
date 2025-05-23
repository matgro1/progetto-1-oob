package org.example.model;

import java.time.LocalDate;

public class ToDoCondiviso extends ToDo {

    private Utente creatore;
    private Utente ultimoModificatore;
    private LocalDate dataCondivisione;

    public ToDoCondiviso(String t, LocalDate dS, Utente uC, LocalDate dC) {
        super(t, dS);
        creatore = uC;
        ultimoModificatore = uC;
        dataCondivisione = dC;
    }

    public Utente getCreatore() {
        return creatore;
    }

    public Utente getUltimoModificatore() {
        return ultimoModificatore;
    }

    public LocalDate getDataCondivisione() {
        return dataCondivisione;
    }

    public void setUltimoModificatore(Utente ultimoModificatore) {
        this.ultimoModificatore = ultimoModificatore;
    }
}
