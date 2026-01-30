package org.example.dao.ChecklistitemDAO;
import org.example.model.ChecklistItem;
import java.util.List;

/**
 * The interface Checklist item dao.
 */
public interface ChecklistItemDAO {
    /**
     * Save checklist item.
     *
     * @param item the item
     * @return the checklist item
     */
    ChecklistItem save(ChecklistItem item);

    /**
     * Find by to do id list.
     *
     * @param todoId the todo id
     * @return the list
     */
    List<ChecklistItem> findByToDoId(int todoId);

    /**
     * Update.
     *
     * @param item the item
     */
    void update(ChecklistItem item);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(int id);
}
