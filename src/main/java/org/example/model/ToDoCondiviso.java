package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ToDoCondiviso extends ToDo {

    @Getter @Setter
    private int creatoreId;
    @Getter @Setter
    private int ultimoModificatoreId;
    @Getter @Setter
    private LocalDate dataCondivisione;

    public ToDoCondiviso(String titolo, LocalDate dataScadenza, int bachecaId,
                         int creatoreId, LocalDate dataCondivisione) {
        super(titolo, dataScadenza, bachecaId); // Chiama il costruttore di ToDo
        this.creatoreId = creatoreId;
        this.ultimoModificatoreId = creatoreId; // Inizialmente creatore e ultimo modificatore sono gli stessi
        this.dataCondivisione = dataCondivisione;
    }

    // Costruttore per popolare l'oggetto dal DB (include tutti i campi)
    public ToDoCondiviso(int id, String titolo, LocalDate dataScadenza, boolean completato, int bachecaId,
                         int creatoreId, int ultimoModificatoreId, LocalDate dataCondivisione) {
        super(id, titolo, dataScadenza, completato, bachecaId); // Chiama il costruttore di ToDo
        this.creatoreId = creatoreId;
        this.ultimoModificatoreId = ultimoModificatoreId;
        this.dataCondivisione = dataCondivisione;
    }

}
