package org.example.model;

public class ChecklistItem {
    private String titolo;
    private String descrizione;
    private StatoChecklist stato;

    public ChecklistItem(String descrizione) {
        this.descrizione = descrizione;
        this.stato = StatoChecklist.NONCOMPLETATO;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public String getTitolo() {
        return titolo;
    }

    public StatoChecklist getStato() {
        return stato;
    }

    public void setStato(StatoChecklist stato) {
        this.stato = stato;
    }

    public void completa() {
        this.stato = StatoChecklist.COMPLETATO;
    }


    public boolean isCompletato() {
        return this.stato == StatoChecklist.COMPLETATO;
    }
}
