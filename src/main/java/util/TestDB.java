package util;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        Connection c = DBConnection.getConnection();
        if (c != null) {
            System.out.println("Connexion réussie à PostgreSQL !");
        } else {
            System.out.println("Échec de la connexion.");
        }
    }
}
