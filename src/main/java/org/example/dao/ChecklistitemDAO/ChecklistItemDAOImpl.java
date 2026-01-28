package org.example.dao.ChecklistitemDAO;

import org.example.database.DatabaseConnection;
import org.example.model.ChecklistItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChecklistItemDAOImpl implements ChecklistItemDAO {
    public ChecklistItem save(ChecklistItem item) {
        String sql = "INSERT INTO checklist_items (stato, todo_id, descrizione) VALUES (?, ?, ?) RETURNING id";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setBoolean(1, item.getStato());
            stmt.setInt(2, item.getTodoId());
            stmt.setString(3, item.getDescrizione());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                item.setId(rs.getInt("id"));
            }
            return item;
        } catch (SQLException e) {
            throw new RuntimeException("errore inserimento bacheca", e);
        }
    }

    @Override
    public List<ChecklistItem> findByToDoId(int todoId) {
        String sql = "SELECT * FROM checklist_items WHERE todo_id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, todoId);
            ResultSet rs = stmt.executeQuery();
            List<ChecklistItem> items = new java.util.ArrayList<>();
            while (rs.next()) {
                ChecklistItem item = new ChecklistItem(
                        rs.getInt("id"),
                        rs.getString("descrizione"),
                        rs.getBoolean("stato"),
                        rs.getInt("todo_id")
                                );
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca checklist items per todo id", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM checklist_items WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("errore eliminazione checklist item", e);
        }
    }

    @Override
    public void update(ChecklistItem item) {
        String sql = "UPDATE checklist_items SET descrizione = ?, stato = ? WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setString(1, item.getDescrizione());
            stmt.setBoolean(2, item.getStato());
            stmt.setInt(3, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("errore aggiornamento checklist item", e);
        }
    }
}
