package org.example.database.ToDoDAO;

import org.example.database.DatabaseConnection;
import org.example.model.ToDo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ToDoDAOImpl implements ToDoDAO {
    @Override
    public ToDo save(ToDo todo) {
       String sql = "INSERT INTO todos (titolo, data_scadenza, bacheca_id, completato) VALUES (?, ?, ?, ?) RETURNING id";
       try (Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
           stmt.setString(1, todo.getTitolo());
           stmt.setDate(2, java.sql.Date.valueOf(todo.getDataScadenza()));
           stmt.setInt(3, todo.getBachecaId());
           stmt.setBoolean(4, todo.isCompletato());
           ResultSet rs = stmt.executeQuery();
           if (rs.next()) {
               todo.setId(rs.getInt("id"));
           }
           return todo;
       } catch (SQLException e) {
           throw new RuntimeException("errore inserimento todo", e);
       }
    }

    @Override
    public List<ToDo> findByBachecaId(int bachecaId) {
        String sql = "SELECT * FROM todos WHERE bacheca_id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, bachecaId);
            ResultSet rs = stmt.executeQuery();
            List<ToDo> todos = new java.util.ArrayList<>();
            while (rs.next()) {
                ToDo todo = new ToDo(
                                        rs.getInt("id"),
                                        rs.getString("titolo"),
                                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getBoolean("completato"),
                        rs.getInt("bacheca_id")
                                );
                todos.add(todo);
            }
            return todos;
        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca todos per bacheca id", e);
        }

    }

    @Override
    public ToDo findById(int id) {
        String sql = "SELECT * FROM todos WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ToDo(
                        rs.getInt("id"),
                        rs.getString("titolo"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getBoolean("completato"),
                        rs.getInt("bacheca_id")
                );

            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca todo per id", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("errore eliminazione todo", e);
        }
    }

    @Override
    public void update(ToDo todo) {
        String sql = "UPDATE todos SET titolo = ?, data_scadenza = ?, completato = ? WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setString(1, todo.getTitolo());
            stmt.setDate(2, java.sql.Date.valueOf(todo.getDataScadenza()));
            stmt.setBoolean(3, todo.isCompletato());
            stmt.setInt(4, todo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("errore aggiornamento todo", e);
        }
    }
}
