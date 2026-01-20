package org.example.database.UtenteDAO;
import org.example.model.Utente;

public interface UtenteDAO {
    Utente save(Utente utente);
    Utente findById(int id);
    Utente findByUsername(String login);
    void update(Utente utente);
    void delete(int id);
}
