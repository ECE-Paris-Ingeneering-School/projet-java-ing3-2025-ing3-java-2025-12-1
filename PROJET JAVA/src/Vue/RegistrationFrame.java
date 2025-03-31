package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controleur.*;
import Modele.*;

public class RegistrationFrame extends JFrame {
    private JTextField txtNom, txtPrenom, txtEmail, txtAge, txtLogin;
    private JPasswordField txtPassword;
    private JComboBox<String> comboTypeClient, comboCategorie;
    private JButton btnRegister, btnCancel;
    private ClientDAO clientDAO = new ClientDAO();

    public RegistrationFrame() {
        setTitle("Inscription");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(new JLabel("Nom:"));
        txtNom = new JTextField();
        panel.add(txtNom);

        panel.add(new JLabel("Prénom:"));
        txtPrenom = new JTextField();
        panel.add(txtPrenom);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        panel.add(txtAge);

        panel.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        panel.add(txtLogin);

        panel.add(new JLabel("Mot de passe:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("Type Client:"));
        comboTypeClient = new JComboBox<>(new String[] {"NOUVEAU", "ANCIEN"});
        panel.add(comboTypeClient);

        panel.add(new JLabel("Catégorie:"));
        comboCategorie = new JComboBox<>(new String[] {"ENFANT", "REGULIER", "SENIOR"});
        panel.add(comboCategorie);

        btnRegister = new JButton("S'inscrire");
        btnCancel = new JButton("Annuler");
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnRegister);
        btnPanel.add(btnCancel);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscrireClient();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void inscrireClient() {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String email = txtEmail.getText();
        int age = Integer.parseInt(txtAge.getText());
        String login = txtLogin.getText();
        String password = new String(txtPassword.getPassword());
        TypeClient type = TypeClient.valueOf((String) comboTypeClient.getSelectedItem());
        CategorieClient categorie = CategorieClient.valueOf((String) comboCategorie.getSelectedItem());

        Client client = new Client(nom, prenom, email, age, type, categorie, login, password);
        clientDAO.addClient(client);
        JOptionPane.showMessageDialog(this, "Inscription réussie ! Votre ID est " + client.getIdClient());
        new LoginFrame().setVisible(true);
        dispose();
    }
}

