package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Paramètres de connexion
    private static final String URL = "jdbc:postgresql://localhost:5432/gestion_prets";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456789"; 

    // Méthode pour obtenir une connexion
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL introuvable !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données !");
            e.printStackTrace();
        }

        return connection;
    }
}
