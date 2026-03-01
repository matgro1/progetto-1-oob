package org.example.dao.bachecadao;
import org.example.model.Bacheca;

import java.util.ArrayList;

/**
 * The interface Bacheca dao.
 */
//
public interface BachecaDAO {
    /**
     * Save bacheca.
     *
     * @param bacheca the bacheca
     * @return the bacheca
     */
    Bacheca save(Bacheca bacheca);



    /**
     * Find by utente id array list.
     *
     * @param utenteId the utente id
     * @return the array list
     */
    ArrayList<Bacheca> findByUtenteId(int utenteId);

    /**
     * Update.
     *
     * @param bacheca the bacheca
     */
    void update(Bacheca bacheca);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(int id);
}
