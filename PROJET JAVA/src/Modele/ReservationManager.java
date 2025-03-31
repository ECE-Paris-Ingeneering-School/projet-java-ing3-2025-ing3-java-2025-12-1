package Modele;

import java.util.ArrayList;
import java.util.List;
import Controleur.*;

public class ReservationManager {
    private List<Client> clients = new ArrayList<>();
    private List<Attraction> attractions = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<Facture> factures = new ArrayList<>();

    // Gestion des Clients
    public Client ajouterClient(String nom, String prenom, String email, int age, TypeClient typeClient, CategorieClient categorie) {
        Client client = new Client(nom, prenom, email, age, typeClient, categorie);
        clients.add(client);
        return client;
    }

    public Client rechercherClientParId(int id) {
        for (Client c : clients) {
            if (c.getIdClient() == id) {
                return c;
            }
        }
        return null;
    }

    // Gestion des Attractions
    public Attraction ajouterAttraction(String nom, String description, int capacite, double prix) {
        Attraction a = new Attraction(nom, description, capacite, prix);
        attractions.add(a);
        return a;
    }

    public Attraction rechercherAttractionParId(int id) {
        for (Attraction a : attractions) {
            if (a.getIdAttraction() == id) {
                return a;
            }
        }
        return null;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    // Gestion des Réservations
    public Reservation reserver(Client client, Attraction attraction, int nbBillets) {
        double prixDeBase = attraction.getPrixUnitaire() * nbBillets;
        double reduction = DiscountService.calculerReduction(client, attraction, nbBillets);
        double prixTotal = prixDeBase - reduction;

        Reservation reservation = new Reservation(client, attraction, nbBillets);
        reservation.setReductionAppliquee(reduction);
        reservation.setPrixTotal(prixTotal);

        // Incrémenter le nombre de réservations du client (sauf pour les invités)
        if (client.getTypeClient() != TypeClient.INVITE) {
            client.incrementerReservations();
        }

        reservations.add(reservation);
        return reservation;
    }

    // Gestion des Factures
    public Facture genererFacture(Reservation reservation) {
        Facture facture = new Facture(reservation);
        factures.add(facture);
        return facture;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<Facture> getFactures() {
        return factures;
    }
}

