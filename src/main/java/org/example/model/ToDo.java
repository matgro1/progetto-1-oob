package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
// Non più ArrayList o List per la checklist qui.
// Le ChecklistItem verranno recuperate tramite ChecklistItemDAO.

public class ToDo {

    // Il metodo getId() ora restituisce l'ID numerico generato dal DB
    // Non più la complessa logica basata su stringhe.
    // L'override per ToDoCondiviso dovrà considerare l'ID del ToDo padre.

    @Getter @Setter private int id; // Corrisponde a id SERIAL PRIMARY KEY nel DB
    @Getter @Setter private String titolo;
    @Getter @Setter private LocalDate dataScadenza;
    @Getter @Setter private boolean completato; // Corrisponde a completato BOOLEAN DEFAULT FALSE nel DB
    @Getter @Setter private int bachecaId; // Chiave esterna per la Bacheca di appartenenza

    // I campi e i metodi relativi alla checklist sono stati rimossi da qui,
    // poiché ChecklistItem è una sua entità gestita tramite DAO.
    // private ArrayList<ChecklistItem> checklist = new ArrayList<>();
    // private boolean completatoConChecklist = false; // Questo stato sarà determinato dal DB

    // Costruttore per creare un nuovo ToDo (prima del salvataggio nel DB)
    public ToDo(String titolo, LocalDate dataScadenza, int bachecaId) {
        this.titolo = titolo;
        this.dataScadenza = dataScadenza;
        this.completato = false; // Di default non completato
        this.bachecaId = bachecaId;
    }

    // Costruttore per popolare l'oggetto dal DB (quando l'ID è già noto)
    public ToDo(int id, String titolo, LocalDate dataScadenza, boolean completato, int bachecaId) {
        this.id = id;
        this.titolo = titolo;
        this.dataScadenza = dataScadenza;
        this.completato = completato;
        this.bachecaId = bachecaId;
    }


    // I metodi che manipolavano la checklist interna (aggiungiChecklistItem, verificaChecklist)
    // sono stati rimossi. La gestione degli ChecklistItem è ora responsabilità di ChecklistItemDAO
    // e la verifica dello stato di completamento del ToDo sarà fatta in base agli item dal DB.

    // Il toString() può rimanere utile per la JList
    @Override
    public String toString() {
        String status = completato ? "✓ " : " ";
        return status + titolo + " (Scad.: " + (dataScadenza != null ? dataScadenza.toString() : "N/D") + ")";
    }
}