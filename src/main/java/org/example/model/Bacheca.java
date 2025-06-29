package org.example.model;

import lombok.Getter;
import lombok.Setter; // Aggiunto per setId, setTitolo, setDescrizione, setUtenteId

// Nota: Le liste come `todo` non devono più essere mantenute qui
// Le relazioni verranno gestite tramite i DAO
public class Bacheca {
    @Getter @Setter private int id; // Corrisponde a id SERIAL PRIMARY KEY nel DB
    @Getter @Setter private String titolo;
    @Getter @Setter private String descrizione;
    @Getter @Setter private int utenteId; // Chiave esterna per l'Utente proprietario

    // Costruttore per la creazione di nuove Bacheche (prima del salvataggio nel DB)
    public Bacheca(String titolo, String descrizione, int utenteId) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.utenteId = utenteId;
    }

    // Costruttore per popolare da DB (quando l'ID è già noto)
    public Bacheca(int id, String titolo, String descrizione, int utenteId) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.utenteId = utenteId;
    }

    @Override
    public String toString() {
        return titolo; // Utile per visualizzare nella JList
    }

    // Metodi come `removeToDo()` e `getToDo()` sono stati rimossi.
    // Per ottenere i ToDo di questa bacheca, userai ToDoDAO.findByBachecaId(this.id).
}