package org.example.database.BachecaDAO;
import org.example.model.Bacheca;

import java.util.ArrayList;

//
public interface BachecaDAO {
    Bacheca save(Bacheca bacheca);
    Bacheca findById(int id);
    ArrayList<Bacheca> findByUtenteId(int utenteId);
    void update(Bacheca bacheca);
    void delete(int id);
}
