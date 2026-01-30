package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * The type To do.
 */
public class ToDo {


    @Getter @Setter private int id;
    @Getter @Setter private String titolo;
    @Getter @Setter private LocalDate dataScadenza;
    @Getter @Setter private boolean completato;
    @Getter @Setter private int bachecaId;


    /**
     * Instantiates a new To do.
     *
     * @param titolo       the titolo
     * @param dataScadenza the data scadenza
     * @param bachecaId    the bacheca id
     */
    public ToDo(String titolo, LocalDate dataScadenza, int bachecaId) {
        this.titolo = titolo;
        this.dataScadenza = dataScadenza;
        this.completato = false;
        this.bachecaId = bachecaId;
    }

    /**
     * Instantiates a new To do.
     *
     * @param id           the id
     * @param titolo       the titolo
     * @param dataScadenza the data scadenza
     * @param completato   the completato
     * @param bachecaId    the bacheca id
     */
    public ToDo(int id, String titolo, LocalDate dataScadenza, boolean completato, int bachecaId) {
        this.id = id;
        this.titolo = titolo;
        this.dataScadenza = dataScadenza;
        this.completato = completato;
        this.bachecaId = bachecaId;
    }


    @Override
    public String toString() {
        String status = completato ? "✓ " : " ";
        return status + titolo + " (Scad.: " + (dataScadenza != null ? dataScadenza.toString() : "N/D") + ")";
    }
}