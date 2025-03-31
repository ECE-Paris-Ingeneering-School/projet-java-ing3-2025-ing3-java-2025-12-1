package Controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Modele.*;
import Vue.*;

public class ReservationDAO {

    public void addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (dateReservation, nombreBillets, prixTotal, reductionAppliquee, client_id, attraction_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, java.sql.Date.valueOf(reservation.getDateReservation()));
            stmt.setInt(2, reservation.getNombreBillets());
            stmt.setDouble(3, reservation.getPrixTotal());
            stmt.setDouble(4, reservation.getReductionAppliquee());
            stmt.setInt(5, reservation.getClient().getIdClient());
            stmt.setInt(6, reservation.getAttraction().getIdAttraction());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                reservation.setIdReservation(rs.getInt(1));
            }

            System.out.println("Réservation ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
