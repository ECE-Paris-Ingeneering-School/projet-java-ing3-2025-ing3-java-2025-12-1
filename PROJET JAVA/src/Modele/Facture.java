package Modele;

import java.time.LocalDate;


public class Facture {
    private int idFacture; // mis à jour après insertion
    private LocalDate dateFacture;
    private double montantTotal;
    private Reservation reservation;

    public Facture(Reservation reservation) {
        this.idFacture = 0;
        this.dateFacture = LocalDate.now();
        this.reservation = reservation;
        this.montantTotal = reservation.getPrixTotal();
    }

    public int getIdFacture() { return idFacture; }
    public void setIdFacture(int idFacture) { this.idFacture = idFacture; }
    public LocalDate getDateFacture() { return dateFacture; }
    public double getMontantTotal() { return montantTotal; }
    public Reservation getReservation() { return reservation; }

    @Override
    public String toString() {
        return "Facture #" + idFacture + " - Date: " + dateFacture + ", Montant: " + montantTotal + "€, Pour la réservation #" + reservation.getIdReservation();
    }
}

