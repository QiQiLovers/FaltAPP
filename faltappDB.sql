-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-11-2025 a las 01:44:38
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `faltapp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administra`
--

CREATE TABLE `administra` (
  `Usuario` varchar(30) NOT NULL,
  `CiProfe` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `Usuario` varchar(30) NOT NULL,
  `Contrasenia` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `curso`
--

CREATE TABLE `curso` (
  `Ci_Profesor` int(8) NOT NULL,
  `ID` varchar(30) NOT NULL,
  `Materia` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gestiona`
--

CREATE TABLE `gestiona` (
  `Usuario` varchar(30) NOT NULL,
  `Id_Inasistencias` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inasistencia`
--

CREATE TABLE `inasistencia` (
  `Id_Inasistencias` int(11) NOT NULL,
  `CiProfe` int(8) NOT NULL,
  `Observacion` varchar(255) NOT NULL,
  `Fecha_fin` date NOT NULL,
  `Fecha_inicio` date NOT NULL,
  `Razon` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `posee`
--

CREATE TABLE `posee` (
  `CiProfe` int(8) NOT NULL,
  `Id_Inasistencias` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE `profesor` (
  `CI` int(8) NOT NULL,
  `Nombre` varchar(30) NOT NULL,
  `Apellido` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administra`
--
ALTER TABLE `administra`
  ADD PRIMARY KEY (`Usuario`,`CiProfe`),
  ADD KEY `CiProfe` (`CiProfe`);

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD UNIQUE KEY `Usuario` (`Usuario`);

--
-- Indices de la tabla `curso`
--
ALTER TABLE `curso`
  ADD PRIMARY KEY (`ID`,`Ci_Profesor`),
  ADD KEY `Ci_Profesor` (`Ci_Profesor`);

--
-- Indices de la tabla `gestiona`
--
ALTER TABLE `gestiona`
  ADD PRIMARY KEY (`Usuario`,`Id_Inasistencias`),
  ADD KEY `Id_Inasistencias` (`Id_Inasistencias`);

--
-- Indices de la tabla `inasistencia`
--
ALTER TABLE `inasistencia`
  ADD PRIMARY KEY (`Id_Inasistencias`),
  ADD KEY `CiProfe` (`CiProfe`);

--
-- Indices de la tabla `posee`
--
ALTER TABLE `posee`
  ADD PRIMARY KEY (`CiProfe`,`Id_Inasistencias`),
  ADD KEY `Id_Inasistencias` (`Id_Inasistencias`);

--
-- Indices de la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD PRIMARY KEY (`CI`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `inasistencia`
--
ALTER TABLE `inasistencia`
  MODIFY `Id_Inasistencias` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `administra`
--
ALTER TABLE `administra`
  ADD CONSTRAINT `administra_ibfk_1` FOREIGN KEY (`Usuario`) REFERENCES `administrador` (`Usuario`),
  ADD CONSTRAINT `administra_ibfk_2` FOREIGN KEY (`CiProfe`) REFERENCES `profesor` (`CI`);

--
-- Filtros para la tabla `curso`
--
ALTER TABLE `curso`
  ADD CONSTRAINT `curso_ibfk_1` FOREIGN KEY (`Ci_Profesor`) REFERENCES `profesor` (`CI`);

--
-- Filtros para la tabla `gestiona`
--
ALTER TABLE `gestiona`
  ADD CONSTRAINT `gestiona_ibfk_1` FOREIGN KEY (`Usuario`) REFERENCES `administrador` (`Usuario`),
  ADD CONSTRAINT `gestiona_ibfk_2` FOREIGN KEY (`Id_Inasistencias`) REFERENCES `inasistencia` (`Id_Inasistencias`);

--
-- Filtros para la tabla `inasistencia`
--
ALTER TABLE `inasistencia`
  ADD CONSTRAINT `inasistencia_ibfk_1` FOREIGN KEY (`CiProfe`) REFERENCES `profesor` (`CI`);

--
-- Filtros para la tabla `posee`
--
ALTER TABLE `posee`
  ADD CONSTRAINT `posee_ibfk_1` FOREIGN KEY (`Id_Inasistencias`) REFERENCES `inasistencia` (`Id_Inasistencias`),
  ADD CONSTRAINT `posee_ibfk_2` FOREIGN KEY (`CiProfe`) REFERENCES `profesor` (`CI`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
