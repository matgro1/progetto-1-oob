package org.example.model;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Bacheca.
 */
public class Bacheca {
    @Getter @Setter private int id;
    @Getter @Setter private String titolo;
    @Getter @Setter private String descrizione;
    @Getter @Setter private int utenteId;

    /**
     * Instantiates a new Bacheca.
     *
     * @param titolo      the titolo
     * @param descrizione the descrizione
     * @param utenteId    the utente id
     */
    public Bacheca(String titolo, String descrizione, int utenteId) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.utenteId = utenteId;
    }

    /**
     * Instantiates a new Bacheca.
     *
     * @param id          the id
     * @param titolo      the titolo
     * @param descrizione the descrizione
     * @param utenteId    the utente id
     */
    public Bacheca(int id, String titolo, String descrizione, int utenteId) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.utenteId = utenteId;
    }
    @Override
    public String toString() {
        return titolo;
    }

}