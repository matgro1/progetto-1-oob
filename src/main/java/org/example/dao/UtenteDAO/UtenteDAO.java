package org.example.dao.UtenteDAO;
import org.example.database.DatabaseConnection;
import org.example.model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Utente dao.
 */
public interface UtenteDAO {
    /**
     * Save utente.
     *
     * @param utente the utente
     * @return the utente
     */
    Utente save(Utente utente);

    /**
     * Update.
     *
     * @param utente the utente
     */
    void update(Utente utente);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(int id);

    /**
     * Find all array list.
     *
     * @return the array list
     */
    static ArrayList<Utente> findAll(){
        String sql = "SELECT * FROM utenti";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            ArrayList<Utente> utenti = new ArrayList<>();
            while (rs.next()) {
                Utente utente = new Utente(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password")
                );
                utenti.add(utente);
            }
            return utenti;
        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca tutti gli utenti", e);
        }
    }

    /**
     * Get name by id string.
     *
     * @param id the id
     * @return the string
     */
    static String getNameById(int id){
        String sql = "SELECT login FROM utenti WHERE id = ?";
        try(Connection conn= DatabaseConnection.getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("login");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("errore ricerca nome utente per id", e);
        }
    }
}
