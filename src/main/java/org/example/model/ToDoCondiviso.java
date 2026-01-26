package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ToDoCondiviso extends ToDo {

    @Getter @Setter
    private int utenteCondivisoId;
    @Getter @Setter
    private int ultimoModificatoreId;
    @Getter @Setter
    private LocalDate dataCondivisione;

    public ToDoCondiviso(String titolo, LocalDate dataScadenza, int bachecaId,
                         int creatoreId, LocalDate dataCondivisione) {
        super(titolo, dataScadenza, bachecaId);
        this.utenteCondivisoId= creatoreId;
        this.ultimoModificatoreId = creatoreId;
        this.dataCondivisione = dataCondivisione;
    }

    public ToDoCondiviso(int id, String titolo, LocalDate dataScadenza, boolean completato, int bachecaId,
                         int creatoreId, int ultimoModificatoreId, LocalDate dataCondivisione) {
        super(id, titolo, dataScadenza, completato, bachecaId);
        this.utenteCondivisoId = creatoreId;
        this.ultimoModificatoreId = ultimoModificatoreId;
        this.dataCondivisione = dataCondivisione;
    }

}
