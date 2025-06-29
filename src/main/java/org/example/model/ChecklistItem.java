package org.example.model;

import lombok.Getter;
import lombok.Setter;

public class ChecklistItem {
    @Getter @Setter private int id; // Corrisponde a id SERIAL PRIMARY KEY nel DB
    @Getter @Setter private String descrizione;
    @Getter @Setter private Boolean stato; // Corrisponde a stato BOOLEAN DEFAULT FALSE nel DB
    @Getter @Setter private int todoId; // Chiave esterna per il ToDo a cui appartiene

    // Costruttore per creare un nuovo ChecklistItem (prima del salvataggio nel DB)
    public ChecklistItem(String descrizione, int todoId) {
        this.descrizione = descrizione;
        this.stato = false; // Di default non completato
        this.todoId = todoId;
    }

    // Costruttore per popolare l'oggetto dal DB (quando l'ID è già noto)
    public ChecklistItem(int id, String descrizione, Boolean stato, int todoId) {
        this.id = id;
        this.descrizione = descrizione;
        this.stato = stato;
        this.todoId = todoId;
    }

    @Override
    public String toString() {
        return descrizione + (stato == true ? " ✓" : "");
    }

    public boolean isCompletato() {
        return this.stato;
    }
}