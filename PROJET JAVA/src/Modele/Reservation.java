package Modele;

import java.time.LocalDate;


public class Reservation {
    private int idReservation; // mis à jour après insertion
    private LocalDate dateReservation;
    private int nombreBillets;
    private double prixTotal;
    private double reductionAppliquee;
    private Client client;
    private Attraction attraction;

    public Reservation(Client client, Attraction attraction, int nombreBillets) {
        this.idReservation = 0;
        this.client = client;
        this.attraction = attraction;
        this.nombreBillets = nombreBillets;
        this.dateReservation = LocalDate.now();
    }

    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }
    public LocalDate getDateReservation() { return dateReservation; }
    public int getNombreBillets() { return nombreBillets; }
    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }
    public double getReductionAppliquee() { return reductionAppliquee; }
    public void setReductionAppliquee(double reductionAppliquee) { this.reductionAppliquee = reductionAppliquee; }
    public Client getClient() { return client; }
    public Attraction getAttraction() { return attraction; }

    @Override
    public String toString() {
        return "Reservation #" + idReservation + " pour " + client.getNom() + " " + client.getPrenom() +
                " - Attraction: " + attraction.getNom() + ", Billets: " + nombreBillets +
                ", Date: " + dateReservation + ", Réduction: " + reductionAppliquee + "€, Total: " + prixTotal + "€";
    }
}

