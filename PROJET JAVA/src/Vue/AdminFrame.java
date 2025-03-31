package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Controleur.*;
import Modele.*;

public class AdminFrame extends JFrame {
    private AttractionDAO attractionDAO = new AttractionDAO();
    private ClientDAO clientDAO = new ClientDAO();

    private JTabbedPane tabbedPane;

    // Composants pour l'onglet Attractions
    private JTextArea txtAttractions;
    private JTextField txtNom, txtDescription, txtCapacite, txtPrix, txtIdAttraction;
    private JButton btnAddAttraction, btnUpdateAttraction, btnDeleteAttraction, btnRefreshAttractions;

    // Composants pour l'onglet Clients/Rapports
    private JTextArea txtClients;
    private JButton btnRefreshClients, btnShowPopular;

    private JButton btnLogout;

    public AdminFrame() {
        setTitle("Interface Administrateur");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Attractions", createAttractionsPanel());
        tabbedPane.addTab("Clients & Rapports", createClientsRapportsPanel());

        btnLogout = new JButton("Déconnexion");
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        add(tabbedPane, BorderLayout.CENTER);
        add(btnLogout, BorderLayout.SOUTH);
    }

    private JPanel createAttractionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));

        // Panneau formulaire pour ajouter / modifier / supprimer une attraction
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Gestion des Attractions"));

        panelForm.add(new JLabel("ID (pour update/delete):"));
        txtIdAttraction = new JTextField();
        panelForm.add(txtIdAttraction);

        panelForm.add(new JLabel("Nom :"));
        txtNom = new JTextField();
        panelForm.add(txtNom);

        panelForm.add(new JLabel("Description :"));
        txtDescription = new JTextField();
        panelForm.add(txtDescription);

        panelForm.add(new JLabel("Capacité :"));
        txtCapacite = new JTextField();
        panelForm.add(txtCapacite);

        panelForm.add(new JLabel("Prix unitaire :"));
        txtPrix = new JTextField();
        panelForm.add(txtPrix);

        btnAddAttraction = new JButton("Ajouter");
        btnUpdateAttraction = new JButton("Mettre à jour");
        btnDeleteAttraction = new JButton("Supprimer");
        JPanel panelButtons = new JPanel();
        panelButtons.add(btnAddAttraction);
        panelButtons.add(btnUpdateAttraction);
        panelButtons.add(btnDeleteAttraction);

        panelForm.add(panelButtons);

        // Action des boutons
        btnAddAttraction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterAttraction();
            }
        });

        btnUpdateAttraction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mettreAJourAttraction();
            }
        });

        btnDeleteAttraction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerAttraction();
            }
        });

        // Zone d'affichage des attractions
        txtAttractions = new JTextArea();
        txtAttractions.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAttractions);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des attractions"));

        btnRefreshAttractions = new JButton("Rafraîchir");
        btnRefreshAttractions.addActionListener(e -> afficherAttractions());

        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.add(panelForm, BorderLayout.CENTER);
        panelNorth.add(btnRefreshAttractions, BorderLayout.SOUTH);

        panel.add(panelNorth, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        afficherAttractions();
        return panel;
    }

    private JPanel createClientsRapportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createTitledBorder("Clients et Rapports"));

        txtClients = new JTextArea();
        txtClients.setEditable(false);
        JScrollPane scrollClients = new JScrollPane(txtClients);
        scrollClients.setBorder(BorderFactory.createTitledBorder("Liste des clients"));

        btnRefreshClients = new JButton("Rafraîchir Clients");
        btnRefreshClients.addActionListener(e -> afficherClients());

        btnShowPopular = new JButton("Attractions Populaires");
        btnShowPopular.addActionListener(e -> afficherAttractionsPopulaires());

        JPanel panelSouth = new JPanel();
        panelSouth.add(btnRefreshClients);
        panelSouth.add(btnShowPopular);

        panel.add(scrollClients, BorderLayout.CENTER);
        panel.add(panelSouth, BorderLayout.SOUTH);

        afficherClients();
        return panel;
    }

    // Méthode pour ajouter une attraction
    private void ajouterAttraction() {
        try {
            String nom = txtNom.getText();
            String description = txtDescription.getText();
            int capacite = Integer.parseInt(txtCapacite.getText());
            double prixUnitaire = Double.parseDouble(txtPrix.getText());
            Attraction attraction = new Attraction(nom, description, capacite, prixUnitaire);
            attractionDAO.addAttraction(attraction);
            JOptionPane.showMessageDialog(this, "Attraction ajoutée avec succès !");
            afficherAttractions();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erreur de saisie pour la capacité ou le prix", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour mettre à jour une attraction existante
    private void mettreAJourAttraction() {
        try {
            int id = Integer.parseInt(txtIdAttraction.getText());
            String nom = txtNom.getText();
            String description = txtDescription.getText();
            int capacite = Integer.parseInt(txtCapacite.getText());
            double prixUnitaire = Double.parseDouble(txtPrix.getText());
            Attraction attraction = new Attraction(nom, description, capacite, prixUnitaire);
            attraction.setIdAttraction(id);
            attractionDAO.updateAttraction(attraction);
            JOptionPane.showMessageDialog(this, "Attraction mise à jour !");
            afficherAttractions();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un ID valide et les autres champs corrects", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour supprimer une attraction
    private void supprimerAttraction() {
        try {
            int id = Integer.parseInt(txtIdAttraction.getText());
            attractionDAO.deleteAttraction(id);
            JOptionPane.showMessageDialog(this, "Attraction supprimée !");
            afficherAttractions();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un ID valide", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Affichage de la liste des attractions
    private void afficherAttractions() {
        List<Attraction> attractions = attractionDAO.getAllAttractions();
        StringBuilder sb = new StringBuilder();
        for (Attraction a : attractions) {
            sb.append(a).append("\n");
        }
        txtAttractions.setText(sb.toString());
    }

    // Affichage de la liste des clients
    private void afficherClients() {
        // On peut récupérer tous les clients via une requête SQL (ajoutez une méthode getAllClients() dans ClientDAO si nécessaire)
        // Pour cet exemple, nous utiliserons une solution simplifiée
        // Si vous n'avez pas cette méthode, vous pouvez afficher un message ou ajouter la méthode dans ClientDAO
        String sql = "SELECT * FROM client";
        // On va simplement exécuter cette requête et afficher le résultat sous forme de texte
        StringBuilder sb = new StringBuilder();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("idClient");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String login = rs.getString("login");
                sb.append("ID: ").append(id).append(", ").append(nom).append(" ").append(prenom)
                        .append(", login: ").append(login).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtClients.setText(sb.toString());
    }

    // Affichage des attractions les plus populaires
    private void afficherAttractionsPopulaires() {
        List<String> popular = attractionDAO.getPopularAttractions();
        StringBuilder sb = new StringBuilder();
        for (String s : popular) {
            sb.append(s).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Attractions Populaires", JOptionPane.INFORMATION_MESSAGE);
    }
}

