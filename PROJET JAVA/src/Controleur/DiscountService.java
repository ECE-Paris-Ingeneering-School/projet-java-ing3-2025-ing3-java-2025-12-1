package Controleur;
import Modele.*;

public class DiscountService{
    public static double calculerReduction(Client client, Attraction attraction, int nombreBillets) {
        double prixDeBase = attraction.getPrixUnitaire() * nombreBillets;
        double reduction = 0.0;

        // Réduction liée à la catégorie d'âge
        switch(client.getCategorieClient()) {
            case ENFANT:
                reduction += prixDeBase * 0.30;
                break;
            case SENIOR:
                reduction += prixDeBase * 0.20;
                break;
            case REGULIER:
                break;
        }

        // Réduction de fidélité pour les anciens clients (par exemple, si nombreReservationsPrecedentes > 5)
        if(client.getTypeClient() == TypeClient.ANCIEN && client.getNombreReservationsPrecedentes() > 5) {
            reduction += prixDeBase * 0.10;
        }
        return reduction;
    }
}
