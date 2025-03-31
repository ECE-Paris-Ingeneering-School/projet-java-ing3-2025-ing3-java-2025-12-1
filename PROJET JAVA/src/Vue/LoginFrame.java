package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controleur.*;
import Modele.*;

public class LoginFrame extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister, btnGuest;
    private ClientDAO clientDAO = new ClientDAO();

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        panel.add(txtLogin);

        panel.add(new JLabel("Mot de passe:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnLogin = new JButton("Se connecter");
        panel.add(btnLogin);

        btnRegister = new JButton("S'inscrire");
        panel.add(btnRegister);

        btnGuest = new JButton("Accès invité");
        panel.add(btnGuest);

        // Espace vide pour aligner
        panel.add(new JLabel(""));

        add(panel);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effectuerLogin();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationFrame().setVisible(true);
                dispose();
            }
        });

        btnGuest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pour l'accès invité, on crée et insère le client dans la base
                Client guest = new Client("Invité", "SansCompte", "guest@example.com", 0,
                        TypeClient.INVITE, CategorieClient.REGULIER);
                clientDAO.addClient(guest);  // Insère le client et met à jour son ID
                new UserFrame(guest).setVisible(true);
                dispose();
            }
        });
    }

    private void effectuerLogin() {
        String login = txtLogin.getText();
        String password = new String(txtPassword.getPassword());

        // Vérification pour l'administrateur (identifiants fixes)
        if (login.equals("admin") && password.equals("admin")) {
            new AdminFrame().setVisible(true);
            dispose();
            return;
        }

        // Recherche d'un client en base
        Client client = clientDAO.getClientByLogin(login, password);
        if (client != null) {
            new UserFrame(client).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Identifiants invalides", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

