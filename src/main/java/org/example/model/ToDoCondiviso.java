package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ToDoCondiviso extends ToDo {

    @Getter @Setter
    private int utenteCreatoreId;
    @Getter @Setter
    private int ultimoModificatoreId;
    @Getter @Setter
    private LocalDate dataCondivisione;
    @Getter @Setter
    private int bachecaOriginaleId;
    @Getter @Setter
    private int utenteCondivisoId;

    public ToDoCondiviso(String titolo, LocalDate dataScadenza, int bachecaId,
                         int creatoreId,int utenteCondivisoId,LocalDate dataCondivisione, int bachecaOriginaleId) {
        super(titolo, dataScadenza, bachecaId);
        this.utenteCreatoreId = creatoreId;
        this.ultimoModificatoreId = creatoreId;
        this.dataCondivisione = dataCondivisione;
        this.bachecaOriginaleId = bachecaOriginaleId;
        this.utenteCondivisoId = utenteCondivisoId;
    }

    public ToDoCondiviso(int id, String titolo, LocalDate dataScadenza, boolean completato, int bachecaId,
                         int creatoreId, int utenteCondivisoId, int ultimoModificatoreId, LocalDate dataCondivisione, int bachecaOriginaleId) {
        super(id, titolo, dataScadenza, completato, bachecaId);
        this.utenteCreatoreId = creatoreId;
        this.ultimoModificatoreId = ultimoModificatoreId;
        this.dataCondivisione = dataCondivisione;
        this.bachecaOriginaleId = bachecaOriginaleId;
        this.utenteCondivisoId = utenteCondivisoId;
    }

}
