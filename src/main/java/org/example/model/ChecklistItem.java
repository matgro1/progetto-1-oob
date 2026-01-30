package org.example.model;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Checklist item.
 */
public class ChecklistItem {
    @Getter @Setter private int id;
    @Getter @Setter private String descrizione;
    @Getter @Setter private Boolean stato;
    @Getter @Setter private int todoId;

    /**
     * Instantiates a new Checklist item.
     *
     * @param descrizione the descrizione
     * @param todoId      the todo id
     */
    public ChecklistItem(String descrizione, int todoId) {
        this.descrizione = descrizione;
        this.stato = false;
        this.todoId = todoId;
    }

    /**
     * Instantiates a new Checklist item.
     *
     * @param id          the id
     * @param descrizione the descrizione
     * @param stato       the stato
     * @param todoId      the todo id
     */
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

    /**
     * Is completato boolean.
     *
     * @return the boolean
     */
    public boolean isCompletato() {
        return this.stato;
    }
}