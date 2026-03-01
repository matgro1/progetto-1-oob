package org.example.dao.tododao;

import org.example.database.DatabaseConnection;
import org.example.model.ToDo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type To do dao.
 */
public class ToDoDAOImpl implements ToDoDAO {

    private static final Logger LOGGER = Logger.getLogger(ToDoDAOImpl.class.getName());

    private static final String COL_ID = "id";
    private static final String COL_TITOLO = "titolo";
    private static final String COL_DATA_SCADENZA = "data_scadenza";
    private static final String COL_COMPLETATO = "completato";
    private static final String COL_BACHECA_ID = "bacheca_id";

    @Override
    public ToDo save(ToDo todo) {
        String sql = "INSERT INTO todos (titolo, data_scadenza, bacheca_id, completato) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, todo.getTitolo());
            stmt.setDate(2, todo.getDataScadenza() != null ? java.sql.Date.valueOf(todo.getDataScadenza()) : null);
            stmt.setInt(3, todo.getBachecaId());
            stmt.setBoolean(4, todo.isCompletato());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                todo.setId(rs.getInt(COL_ID));
            }
            return todo;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore inserimento todo", e);
            return null;
        }
    }


    @Override
    public ArrayList<ToDo> findByBachecaId(int bachecaId) {
        String sql = "SELECT id, titolo, data_scadenza, completato, bacheca_id FROM todos WHERE bacheca_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bachecaId);
            ResultSet rs = stmt.executeQuery();
            ArrayList<ToDo> todos = new ArrayList<>();
            while (rs.next()) {
                ToDo todo = new ToDo(
                        rs.getInt(COL_ID),
                        rs.getString(COL_TITOLO),
                        rs.getDate(COL_DATA_SCADENZA) != null ? rs.getDate(COL_DATA_SCADENZA).toLocalDate() : null,
                        rs.getBoolean(COL_COMPLETATO),
                        rs.getInt(COL_BACHECA_ID)
                );
                todos.add(todo);
            }
            return todos;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore ricerca todos per bacheca id", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(int id) {
        String sqlChecklist = "DELETE FROM checklist_items WHERE todo_id = ?";
        String sqlToDo = "DELETE FROM todos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sqlChecklist)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(sqlToDo)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore eliminazione todo e checklist", e);
        }
    }

    @Override
    public void update(ToDo todo) {
        String sql = "UPDATE todos SET titolo = ?, data_scadenza = ?, completato = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, todo.getTitolo());
            stmt.setDate(2, todo.getDataScadenza() != null ? java.sql.Date.valueOf(todo.getDataScadenza()) : null);
            stmt.setBoolean(3, todo.isCompletato());
            stmt.setInt(4, todo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore aggiornamento todo", e);
        }
    }
}