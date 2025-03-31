package Controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Modele.*;
import Vue.*;

public class FactureDAO {

    public void addFacture(Facture facture) {
        String sql = "INSERT INTO facture (dateFacture, montantTotal, reservation_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, java.sql.Date.valueOf(facture.getDateFacture()));
            stmt.setDouble(2, facture.getMontantTotal());
            stmt.setInt(3, facture.getReservation().getIdReservation());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                facture.setIdFacture(rs.getInt(1));
            }

            System.out.println("Facture ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

