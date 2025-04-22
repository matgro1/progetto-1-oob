package org.example.model;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.time.LocalDate;
import java.util.ArrayList;

public class ToDo {
    protected String titolo;
    protected LocalDate dataScadenza;
    protected String id;
    protected String url;
    protected ImageIcon immaggine;
    protected ColorUIResource colore;
    protected Stato stato;
    protected ArrayList<ChecklistItem> checklist;

    public ToDo(String t, LocalDate dS) {
        titolo = t;
        dataScadenza = dS;
        id = generaId();
        url = "";
        immaggine = new ImageIcon("img/img.png");
        colore = new ColorUIResource((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        stato = Stato.INCORSO;
        checklist = new ArrayList<>();
    }

    private String generaId() {
        return getClass().toString() + "-" + dataScadenza + "-" + titolo.replaceAll("\\s+", "_");
    }

    public String getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public void aggiungiChecklistItem(ChecklistItem item) {
        checklist.add(item);
    }

    public boolean tuttiCompletati() {
        for (ChecklistItem item : checklist) {
            if (!item.isCompletato()) {
                return false;
            }
        }
        return true;
    }

    public void verificaChecklist() {
        if (tuttiCompletati()) {
            this.stato = Stato.COMPLETO;
        }
    }

    public ArrayList<ChecklistItem> getChecklist() {
        return checklist;
    }
}
