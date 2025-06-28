package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDo {
    protected String titolo;
    protected LocalDate dataScadenza;
    protected String id;

    @Getter @Setter
    protected boolean stato;
    protected ArrayList<ChecklistItem> checklist;

    public ToDo(String t, LocalDate dS) {
        titolo = t;
        dataScadenza = dS;
        id = generaId();
        stato = false;
        checklist = new ArrayList<>();
    }

    private String generaId() {
        return getClass() + "-" + dataScadenza + "-" + titolo.replaceAll("\\s+", "_");
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

    // Nota: getter per 'stato' è già fornito da @Getter

    public void aggiungiChecklistItem(ChecklistItem item) {
        checklist.add(item);
    }


    public boolean tuttiCompletati() {

        if (checklist.isEmpty()) {
            return false;
        }

        for (ChecklistItem item : checklist) {
            if (!item.isCompletato()) {
                return false; // Trovato almeno un elemento non completato
            }
        }
        return true; // Tutti gli elementi sono completati
    }



    public void verificaChecklist() {
        if (!checklist.isEmpty() && tuttiCompletati()) {
            this.stato = true;
        } else {

            this.stato = false;
        }
    }

    public List<ChecklistItem> getChecklist() {
        return checklist;
    }

    public boolean getStato() {
        return stato;
    }
    @Override
    public String toString() {
        return getTitolo();
    }
}