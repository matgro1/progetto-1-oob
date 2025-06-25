package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToDo {
    protected String titolo;
    protected LocalDate dataScadenza;
    protected String id;
    protected String url;
    protected ImageIcon immagine;
    protected ColorUIResource colore;
    @Getter @Setter
    protected boolean stato;
    protected ArrayList<ChecklistItem> checklist;

    public ToDo(String t, LocalDate dS) {
        titolo = t;
        dataScadenza = dS;
        id = generaId();
        url = "";
        immagine = new ImageIcon("img/img.png");
        Random random = new Random();
        colore = new ColorUIResource(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
        );
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
        // Se la checklist è vuota, non ci sono elementi da completare,
        // quindi non può essere considerata "tutta completata".
        if (checklist.isEmpty()) {
            return false;
        }

        // Se la checklist non è vuota, controlliamo ogni elemento.
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