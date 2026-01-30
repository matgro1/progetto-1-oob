package org.example.dao.ToDoCondivisoDAO;
import org.example.model.ToDoCondiviso;

import java.util.List;

/**
 * The interface To do condiviso dao.
 */
public interface ToDoCondivisoDAO {
    /**
     * Save to do condiviso.
     *
     * @param todoCondiviso the todo condiviso
     * @return the to do condiviso
     */
    ToDoCondiviso save(ToDoCondiviso todoCondiviso);

    /**
     * Find by id to do condiviso.
     *
     * @param id the id
     * @return the to do condiviso
     */
    ToDoCondiviso findById(int id);

    /**
     * Find by bacheca id list.
     *
     * @param bachecaId the bacheca id
     * @return the list
     */
    List<ToDoCondiviso> findByBachecaID(int bachecaId);

    /**
     * Find by utente condiviso id list.
     *
     * @param utenteId the utente id
     * @return the list
     */
    List<ToDoCondiviso> findByUtenteCondivisoID(int utenteId);

    /**
     * Update.
     *
     * @param todoCondiviso the todo condiviso
     */
    void update(ToDoCondiviso todoCondiviso);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(int id);
}
