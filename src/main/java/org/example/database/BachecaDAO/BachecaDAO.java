package org.example.database.BachecaDAO;
import org.example.model.Bacheca;
import java.util.List;

public interface BachecaDAO {
    Bacheca save(Bacheca bacheca);
    Bacheca findById(int id);
    List<Bacheca> findByUtenteId(int utenteId);
    void update(Bacheca bacheca);
    void delete(int id);
}
