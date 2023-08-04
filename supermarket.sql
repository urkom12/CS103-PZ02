-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 26, 2023 at 12:16 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `supermarket`
--

-- --------------------------------------------------------

--
-- Table structure for table `artikli`
--

CREATE TABLE `artikli` (
  `vrednost` varchar(255) NOT NULL,
  `ime` varchar(255) DEFAULT NULL,
  `cena` double(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `hashtabela`
--

CREATE TABLE `hashtabela` (
  `upc` varchar(255) NOT NULL,
  `vrednost` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `kes`
--

CREATE TABLE `kes` (
  `upc` varchar(255) NOT NULL,
  `vrednost` int(11) DEFAULT NULL,
  `ime` varchar(255) DEFAULT NULL,
  `cena` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kes`
--

INSERT INTO `kes` (`upc`, `vrednost`, `ime`, `cena`) VALUES
('4297f44b13955235245b2497399d7a93', 4297, 'divac', 120),
('e3ceb5881a0a1fdaad01296d7554868d', 0, 'marko', 190);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `artikli`
--
ALTER TABLE `artikli`
  ADD PRIMARY KEY (`vrednost`);

--
-- Indexes for table `hashtabela`
--
ALTER TABLE `hashtabela`
  ADD PRIMARY KEY (`upc`);

--
-- Indexes for table `kes`
--
ALTER TABLE `kes`
  ADD PRIMARY KEY (`upc`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
