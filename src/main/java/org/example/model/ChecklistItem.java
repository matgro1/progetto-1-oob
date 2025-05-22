package org.example.model;

public class ChecklistItem {
    private String descrizione;
    private StatoChecklist stato;
    @Override
    public String toString() {
        return descrizione + (stato == StatoChecklist.COMPLETATO ? " ✓" : "");
    }
    public ChecklistItem(String descrizione) {
        this.descrizione = descrizione;
        this.stato = StatoChecklist.NONCOMPLETATO;
    }
    public String getDescrizione() {
        return descrizione;
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
