package org.example.dao.ToDoCondivisoDAO;

import org.example.database.DatabaseConnection;
import org.example.model.ToDoCondiviso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToDoCondivisoDAOImpl implements ToDoCondivisoDAO{

    @Override
    public ToDoCondiviso save(ToDoCondiviso todoCondiviso) {
        // Aggiungiamo bacheca_creatore_id alla query
        String sql = "INSERT INTO todos_condivisi (titolo, data_scadenza, bacheca_id, completato, utente_condivisore_id, ultimo_modificatore_id, data_condivisione, bacheca_creatore_id, utente_condiviso_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?) returning id";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setString(1, todoCondiviso.getTitolo());
            stmt.setDate(2, java.sql.Date.valueOf(todoCondiviso.getDataScadenza()));
            stmt.setInt(3, todoCondiviso.getBachecaId());
            stmt.setBoolean(4, todoCondiviso.isCompletato());
            stmt.setInt(5, todoCondiviso.getUtenteCreatoreId());
            stmt.setInt(6, todoCondiviso.getUltimoModificatoreId());
            stmt.setDate(7, java.sql.Date.valueOf(todoCondiviso.getDataCondivisione()));
            stmt.setInt(8, todoCondiviso.getBachecaOriginaleId());
            stmt.setInt(9, todoCondiviso.getUtenteCondivisoId());

            var rs = stmt.executeQuery();
            if (rs.next()) {
                todoCondiviso.setId(rs.getInt("id"));
            }
            return todoCondiviso;
        } catch (SQLException e) {
            throw new RuntimeException("errore inserimento todo condiviso", e);
        }
    }

    @Override
    public ToDoCondiviso findById(int id) {
        String sql = "SELECT * FROM todos_condivisi WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return new ToDoCondiviso(
                        rs.getInt("id"),
                        rs.getString("titolo"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getBoolean("completato"),
                        rs.getInt("bacheca_id"),
                        rs.getInt("utente_condivisore_id"),
                        rs.getInt("utnete_condiviso_id"),
                        rs.getInt("ultimo_modificatore_id"),
                        rs.getDate("data_condivisione").toLocalDate(),
                        rs.getInt("bacheca_creatore_id")
                );

            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca todo condiviso per id", e);
        }
    }

    @Override
    public List<ToDoCondiviso> findByBachecaID(int bachecaId) {
        String sql = "SELECT * FROM todos_condivisi WHERE bacheca_id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, bachecaId);
            var rs = stmt.executeQuery();
            List<ToDoCondiviso> todosCondivisi = new java.util.ArrayList<>();
            while (rs.next()) {
                ToDoCondiviso todoCondiviso = new ToDoCondiviso(
                        rs.getInt("id"),
                        rs.getString("titolo"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getBoolean("completato"),
                        rs.getInt("bacheca_id"),
                        rs.getInt("utente_condivisore_id"),
                        rs.getInt("utnete_condiviso_id"),
                        rs.getInt("ultimo_modificatore_id"),
                        rs.getDate("data_condivisione").toLocalDate(),
                        rs.getInt("bacheca_creatore_id")
                );
                todosCondivisi.add(todoCondiviso);
            }
            return todosCondivisi;
        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca todos condivisi per bacheca id", e);
        }
    }

    @Override
    public List<ToDoCondiviso> findByUtenteCondivisoID(int utenteId) {
        String sql = "SELECT * FROM todos_condivisi WHERE utente_condiviso_id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, utenteId);
            var rs = stmt.executeQuery();
            List<ToDoCondiviso> todosCondivisi = new java.util.ArrayList<>();
            while (rs.next()) {
                ToDoCondiviso todoCondiviso = new ToDoCondiviso(
                        rs.getInt("id"),
                        rs.getString("titolo"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getBoolean("completato"),
                        rs.getInt("bacheca_id"),
                        rs.getInt("utente_condivisore_id"),
                        rs.getInt("utnete_condiviso_id"),
                        rs.getInt("ultimo_modificatore_id"),
                        rs.getDate("data_condivisione").toLocalDate(),
                        rs.getInt("bacheca_creatore_id")
                );
                todosCondivisi.add(todoCondiviso);
            }
            return todosCondivisi;
        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca todos condivisi per utente condiviso id", e);
        }
    }

    @Override
    public void update(ToDoCondiviso todoCondiviso) {
        String sql = "UPDATE todos_condivisi SET titolo = ?, data_scadenza = ?, bacheca_id = ?, completato = ?, utente_condiviso_id = ?, ultimo_modificatore_id = ?, data_condivisione = ? WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setString(1, todoCondiviso.getTitolo());
            stmt.setDate(2, java.sql.Date.valueOf(todoCondiviso.getDataScadenza()));
            stmt.setInt(3, todoCondiviso.getBachecaId());
            stmt.setBoolean(4, todoCondiviso.isCompletato());
            stmt.setInt(5, todoCondiviso.getUtenteCreatoreId());
            stmt.setInt(6, todoCondiviso.getUltimoModificatoreId());
            stmt.setDate(7, java.sql.Date.valueOf(todoCondiviso.getDataCondivisione()));
            stmt.setInt(8, todoCondiviso.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("errore aggiornamento todo condiviso", e);
        }
    }
    public List<ToDoCondiviso> findByBachecaCreatoreId(int bachecaCreatoreId) {
        String sql = "SELECT * FROM todos_condivisi WHERE bacheca_creatore_id = ?";
        List<ToDoCondiviso> todosCondivisi = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bachecaCreatoreId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ToDoCondiviso todoCondiviso = new ToDoCondiviso(
                        rs.getInt("id"),
                        rs.getString("titolo"),
                        rs.getDate("data_scadenza") != null ? rs.getDate("data_scadenza").toLocalDate() : null,
                        rs.getBoolean("completato"),
                        rs.getInt("bacheca_id"),
                        rs.getInt("utente_condivisore_id"),
                        rs.getInt("utnete_condiviso_id"),
                        rs.getInt("ultimo_modificatore_id"),
                        rs.getDate("data_condivisione") != null ? rs.getDate("data_condivisione").toLocalDate() : null,
                        rs.getInt("bacheca_creatore_id")
                );
                todosCondivisi.add(todoCondiviso);
            }
        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca todos condivisi per creatore", e);
        }
        return todosCondivisi;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM todos_condivisi WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("errore eliminazione todo condiviso", e);
        }
    }
}
