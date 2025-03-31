-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 26 mars 2025 à 16:40
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `attractionsdb`
--

-- --------------------------------------------------------

--
-- Structure de la table `attraction`
--

DROP TABLE IF EXISTS `attraction`;
CREATE TABLE IF NOT EXISTS `attraction` (
                                            `idAttraction` int NOT NULL AUTO_INCREMENT,
                                            `nom` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
    `description` text COLLATE utf8mb4_general_ci,
    `capacite` int NOT NULL,
    `prixUnitaire` decimal(10,2) NOT NULL,
    PRIMARY KEY (`idAttraction`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
                                        `idClient` int NOT NULL AUTO_INCREMENT,
                                        `nom` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
    `prenom` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
    `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `age` int NOT NULL,
    `typeClient` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
    `categorieClient` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
    `nombreReservationsPrecedentes` int NOT NULL DEFAULT '0',
    `login` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
    `motDePasse` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`idClient`)
    ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

DROP TABLE IF EXISTS `facture`;
CREATE TABLE IF NOT EXISTS `facture` (
                                         `idFacture` int NOT NULL AUTO_INCREMENT,
                                         `dateFacture` date NOT NULL,
                                         `montantTotal` decimal(10,2) NOT NULL,
    `reservation_id` int NOT NULL,
    PRIMARY KEY (`idFacture`),
    KEY `fk_facture_reservation` (`reservation_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
                                             `idReservation` int NOT NULL AUTO_INCREMENT,
                                             `dateReservation` date NOT NULL,
                                             `nombreBillets` int NOT NULL,
                                             `prixTotal` decimal(10,2) NOT NULL,
    `reductionAppliquee` decimal(10,2) NOT NULL,
    `client_id` int NOT NULL,
    `attraction_id` int NOT NULL,
    PRIMARY KEY (`idReservation`),
    KEY `fk_reservation_client` (`client_id`),
    KEY `fk_reservation_attraction` (`attraction_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
    ADD CONSTRAINT `fk_facture_reservation` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`idReservation`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
    ADD CONSTRAINT `fk_reservation_attraction` FOREIGN KEY (`attraction_id`) REFERENCES `attraction` (`idAttraction`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reservation_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`idClient`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
