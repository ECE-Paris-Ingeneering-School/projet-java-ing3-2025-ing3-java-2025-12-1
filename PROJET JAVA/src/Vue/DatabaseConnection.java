package Vue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    // Adaptez l'URL, le nom de la base, le port, etc.
    private static final String URL = "jdbc:mysql://localhost:3306/attractions";
    private static final String USER = "root";
    // Remplacez "votre_mot_de_passe" par le mot de passe réel de votre compte MySQL root, ou laissez vide s'il n'y en a pas.
    private static final String PASSWORD = "";

    // Retourne une nouvelle connexion à chaque appel
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

