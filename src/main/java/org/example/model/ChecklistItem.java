package org.example.model;

import lombok.Getter;
import lombok.Setter;

public class ChecklistItem {
    @Getter @Setter private int id;
    @Getter @Setter private String descrizione;
    @Getter @Setter private Boolean stato;
    @Getter @Setter private int todoId;

    public ChecklistItem(String descrizione, int todoId) {
        this.descrizione = descrizione;
        this.stato = false;
        this.todoId = todoId;
    }

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