-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Jeu 29 Janvier 2015 à 08:17
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `dbrdvmedecins`
--
CREATE DATABASE IF NOT EXISTS `dbrdvmedecins` DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci;
USE `dbrdvmedecins`;

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

CREATE TABLE IF NOT EXISTS `clients` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITRE` varchar(5) COLLATE utf8_swedish_ci NOT NULL,
  `NOM` varchar(30) COLLATE utf8_swedish_ci NOT NULL,
  `VERSION` int(11) NOT NULL,
  `PRENOM` varchar(30) COLLATE utf8_swedish_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci AUTO_INCREMENT=5 ;

--
-- Contenu de la table `clients`
--

INSERT INTO `clients` (`ID`, `TITRE`, `NOM`, `VERSION`, `PRENOM`) VALUES
(1, 'Mr', 'MARTIN', 1, 'Jules'),
(2, 'Mme', 'GERMAN', 1, 'Christine'),
(3, 'Mr', 'JACQUARD', 1, 'Jules'),
(4, 'Melle', 'BISTROU', 1, 'Brigitte');

-- --------------------------------------------------------

--
-- Structure de la table `creneaux`
--

CREATE TABLE IF NOT EXISTS `creneaux` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MDEBUT` int(11) NOT NULL,
  `HFIN` int(11) NOT NULL,
  `HDEBUT` int(11) NOT NULL,
  `MFIN` int(11) NOT NULL,
  `VERSION` int(11) NOT NULL,
  `ID_MEDECIN` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CRENEAUX_ID_MEDECIN` (`ID_MEDECIN`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci AUTO_INCREMENT=47 ;

--
-- Contenu de la table `creneaux`
--

INSERT INTO `creneaux` (`ID`, `MDEBUT`, `HFIN`, `HDEBUT`, `MFIN`, `VERSION`, `ID_MEDECIN`) VALUES
(1, 0, 8, 8, 20, 1, 1),
(2, 20, 8, 8, 40, 1, 1),
(3, 40, 9, 8, 0, 1, 1),
(4, 0, 9, 9, 20, 1, 1),
(5, 20, 9, 9, 40, 1, 1),
(6, 40, 10, 9, 0, 1, 1),
(7, 0, 10, 10, 20, 1, 1),
(8, 20, 10, 10, 40, 1, 1),
(9, 40, 11, 10, 0, 1, 1),
(10, 0, 11, 11, 20, 1, 1),
(11, 20, 11, 11, 40, 1, 1),
(12, 40, 12, 11, 0, 1, 1),
(13, 0, 14, 14, 20, 1, 1),
(14, 20, 14, 14, 40, 1, 1),
(15, 40, 15, 14, 0, 1, 1),
(16, 0, 15, 15, 20, 1, 1),
(17, 20, 15, 15, 40, 1, 1),
(18, 40, 16, 15, 0, 1, 1),
(19, 0, 16, 16, 20, 1, 1),
(20, 20, 16, 16, 40, 1, 1),
(21, 40, 17, 16, 0, 1, 1),
(22, 0, 17, 17, 20, 1, 1),
(23, 20, 17, 17, 40, 1, 1),
(24, 40, 18, 17, 0, 1, 1),
(25, 0, 8, 8, 20, 1, 2),
(26, 20, 8, 8, 40, 1, 2),
(27, 40, 9, 8, 0, 1, 2),
(28, 0, 9, 9, 20, 1, 2),
(29, 20, 9, 9, 40, 1, 2),
(30, 40, 10, 9, 0, 1, 2),
(31, 0, 10, 10, 20, 1, 2),
(32, 20, 10, 10, 40, 1, 2),
(33, 40, 11, 10, 0, 1, 2),
(34, 0, 11, 11, 20, 1, 2),
(35, 20, 11, 11, 40, 1, 2),
(36, 40, 12, 11, 0, 1, 2),
(37, 0, 8, 8, 20, 1, 3),
(38, 20, 8, 8, 40, 1, 3),
(39, 40, 9, 8, 0, 1, 3),
(40, 0, 9, 9, 20, 1, 3),
(41, 20, 9, 9, 40, 1, 3),
(42, 40, 10, 9, 0, 1, 3),
(43, 0, 10, 10, 20, 1, 3),
(44, 20, 10, 10, 40, 1, 3),
(45, 40, 11, 10, 0, 1, 3),
(46, 0, 11, 11, 20, 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `medecins`
--

CREATE TABLE IF NOT EXISTS `medecins` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITRE` varchar(5) COLLATE utf8_swedish_ci NOT NULL,
  `NOM` varchar(30) COLLATE utf8_swedish_ci NOT NULL,
  `VERSION` int(11) NOT NULL,
  `PRENOM` varchar(30) COLLATE utf8_swedish_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci AUTO_INCREMENT=5 ;

