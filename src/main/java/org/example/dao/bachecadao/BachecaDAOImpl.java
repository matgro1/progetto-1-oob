package org.example.dao.bachecadao;

import org.example.database.DatabaseConnection;
import org.example.model.Bacheca;
import org.example.model.ToDo;
import org.example.model.ToDoCondiviso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Bacheca dao.
 */
public class BachecaDAOImpl implements BachecaDAO {

    private static final Logger LOGGER = Logger.getLogger(BachecaDAOImpl.class.getName());

    @Override
    public Bacheca save(Bacheca bacheca) {
        String sql = "INSERT INTO bacheche (nome, utente_id, descrizione) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bacheca.getTitolo());
            stmt.setInt(2, bacheca.getUtenteId());
            stmt.setString(3, bacheca.getDescrizione());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bacheca.setId(rs.getInt("id"));
            }
            return bacheca;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore inserimento bacheca", e);
            return null;
        }
    }



    @Override
    public ArrayList<Bacheca> findByUtenteId(int utenteId) {
        String sql = "SELECT id, nome, descrizione, utente_id FROM bacheche WHERE utente_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, utenteId);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Bacheca> bacheche = new ArrayList<>();
            while (rs.next()) {
                Bacheca bacheca = new Bacheca(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getInt("utente_id")
                );
                bacheche.add(bacheca);
            }
            return bacheche;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore ricerca bacheche per utente id", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void update(Bacheca bacheca) {
        String sql = "UPDATE bacheche SET nome = ?, descrizione = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bacheca.getTitolo());
            stmt.setString(2, bacheca.getDescrizione());
            stmt.setInt(3, bacheca.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore aggiornamento bacheca", e);
        }
    }

    @Override
    public void delete(int id) {
        ArrayList<ToDo> todos = DatabaseConnection.todoDB.findByBachecaId(id);
        List<ToDoCondiviso> sharedTodos = DatabaseConnection.todoCondivisoDB.findByBachecaID(id);

        try {
            for (ToDo t : todos) {
                DatabaseConnection.todoDB.delete(t.getId());
            }
            for (ToDoCondiviso t : sharedTodos) {
                DatabaseConnection.todoCondivisoDB.delete(t.getId());
            }

            String sql = "DELETE FROM bacheche WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "errore cancellazione bacheca e contenuti", e);
        }
    }
}