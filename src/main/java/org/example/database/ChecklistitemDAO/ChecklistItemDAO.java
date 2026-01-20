package org.example.database.ChecklistitemDAO;
import org.example.model.ChecklistItem;
import java.util.List;

public interface ChecklistItemDAO {
    ChecklistItem save(ChecklistItem item);
    List<ChecklistItem> findByToDoId(int todoId);
    void update(ChecklistItem item);
    void delete(int id);
}
