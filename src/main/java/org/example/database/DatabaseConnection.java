package org.example.database;
import org.example.dao.BachecaDAO.BachecaDAOImpl;
import org.example.dao.ChecklistitemDAO.ChecklistItemDAOImpl;
import org.example.dao.ToDoCondivisoDAO.ToDoCondivisoDAOImpl;
import org.example.dao.ToDoDAO.ToDoDAOImpl;
import org.example.dao.UtenteDAO.UtenteDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Database connection.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";
    /**
     * The constant bachecaDB.
     */
    public static final BachecaDAOImpl bachecaDB = new BachecaDAOImpl();
    /**
     * The constant todoDB.
     */
    public static final ToDoDAOImpl todoDB = new ToDoDAOImpl();
    /**
     * The constant todoCondivisoDB.
     */
    public static final ToDoCondivisoDAOImpl todoCondivisoDB = new ToDoCondivisoDAOImpl();
    /**
     * The constant checklistItemDB.
     */
    public static final ChecklistItemDAOImpl checklistItemDB = new ChecklistItemDAOImpl();
    /**
     * The constant utenteDB.
     */
    public static final UtenteDAOImpl utenteDB = new UtenteDAOImpl();

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
