package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import Modele.*;
import Controleur.*;


public class UserFrame extends JFrame {
    private Client client;
    private AttractionDAO attractionDAO = new AttractionDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();
    private FactureDAO factureDAO = new FactureDAO();
    private ClientDAO clientDAO = new ClientDAO();

    private JTextArea txtAttractions;
    private JTextField txtAttractionId, txtNbBillets;
    private JButton btnReserve, btnRefresh, btnLogout;

    public UserFrame(Client client) {
        this.client = client;
        setTitle("Interface Client - " + (client.getTypeClient() == TypeClient.INVITE ? "Invité" : client.getNom()));
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelTop = new JPanel(new BorderLayout());
        txtAttractions = new JTextArea();
        txtAttractions.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAttractions);
        panelTop.add(scrollPane, BorderLayout.CENTER);

        btnRefresh = new JButton("Rafraîchir les attractions");
        panelTop.add(btnRefresh, BorderLayout.SOUTH);

        JPanel panelBottom = new JPanel(new GridLayout(3, 2, 10, 10));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panelBottom.add(new JLabel("ID Attraction:"));
        txtAttractionId = new JTextField();
        panelBottom.add(txtAttractionId);
        panelBottom.add(new JLabel("Nombre de billets:"));
        txtNbBillets = new JTextField();
        panelBottom.add(txtNbBillets);
        btnReserve = new JButton("Réserver");
        panelBottom.add(btnReserve);
        btnLogout = new JButton("Déconnexion");
        panelBottom.add(btnLogout);

        add(panelTop, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherAttractions();
            }
        });

        btnReserve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effectuerReservation();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        afficherAttractions();
    }

    private void afficherAttractions() {
        List<Attraction> attractions = attractionDAO.getAllAttractions();
        StringBuilder sb = new StringBuilder();
        for (Attraction a : attractions) {
            sb.append(a).append("\n");
        }
        txtAttractions.setText(sb.toString());
    }

    /**
     * Calcule et affiche le prix/réduction, puis crée la réservation + facture
     */
    private void effectuerReservation() {
        try {
            int attractionId = Integer.parseInt(txtAttractionId.getText());
            int nbBillets = Integer.parseInt(txtNbBillets.getText());
            Attraction attraction = attractionDAO.getAttractionById(attractionId);

            if (attraction == null) {
                JOptionPane.showMessageDialog(this, "Attraction introuvable !");
                return;
            }

            // Si client invité => pas de réduction, sinon on calcule via DiscountService
            double reduction = (client.getTypeClient() == TypeClient.INVITE)
                    ? 0
                    : DiscountService.calculerReduction(client, attraction, nbBillets);

            double prixTotal = attraction.getPrixUnitaire() * nbBillets - reduction;

            // Afficher le détail avant la création de la réservation
            String messageDetail =
                    "Prix unitaire : " + attraction.getPrixUnitaire() + " €\n" +
                            "Nombre de billets : " + nbBillets + "\n" +
                            "Réduction appliquée : " + reduction + " €\n" +
                            "Prix total à payer : " + prixTotal + " €";

            JOptionPane.showMessageDialog(this, messageDetail,
                    "Détail de la facture", JOptionPane.INFORMATION_MESSAGE);

            // Créer la réservation
            Reservation res = new Reservation(client, attraction, nbBillets);
            res.setReductionAppliquee(reduction);
            res.setPrixTotal(prixTotal);
            reservationDAO.addReservation(res);

            // Créer la facture
            Facture fact = new Facture(res);
            factureDAO.addFacture(fact);

            JOptionPane.showMessageDialog(this, "Réservation et facture créées !");

            // Mise à jour du nombre de réservations si le client est membre
            if (client.getTypeClient() != TypeClient.INVITE) {
                client.incrementerReservations();
                // On ré-insère ou on met à jour le client en base si nécessaire
                clientDAO.addClient(client);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez saisir des valeurs numériques valides.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

