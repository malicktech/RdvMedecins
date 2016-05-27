-- EXECUTED ON STARTUP  in DEV Profile to initialize database when Hibernate is used to start DB

-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Ven 27 Mai 2016 à 15:00
-- Version du serveur :  5.7.9
-- Version de PHP :  7.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


INSERT INTO `admin` (`id`, `birthday`, `civility`, `first_name`, `last_name`, `mobile_phone_number`, `version`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, '2014-06-25', 'MR', 'System', 'System', '0651920130', 0, 'system', '2016-05-27 14:56:59', NULL, NULL),
(2, '2014-06-25', 'MR', 'Anonymous', 'User', '0651920130', 0, 'system', '2016-05-27 14:56:59', NULL, NULL),
(3, '2014-06-25', 'MR', 'Administrator', 'Administrator', '0651920130', 0, 'system', '2016-05-27 14:56:59', NULL, NULL),
(4, '2014-06-25', 'MR', 'User', 'User', '0651920130', 0, 'system', '2016-05-27 14:56:59', NULL, NULL);


INSERT INTO `clients` (`id`, `birthday`, `civility`, `first_name`, `last_name`, `mobile_phone_number`, `version`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, '1990-06-25', 'MR', 'Jules', 'MARTIN', '0752620130', 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(2, '1990-06-25', 'MRS', 'Christine', 'GERMAN', '0651820130', 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(3, '1990-06-25', 'MR', 'Jules', 'JACQUARD', '0651920230', 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(4, '1990-06-25', 'MRS', 'Brigitte', 'BISTROU', '0751920130', 1, 'system', '2016-05-27 14:56:59', NULL, NULL);

INSERT INTO `medecins` (`id`, `birthday`, `civility`, `first_name`, `last_name`, `mobile_phone_number`, `id_specialisation`, `version`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, '1990-06-25', 'MRS', 'Marie', 'PELISSIER', '0752620130', NULL, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(2, '1990-06-25', 'MR', 'Jacques', 'BROMARD', '0651820130', NULL, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(3, '1990-06-25', 'MR', 'Philippe', 'JANDOT', '0651920230', NULL, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(4, '1990-06-25', 'MRS', 'Justine', 'JACQUEMOT', '0751920130', NULL, 1, 'system', '2016-05-27 14:56:59', NULL, NULL);


INSERT INTO `creneaux` (`id`, `hdebut`, `hfin`, `mdebut`, `mfin`, `id_medecin`, `version`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, 8, 8, 0, 20, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(2, 8, 8, 20, 40, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(3, 8, 9, 40, 0, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(4, 9, 9, 0, 20, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(5, 9, 9, 20, 40, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(6, 9, 10, 40, 0, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(7, 10, 10, 0, 20, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(8, 10, 10, 20, 40, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(9, 10, 11, 40, 0, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(10, 11, 11, 0, 20, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(11, 11, 11, 20, 40, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(12, 11, 12, 40, 0, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(13, 14, 14, 0, 20, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(14, 14, 14, 20, 40, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(15, 14, 15, 40, 0, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(16, 15, 15, 0, 20, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(17, 15, 15, 20, 40, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(18, 15, 16, 40, 0, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(19, 16, 16, 0, 20, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(20, 16, 16, 20, 40, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(21, 16, 17, 40, 0, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(22, 17, 17, 0, 20, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(23, 17, 17, 20, 40, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(24, 17, 18, 40, 0, 1, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(25, 8, 8, 0, 20, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(26, 8, 8, 20, 40, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(27, 8, 9, 40, 0, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(28, 9, 9, 0, 20, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(29, 9, 9, 20, 40, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(30, 9, 10, 40, 0, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(31, 10, 10, 0, 20, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(32, 10, 10, 20, 40, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(33, 10, 11, 40, 0, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(34, 11, 11, 0, 20, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(35, 11, 11, 20, 40, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(36, 11, 12, 40, 0, 2, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(37, 8, 8, 0, 20, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(38, 8, 8, 20, 40, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(39, 8, 9, 40, 0, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(40, 9, 9, 0, 20, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(41, 9, 9, 20, 40, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(42, 9, 10, 40, 0, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(43, 10, 10, 0, 20, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(44, 10, 10, 20, 40, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(45, 10, 11, 40, 0, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL),
(46, 11, 11, 0, 20, 3, 1, 'system', '2016-05-27 14:56:59', NULL, NULL);

INSERT INTO `rv` (`id`, `jour`, `id_client`, `id_creneau`, `version`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES
(8, '2014-06-26', 3, 4, 1, 'system', '2016-05-27 14:57:00', NULL, NULL),
(14, '2014-06-26', 4, 1, 1, 'system', '2016-05-27 14:57:00', NULL, NULL),
(18, '2014-07-03', 1, 1, 1, 'system', '2016-05-27 14:57:00', NULL, NULL),
(21, '2015-02-15', 1, 1, 1, 'system', '2016-05-27 14:57:00', NULL, NULL),
(75, '2015-01-21', 4, 7, 1, 'system', '2016-05-27 14:57:00', NULL, NULL),
(84, '2015-02-01', 1, 1, 1, 'system', '2016-05-27 14:57:00', NULL, NULL),
(98, '2015-01-22', 4, 8, 1, 'system', '2016-05-27 14:57:00', NULL, NULL),
(100, '2015-01-22', 4, 2, 1, 'system', '2016-05-27 14:57:00', NULL, NULL),
(101, '2015-01-31', 1, 8, 1, 'system', '2016-05-27 14:57:00', NULL, NULL),
(117, '2015-01-28', 4, 2, 1, 'system', '2016-05-27 14:57:00', NULL, NULL);


INSERT INTO `zocdoc_authority` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO `zocdoc_user` (`id`, `login`, `email`, `password_hash`, `activated`, `activation_key`, `lang_key`, `reset_date`, `reset_key`, `person_user_id`, `version`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, 'system', 'system@localhost', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', b'1', NULL, 'en', NULL, NULL, 1, 0, 'system', '2016-05-27 14:56:59', NULL, NULL),
(2, 'anonymousUser', 'anonymous@localhost', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', b'1', NULL, 'en', NULL, NULL, 2, 0, 'system', '2016-05-27 14:56:59', NULL, NULL),
(3, 'admin', 'admin@localhost', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', b'1', NULL, 'en', NULL, NULL, 3, 0, 'system', '2016-05-27 14:56:59', NULL, NULL),
(4, 'user', 'user@localhost', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', b'1', NULL, 'en', NULL, NULL, 4, 0, 'system', '2016-05-27 14:56:59', NULL, NULL);

INSERT INTO `zocdoc_user_authority` (`user_id`, `authority_name`) VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(3, 'ROLE_ADMIN'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
