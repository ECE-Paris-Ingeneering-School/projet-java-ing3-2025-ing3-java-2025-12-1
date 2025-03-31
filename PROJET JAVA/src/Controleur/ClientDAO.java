package Controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Modele.*;
import Vue.*;

public class ClientDAO {

    public void addClient(Client client) {
        String sql = "INSERT INTO client (nom, prenom, email, age, typeClient, categorieClient, nombreReservationsPrecedentes, login, motDePasse) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setInt(4, client.getAge());
            stmt.setString(5, client.getTypeClient().name());
            stmt.setString(6, client.getCategorieClient().name());
            stmt.setInt(7, client.getNombreReservationsPrecedentes());
            stmt.setString(8, client.getLogin());
            stmt.setString(9, client.getMotDePasse());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                client.setIdClient(rs.getInt(1));
            }

            System.out.println("Client ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getClientById(int id) {
        Client client = null;
        String sql = "SELECT * FROM client WHERE idClient = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                TypeClient type = TypeClient.valueOf(rs.getString("typeClient"));
                CategorieClient categorie = CategorieClient.valueOf(rs.getString("categorieClient"));
                String login = rs.getString("login");
                String motDePasse = rs.getString("motDePasse");
                client = new Client(nom, prenom, email, age, type, categorie, login, motDePasse);
                client.setIdClient(rs.getInt("idClient"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public Client getClientByLogin(String login, String motDePasse) {
        Client client = null;
        String sql = "SELECT * FROM client WHERE login = ? AND motDePasse = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                TypeClient type = TypeClient.valueOf(rs.getString("typeClient"));
                CategorieClient categorie = CategorieClient.valueOf(rs.getString("categorieClient"));
                client = new Client(nom, prenom, email, age, type, categorie, login, motDePasse);
                client.setIdClient(rs.getInt("idClient"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
}

