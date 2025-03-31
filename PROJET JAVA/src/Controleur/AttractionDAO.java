package Controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Vue.*;
import Modele.*;

public class AttractionDAO {

    public void addAttraction(Attraction attraction) {
        String sql = "INSERT INTO attraction (nom, description, capacite, prixUnitaire) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, attraction.getNom());
            stmt.setString(2, attraction.getDescription());
            stmt.setInt(3, attraction.getCapacite());
            stmt.setDouble(4, attraction.getPrixUnitaire());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                attraction.setIdAttraction(rs.getInt(1));
            }

            System.out.println("Attraction ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Attraction getAttractionById(int id) {
        Attraction attraction = null;
        String sql = "SELECT * FROM attraction WHERE idAttraction = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                int capacite = rs.getInt("capacite");
                double prixUnitaire = rs.getDouble("prixUnitaire");
                attraction = new Attraction(nom, description, capacite, prixUnitaire);
                attraction.setIdAttraction(rs.getInt("idAttraction"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attraction;
    }

    public List<Attraction> getAllAttractions() {
        List<Attraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM attraction";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                int capacite = rs.getInt("capacite");
                double prixUnitaire = rs.getDouble("prixUnitaire");
                Attraction attraction = new Attraction(nom, description, capacite, prixUnitaire);
                attraction.setIdAttraction(rs.getInt("idAttraction"));
                attractions.add(attraction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attractions;
    }

    public void updateAttraction(Attraction attraction) {
        String sql = "UPDATE attraction SET nom = ?, description = ?, capacite = ?, prixUnitaire = ? WHERE idAttraction = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, attraction.getNom());
            stmt.setString(2, attraction.getDescription());
            stmt.setInt(3, attraction.getCapacite());
            stmt.setDouble(4, attraction.getPrixUnitaire());
            stmt.setInt(5, attraction.getIdAttraction());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Attraction mise à jour avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAttraction(int id) {
        String sql = "DELETE FROM attraction WHERE idAttraction = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Attraction supprimée avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retourne une liste de chaînes indiquant le nom de l'attraction et le nombre de réservations
    public List<String> getPopularAttractions() {
        List<String> popular = new ArrayList<>();
        String sql = "SELECT a.nom, COUNT(r.idReservation) AS nbReservations " +
                "FROM attraction a LEFT JOIN reservation r ON a.idAttraction = r.attraction_id " +
                "GROUP BY a.idAttraction, a.nom " +
                "ORDER BY nbReservations DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nom = rs.getString("nom");
                int nb = rs.getInt("nbReservations");
                popular.add(nom + " - Réservations: " + nb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return popular;
    }
}
