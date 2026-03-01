package org.example.database;
import org.example.dao.bachecadao.BachecaDAOImpl;
import org.example.dao.checklistitemdao.ChecklistItemDAOImpl;
import org.example.dao.todocondivisodao.ToDoCondivisoDAOImpl;
import org.example.dao.tododao.ToDoDAOImpl;
import org.example.dao.utentedao.UtenteDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Database connection.
 */
public class DatabaseConnection {
    private DatabaseConnection(){}
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Ciaosono1";
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
