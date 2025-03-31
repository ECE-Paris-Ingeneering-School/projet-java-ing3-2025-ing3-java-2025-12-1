package Modele;


public class Attraction {
    private int idAttraction; // mis à jour après insertion
    private String nom;
    private String description;
    private int capacite;
    private double prixUnitaire;

    public Attraction(String nom, String description, int capacite, double prixUnitaire) {
        this.idAttraction = 0;
        this.nom = nom;
        this.description = description;
        this.capacite = capacite;
        this.prixUnitaire = prixUnitaire;
    }

    public int getIdAttraction() { return idAttraction; }
    public void setIdAttraction(int idAttraction) { this.idAttraction = idAttraction; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public int getCapacite() { return capacite; }
    public double getPrixUnitaire() { return prixUnitaire; }

    @Override
    public String toString() {
        return "Modele.Attraction #" + idAttraction + " - " + nom + " [Prix: " + prixUnitaire + "€, Capacité: " + capacite + "]";
    }
}
