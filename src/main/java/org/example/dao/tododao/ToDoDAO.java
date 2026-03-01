package org.example.dao.tododao;

import org.example.model.ToDo;
import java.util.ArrayList;

/**
 * The interface To do dao.
 */
public interface ToDoDAO {
    /**
     * Save to do.
     *
     * @param todo the todo
     * @return the to do
     */
    ToDo save(ToDo todo);



    /**
     * Find by bacheca id array list.
     *
     * @param bachecaId the bacheca id
     * @return the array list
     */
    ArrayList<ToDo> findByBachecaId(int bachecaId);

    /**
     * Update.
     *
     * @param todo the todo
     */
    void update(ToDo todo);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(int id);
}