--
-- Contenu de la table `medecins`
--

INSERT INTO `medecins` (`ID`, `TITRE`, `NOM`, `VERSION`, `PRENOM`) VALUES
(1, 'Mme', 'PELISSIER', 1, 'Marie'),
(2, 'Mr', 'BROMARD', 1, 'Jacques'),
(3, 'Mr', 'JANDOT', 1, 'Philippe'),
(4, 'Melle', 'JACQUEMOT', 1, 'Justine');

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `VERSION` int(11) NOT NULL,
  `NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `roles`
--

INSERT INTO `roles` (`ID`, `VERSION`, `NAME`) VALUES
(5, 0, 'ROLE_GUEST'),
(6, 0, 'ROLE_ADMIN'),
(7, 0, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Structure de la table `rv`
--

CREATE TABLE IF NOT EXISTS `rv` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `JOUR` date NOT NULL,
  `ID_CLIENT` bigint(20) NOT NULL,
  `ID_CRENEAU` bigint(20) NOT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNQ1_RV` (`JOUR`,`ID_CRENEAU`),
  KEY `FK_RV_ID_CRENEAU` (`ID_CRENEAU`),
  KEY `FK_RV_ID_CLIENT` (`ID_CLIENT`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci AUTO_INCREMENT=119 ;

--
-- Contenu de la table `rv`
--

INSERT INTO `rv` (`ID`, `JOUR`, `ID_CLIENT`, `ID_CRENEAU`, `VERSION`) VALUES
(8, '2014-06-26', 3, 4, 0),
(14, '2014-06-26', 4, 1, 0),
(18, '2014-07-03', 1, 1, 0),
(21, '2015-02-15', 1, 1, 0),
(75, '2015-01-21', 4, 7, 0),
(84, '2015-02-01', 1, 1, 0),
(98, '2015-01-22', 4, 8, 0),
(100, '2015-01-22', 4, 2, 0),
(101, '2015-01-31', 1, 8, 0),
(117, '2015-01-28', 4, 2, 0);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `VERSION` int(11) NOT NULL,
  `IDENTITY` varchar(50) NOT NULL,
  `LOGIN` varchar(15) NOT NULL,
  `PASSWORD` varchar(60) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=18 ;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`ID`, `VERSION`, `IDENTITY`, `LOGIN`, `PASSWORD`) VALUES
(14, 0, 'admin', 'admin', '$2a$10$FN1LMKjPU46aPffh9Zaw4exJOLo51JJPWrxqzak/eJrbt3CO9WzVG'),
(15, 0, 'user', 'user', '$2a$10$SJehR9Mv2VdyRZo9F0rXa.hKAoGLhJg6kSdyfExi40mEJrNOj0BTq'),
(16, 0, 'guest', 'guest', '$2a$10$ubyWJb/vg2XZnUOAUjspZuz9jpHP3fIbPTbwQU115EtLdeSZ2PB7q'),
(17, 0, 'x', 'x', '$2a$10$kEXA56wpKHFReVqwQTyWguKguK8I4uhA2zb6t3wGxag8Dyv7AhLom');

-- --------------------------------------------------------

--
-- Structure de la table `users_roles`
--

CREATE TABLE IF NOT EXISTS `users_roles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `VERSION` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ROLE_ID` (`ROLE_ID`),
  KEY `USER_ID` (`USER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Contenu de la table `users_roles`
--

INSERT INTO `users_roles` (`ID`, `VERSION`, `USER_ID`, `ROLE_ID`) VALUES
(11, 0, 14, 6),
(12, 0, 15, 7),
(13, 0, 16, 5),
(14, 0, 17, 5);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `creneaux`
--
ALTER TABLE `creneaux`
  ADD CONSTRAINT `FK_CRENEAUX_ID_MEDECIN` FOREIGN KEY (`ID_MEDECIN`) REFERENCES `medecins` (`ID`);

--
-- Contraintes pour la table `rv`
--
ALTER TABLE `rv`
  ADD CONSTRAINT `FK_RV_ID_CLIENT` FOREIGN KEY (`ID_CLIENT`) REFERENCES `clients` (`ID`),
  ADD CONSTRAINT `FK_RV_ID_CRENEAU` FOREIGN KEY (`ID_CRENEAU`) REFERENCES `creneaux` (`ID`);

--
-- Contraintes pour la table `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK_USERSROLES_ROLES` FOREIGN KEY (`ROLE_ID`) REFERENCES `roles` (`ID`),
  ADD CONSTRAINT `FK_USERSROLES_USERS` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
