package org.example.dao.UtenteDAO;

import org.example.database.DatabaseConnection;
import org.example.model.Bacheca;
import org.example.model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Utente dao.
 */
public class UtenteDAOImpl implements UtenteDAO {
    @Override
    public Utente save(Utente utente) {
        String sql = "INSERT INTO utenti (login, password) VALUES (?, ?) returning id";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setString(1, utente.getLogin());
            stmt.setString(2, utente.getPassword());
            var rs = stmt.executeQuery();
            if (rs.next()) {
                utente.setId(rs.getInt("id"));
            }
            return utente;
        } catch (SQLException e) {
            throw new RuntimeException("errore inserimento utente", e);
        }
    }



    @Override
    public void delete(int id) {
        List<Bacheca> bacheche = DatabaseConnection.bachecaDB.findByUtenteId(id);

        try {
            for (Bacheca b : bacheche) {
                DatabaseConnection.bachecaDB.delete(b.getId());
            }

            String sql = "DELETE FROM utenti WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("errore cancellazione utente e dati associati", e);
        }
    }

    @Override
    public void update(Utente utente) {
        String sql = "UPDATE utenti SET login = ?, password = ? WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setString(1, utente.getLogin());
            stmt.setString(2, utente.getPassword());
            stmt.setInt(3, utente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("errore aggiornamento utente", e);
        }
    }
}
