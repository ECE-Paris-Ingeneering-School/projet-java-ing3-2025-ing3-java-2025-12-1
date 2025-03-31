package Modele;


public class Client {
    private int idClient; // mis à jour après insertion
    private String nom;
    private String prenom;
    private String email;
    private int age;
    private TypeClient typeClient;          // NOUVEAU, ANCIEN, INVITE
    private CategorieClient categorieClient; // ENFANT, REGULIER, SENIOR
    private int nombreReservationsPrecedentes;
    // Champs pour l'authentification (utilisés pour les clients membres)
    private String login;
    private String motDePasse;

    // Constructeur pour clients membres (nouveaux et anciens)
    public Client(String nom, String prenom, String email, int age,
                  TypeClient typeClient, CategorieClient categorieClient,
                  String login, String motDePasse) {
        this.idClient = 0;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.age = age;
        this.typeClient = typeClient;
        this.categorieClient = categorieClient;
        this.nombreReservationsPrecedentes = 0;
        this.login = login;
        this.motDePasse = motDePasse;
    }

    // Constructeur pour clients invités (sans identifiants)
    // On fournit ici des valeurs par défaut pour éviter que login et motDePasse soient null.
    public Client(String nom, String prenom, String email, int age,
                  TypeClient typeClient, CategorieClient categorieClient) {
        this(nom, prenom, email, age, typeClient, categorieClient, "guest", "guest");
    }

    // Getters et setters
    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public TypeClient getTypeClient() { return typeClient; }
    public CategorieClient getCategorieClient() { return categorieClient; }
    public int getNombreReservationsPrecedentes() { return nombreReservationsPrecedentes; }
    public void incrementerReservations() { this.nombreReservationsPrecedentes++; }
    public String getLogin() { return login; }
    public String getMotDePasse() { return motDePasse; }

    @Override
    public String toString() {
        return "Client #" + idClient + " - " + nom + " " + prenom
                + " (" + typeClient + ", " + categorieClient + ")";
    }
}

