package org.example.model;

import lombok.Getter;
import lombok.Setter;

public class Bacheca {
    @Getter @Setter private int id;
    @Getter @Setter private String titolo;
    @Getter @Setter private String descrizione;
    @Getter @Setter private int utenteId;

    public Bacheca(String titolo, String descrizione, int utenteId) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.utenteId = utenteId;
    }

    public Bacheca(int id, String titolo, String descrizione, int utenteId) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.utenteId = utenteId;
    }

}