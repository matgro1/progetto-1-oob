package org.example.database.BachecaDAO;

import org.example.database.DatabaseConnection;
import org.example.model.Bacheca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BachecaDAOImpl implements BachecaDAO{
    @Override
    public Bacheca save(Bacheca bacheca) {
        String sql = "INSERT INTO bacheche (nome, utente_id, descrizione) VALUES (?, ?, ?) RETURNING id";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setString(1, bacheca.getTitolo());
            stmt.setInt(2, bacheca.getUtenteId());
            stmt.setString(3, bacheca.getDescrizione());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bacheca.setId(rs.getInt("id"));
            }
            return bacheca;
        } catch (SQLException e) {
            throw new RuntimeException("errore inserimento bacheca", e);
        }
    }

    @Override
    public Bacheca findById(int id) {
        String sql = "SELECT * FROM bacheche WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Bacheca(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getInt("utente_id")
                );

            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca bacheca per id", e);
        }
    }

    @Override
    public List<Bacheca> findByUtenteId(int utenteId) {
        String sql = "SELECT * FROM bacheche WHERE utente_id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, utenteId);
            ResultSet rs = stmt.executeQuery();
            List<Bacheca> bacheche = new ArrayList<>();
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
            throw new RuntimeException("errore ricerca bacheche per utente id", e);
        }
    }

    @Override
    public void update(Bacheca bacheca) {
        String sql = "UPDATE bacheche SET nome = ?, descrizione = ? WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setString(1, bacheca.getTitolo());
            stmt.setString(2, bacheca.getDescrizione());
            stmt.setInt(3, bacheca.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("errore aggiornamento bacheca", e);
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM bacheche WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("errore cancellazione bacheca", e);
        }

    }
}
