package org.example.database;
import org.example.database.BachecaDAO.BachecaDAOImpl;
import org.example.database.ChecklistitemDAO.ChecklistItemDAOImpl;
import org.example.database.ToDoCondivisoDAO.ToDoCondivisoDAOImpl;
import org.example.database.ToDoDAO.ToDoDAOImpl;
import org.example.database.UtenteDAO.UtenteDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";
    public static final BachecaDAOImpl bachecaDB = new BachecaDAOImpl();
    public static final ToDoDAOImpl todoDB = new ToDoDAOImpl();
    public static final ToDoCondivisoDAOImpl todoCondivisoDB = new ToDoCondivisoDAOImpl();
    public static final ChecklistItemDAOImpl checklistItemDB = new ChecklistItemDAOImpl();
    public static final UtenteDAOImpl utenteDB = new UtenteDAOImpl();
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
