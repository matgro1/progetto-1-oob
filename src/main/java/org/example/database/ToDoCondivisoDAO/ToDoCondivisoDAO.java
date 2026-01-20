package org.example.database.ToDoCondivisoDAO;
import org.example.model.ToDoCondiviso;

import java.util.List;

public interface ToDoCondivisoDAO {
    ToDoCondiviso save(ToDoCondiviso todoCondiviso);
    ToDoCondiviso findById(int id);
    List<ToDoCondiviso> findByBachecaID(int bachecaId);
    List<ToDoCondiviso> findByUtenteCondivisoID(int utenteId);
    void update(ToDoCondiviso todoCondiviso);
    void delete(int id);
}
