package org.example.dao.todocondivisodao;

import org.example.database.DatabaseConnection;
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
 * The type To do condiviso dao.
 */
public class ToDoCondivisoDAOImpl implements ToDoCondivisoDAO {

    private static final Logger LOGGER = Logger.getLogger(ToDoCondivisoDAOImpl.class.getName());

    // Costanti per evitare la duplicazione delle stringhe e l'uso di SELECT *
    private static final String COL_ID = "id";
    private static final String COL_TITOLO = "titolo";
    private static final String COL_DATA_SCADENZA = "data_scadenza";
    private static final String COL_COMPLETATO = "completato";
    private static final String COL_BACHECA_ID = "bacheca_id";
    private static final String COL_UTENTE_CONDIVISORE = "utente_condivisore_id";
    private static final String COL_UTENTE_CONDIVISO = "utente_condiviso_id";
    private static final String COL_ULTIMO_MOD = "ultimo_modificatore_id";
    private static final String COL_DATA_CONDIVISIONE = "data_condivisione";
    private static final String COL_BACHECA_CREATORE = "bacheca_creatore_id";

    private static final String SELECT_COLUMNS = "SELECT id, titolo, data_scadenza, completato, bacheca_id, utente_condivisore_id, utente_condiviso_id, ultimo_modificatore_id, data_condivisione, bacheca_creatore_id FROM todos_condivisi ";

    @Override
    public ToDoCondiviso save(ToDoCondiviso todoCondiviso) {
        String sql = "INSERT INTO todos_condivisi (titolo, data_scadenza, bacheca_id, completato, utente_condivisore_id, ultimo_modificatore_id, data_condivisione, bacheca_creatore_id, utente_condiviso_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, todoCondiviso.getTitolo());
            stmt.setDate(2, todoCondiviso.getDataScadenza() != null ? java.sql.Date.valueOf(todoCondiviso.getDataScadenza()) : null);
            stmt.setInt(3, todoCondiviso.getBachecaId());
            stmt.setBoolean(4, todoCondiviso.isCompletato());
            stmt.setInt(5, todoCondiviso.getUtenteCreatoreId());
            stmt.setInt(6, todoCondiviso.getUltimoModificatoreId());
            stmt.setDate(7, todoCondiviso.getDataCondivisione() != null ? java.sql.Date.valueOf(todoCondiviso.getDataCondivisione()) : null);
            stmt.setInt(8, todoCondiviso.getBachecaOriginaleId());
            stmt.setInt(9, todoCondiviso.getUtenteCondivisoId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                todoCondiviso.setId(rs.getInt(COL_ID));
            }
            return todoCondiviso;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore inserimento todo condiviso", e);
            return null;
        }
    }

    @Override
    public ToDoCondiviso findById(int id) {
        String sql = SELECT_COLUMNS + "WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToToDoCondiviso(rs);
            }
            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore ricerca todo condiviso per id", e);
            return null;
        }
    }

    @Override
    public List<ToDoCondiviso> findByBachecaID(int bachecaId) {
        String sql = SELECT_COLUMNS + "WHERE bacheca_id = ?";
        return executeQueryToList(sql, bachecaId, "errore ricerca todos condivisi per bacheca id");
    }

    @Override
    public List<ToDoCondiviso> findByUtenteCondivisoID(int utenteId) {
        String sql = SELECT_COLUMNS + "WHERE utente_condiviso_id = ?";
        return executeQueryToList(sql, utenteId, "errore ricerca todos condivisi per utente condiviso id");
    }

    @Override
    public List<ToDoCondiviso> findByBachecaCreatoreId(int bachecaCreatoreId) {
        String sql = SELECT_COLUMNS + "WHERE bacheca_creatore_id = ?";
        return executeQueryToList(sql, bachecaCreatoreId, "errore ricerca todos condivisi per creatore");
    }

    @Override
    public void update(ToDoCondiviso todoCondiviso) {
        String sql = "UPDATE todos_condivisi SET titolo = ?, data_scadenza = ?, bacheca_id = ?, completato = ?, utente_condiviso_id = ?, ultimo_modificatore_id = ?, data_condivisione = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, todoCondiviso.getTitolo());
            stmt.setDate(2, todoCondiviso.getDataScadenza() != null ? java.sql.Date.valueOf(todoCondiviso.getDataScadenza()) : null);
            stmt.setInt(3, todoCondiviso.getBachecaId());
            stmt.setBoolean(4, todoCondiviso.isCompletato());
            stmt.setInt(5, todoCondiviso.getUtenteCondivisoId());
            stmt.setInt(6, todoCondiviso.getUltimoModificatoreId());
            stmt.setDate(7, todoCondiviso.getDataCondivisione() != null ? java.sql.Date.valueOf(todoCondiviso.getDataCondivisione()) : null);
            stmt.setInt(8, todoCondiviso.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore aggiornamento todo condiviso", e);
        }
    }

    @Override
    public void delete(int id) {
        String sqlChecklist = "DELETE FROM checklist_items WHERE todo_id = ?";
        String sqlToDo = "DELETE FROM todos_condivisi WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sqlChecklist)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(sqlToDo)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "errore eliminazione todo condiviso e checklist", e);
        }
    }


    private List<ToDoCondiviso> executeQueryToList(String sql, int param, String errorMessage) {
        List<ToDoCondiviso> todosCondivisi = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, param);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                todosCondivisi.add(mapResultSetToToDoCondiviso(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, errorMessage, e);
        }
        return todosCondivisi;
    }

    private ToDoCondiviso mapResultSetToToDoCondiviso(ResultSet rs) throws SQLException {
        return new ToDoCondiviso(
                rs.getInt(COL_ID),
                rs.getString(COL_TITOLO),
                rs.getDate(COL_DATA_SCADENZA) != null ? rs.getDate(COL_DATA_SCADENZA).toLocalDate() : null,
                rs.getBoolean(COL_COMPLETATO),
                rs.getInt(COL_BACHECA_ID),
                rs.getInt(COL_UTENTE_CONDIVISORE),
                rs.getInt(COL_UTENTE_CONDIVISO),
                rs.getInt(COL_ULTIMO_MOD),
                rs.getDate(COL_DATA_CONDIVISIONE) != null ? rs.getDate(COL_DATA_CONDIVISIONE).toLocalDate() : null,
                rs.getInt(COL_BACHECA_CREATORE)
        );
    }
}