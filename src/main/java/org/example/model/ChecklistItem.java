package org.example.model;

import lombok.*;

public class ChecklistItem {
    private String descrizione;
    @Setter
    private Boolean stato;
    @Override
    public String toString() {
        return descrizione + (stato == true ? " ✓" : "");
    }
    public ChecklistItem(String descrizione) {
        this.descrizione = descrizione;
        this.stato = false;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public Boolean getStato() {
        return stato;
    }

    public boolean isCompletato() {
        return this.stato;
    }
}
