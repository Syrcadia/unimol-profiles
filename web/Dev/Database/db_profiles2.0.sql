-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Gen 15, 2015 alle 09:37
-- Versione del server: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_profiles`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `contenuti`
--

CREATE TABLE IF NOT EXISTS `contenuti` (
  `id_sezione` int(11) NOT NULL,
  `id_risorsa` int(11) NOT NULL,
  `tipo_risorsa` varchar(20) NOT NULL,
  `ordine` int(11) NOT NULL,
  PRIMARY KEY (`id_sezione`,`id_risorsa`,`tipo_risorsa`,`ordine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `contenuti`
--

INSERT INTO `contenuti` (`id_sezione`, `id_risorsa`, `tipo_risorsa`, `ordine`) VALUES
(1, 1, 'file', 2),
(1, 1, 'testo', 1),
(2, 1, 'foto', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `docenti`
--

CREATE TABLE IF NOT EXISTS `docenti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `dipartimento` varchar(255) NOT NULL,
  `id_ruolo` int(11) NOT NULL,
  `id_pagina_insegnamenti` varchar(255) DEFAULT NULL,
  `nome_foto_profilo` varchar(255) DEFAULT NULL,
  `sesso` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dump dei dati per la tabella `docenti`
--

INSERT INTO `docenti` (`id`, `nome`, `cognome`, `dipartimento`, `id_ruolo`, `id_pagina_insegnamenti`, `nome_foto_profilo`, `sesso`) VALUES
(1, 'Fausto', 'Fasano', 'Bioscienze e Territorio', 0, 'fausto.fasano', 'wedding.jpg', 'M'),
(2, 'Rocco', 'Oliveto', 'Bioscienze e Territorio', 0, 'rocco.oliveto', 'Rocco.png', 'M'),
(3, 'Ciro', 'Marmolino', 'Bioscienze e Territorio', 1, 'ciro.marmolino', NULL, 'M'),
(4, 'Barbara', 'Troncarelli', 'Bioscienze e Territorio', 1, NULL, NULL, 'F'),
(5, 'Giuseppe', 'Lustrato', 'Bioscienze e Territorio', 2, 'g.lustrato', NULL, 'M');

-- --------------------------------------------------------

--
-- Struttura della tabella `email`
--

CREATE TABLE IF NOT EXISTS `email` (
  `email` varchar(50) NOT NULL,
  `id_docente` int(11) NOT NULL,
  `ordine` int(11) NOT NULL,
  PRIMARY KEY (`email`,`id_docente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `email`
--

INSERT INTO `email` (`email`, `id_docente`, `ordine`) VALUES
('faufas@gamil.com', 1, 2),
('fausto.fasano@unimol.it ', 1, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `files`
--

CREATE TABLE IF NOT EXISTS `files` (
  `id` int(11) NOT NULL,
  `nome_file` varchar(255) NOT NULL,
  `estensione` varchar(10) NOT NULL,
  `descrizione` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `files`
--

INSERT INTO `files` (`id`, `nome_file`, `estensione`, `descrizione`) VALUES
(1, 'Materiale di vitale importanza.rar', 'rar', 'Magalli');

-- --------------------------------------------------------

--
-- Struttura della tabella `foto`
--

CREATE TABLE IF NOT EXISTS `foto` (
  `id` int(11) NOT NULL,
  `nome_foto` varchar(255) NOT NULL,
  `descrizione` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `foto`
--

INSERT INTO `foto` (`id`, `nome_foto`, `descrizione`) VALUES
(1, 'fasano-largo.jpg', 'A tutta birra!!!');

-- --------------------------------------------------------

--
-- Struttura della tabella `ruoli`
--

CREATE TABLE IF NOT EXISTS `ruoli` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome_ruolo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dump dei dati per la tabella `ruoli`
--

INSERT INTO `ruoli` (`id`, `nome_ruolo`) VALUES
(0, 'Ricercatore'),
(1, 'Professori Ordinari'),
(2, 'Contadini');

-- --------------------------------------------------------

--
-- Struttura della tabella `sezioni_docenti`
--

CREATE TABLE IF NOT EXISTS `sezioni_docenti` (
  `id_sezione` int(11) NOT NULL,
  `id_docente` int(11) NOT NULL,
  `nome_sezione` varchar(100) NOT NULL,
  `ordine` int(11) NOT NULL,
  PRIMARY KEY (`id_sezione`,`id_docente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `sezioni_docenti`
--

INSERT INTO `sezioni_docenti` (`id_sezione`, `id_docente`, `nome_sezione`, `ordine`) VALUES
(1, 1, 'Sito Web', 1),
(2, 1, 'Foto', 2),
(3, 1, 'Lezioni di Tecnologie di Sviluppo per il Web', 3),
(4, 2, 'Viaggio d''istruzione Pratica di Mare', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `telefono`
--

CREATE TABLE IF NOT EXISTS `telefono` (
  `num_telefono` varchar(30) NOT NULL,
  `id_docente` int(11) NOT NULL,
  `ordine` int(11) NOT NULL,
  PRIMARY KEY (`num_telefono`,`id_docente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `telefono`
--

INSERT INTO `telefono` (`num_telefono`, `id_docente`, `ordine`) VALUES
('casa: 0874 404126', 1, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `testi`
--

CREATE TABLE IF NOT EXISTS `testi` (
  `id` int(11) NOT NULL,
  `nome_testo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `testi`
--

INSERT INTO `testi` (`id`, `nome_testo`) VALUES
(1, 'comunicazione.html');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
