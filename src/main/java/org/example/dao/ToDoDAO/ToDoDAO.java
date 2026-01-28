package org.example.dao.ToDoDAO;

import org.example.model.ToDo;
import java.util.ArrayList;

public interface ToDoDAO {
    ToDo save(ToDo todo);
    ToDo findById(int id);
    ArrayList<ToDo> findByBachecaId(int bachecaId);
    void update(ToDo todo);
    void delete(int id);
}
