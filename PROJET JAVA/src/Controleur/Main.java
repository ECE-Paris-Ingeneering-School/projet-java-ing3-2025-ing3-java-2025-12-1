package Controleur;

import Vue.*;

public class Main {
    public static void main(String[] args) {
        // Démarrage de l'interface graphique
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
