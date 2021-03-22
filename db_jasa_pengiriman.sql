-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2021 at 12:20 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.4.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_jasa_pengiriman`
--

-- --------------------------------------------------------

--
-- Table structure for table `akses`
--

CREATE TABLE `akses` (
  `id_akses` int(11) NOT NULL,
  `nama_akses` varchar(30) NOT NULL,
  `operasi` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `biaya`
--

CREATE TABLE `biaya` (
  `id_biaya` int(11) NOT NULL,
  `id_kota_asal` int(11) NOT NULL,
  `id_kota_tujuan` int(11) NOT NULL,
  `harga` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `cabang`
--

CREATE TABLE `cabang` (
  `id_cabang` int(11) NOT NULL,
  `id_kota` int(11) NOT NULL,
  `nama_cabang` varchar(100) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `no_hp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cabang`
--

INSERT INTO `cabang` (`id_cabang`, `id_kota`, `nama_cabang`, `alamat`, `no_hp`) VALUES
(4, 1, 'Cab. Salatiga', 'Jl. Diponegoro', '082283947281');

-- --------------------------------------------------------

--
-- Table structure for table `detail_akses`
--

CREATE TABLE `detail_akses` (
  `id_detail_akses` int(11) NOT NULL,
  `id_peran` int(11) NOT NULL,
  `id_akses` int(11) NOT NULL,
  `batasan_operasi` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `kota`
--

CREATE TABLE `kota` (
  `id_kota` int(11) NOT NULL,
  `id_provinsi` int(11) NOT NULL,
  `nama_kota` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kota`
--

INSERT INTO `kota` (`id_kota`, `id_provinsi`, `nama_kota`) VALUES
(1, 1, 'Salatiga');

-- --------------------------------------------------------

--
-- Table structure for table `pelacakan`
--

CREATE TABLE `pelacakan` (
  `id_pelacakan` int(11) NOT NULL,
  `no_resi` char(16) NOT NULL,
  `id_cabang` int(11) DEFAULT NULL,
  `id_status_pelacakan` int(11) DEFAULT NULL,
  `waktu_lapor` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `keterangan` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pengguna`
--

CREATE TABLE `pengguna` (
  `id_pengguna` int(11) NOT NULL,
  `id_cabang` int(11) DEFAULT NULL,
  `id_peran` int(11) DEFAULT NULL,
  `nama` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `terakhir_login` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`id_pengguna`, `id_cabang`, `id_peran`, `nama`, `email`, `password`, `terakhir_login`) VALUES
(4, 4, 4, 'Admin Cab. Salatiga', 'admin.salatiga@jasakirim.com', 'admin123', '2021-03-15 22:23:40');

-- --------------------------------------------------------

--
-- Table structure for table `pengiriman`
--

CREATE TABLE `pengiriman` (
  `no_resi` char(16) NOT NULL,
  `id_cabang_pengirim` int(11) DEFAULT NULL,
  `id_kota_penerima` int(11) DEFAULT NULL,
  `isi_barang` text NOT NULL,
  `berat` double NOT NULL,
  `waktu_kirim` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `nama_penerima` varchar(100) NOT NULL,
  `alamat_penerima` varchar(100) NOT NULL,
  `no_hp_penerima` varchar(15) NOT NULL,
  `biaya` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `peran`
--

CREATE TABLE `peran` (
  `id_peran` int(11) NOT NULL,
  `nama_peran` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `peran`
--

INSERT INTO `peran` (`id_peran`, `nama_peran`) VALUES
(4, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `provinsi`
--

CREATE TABLE `provinsi` (
  `id_provinsi` int(11) NOT NULL,
  `nama_provinsi` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `provinsi`
--

INSERT INTO `provinsi` (`id_provinsi`, `nama_provinsi`) VALUES
(1, 'Jawa Tengah');

-- --------------------------------------------------------

--
-- Table structure for table `riwayat_peran`
--

CREATE TABLE `riwayat_peran` (
  `id_riwayat_peran` int(11) NOT NULL,
  `id_pengguna` int(11) NOT NULL,
  `id_peran` int(11) DEFAULT NULL,
  `tanggal_dibuat` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `status_pelacakan`
--

CREATE TABLE `status_pelacakan` (
  `id_status_pelacakan` int(11) NOT NULL,
  `nama_status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akses`
--
ALTER TABLE `akses`
  ADD PRIMARY KEY (`id_akses`);

--
-- Indexes for table `biaya`
--
ALTER TABLE `biaya`
  ADD PRIMARY KEY (`id_biaya`),
  ADD KEY `id_kota_asal` (`id_kota_asal`),
  ADD KEY `id_kota_tujuan` (`id_kota_tujuan`);

--
-- Indexes for table `cabang`
--
ALTER TABLE `cabang`
  ADD PRIMARY KEY (`id_cabang`),
  ADD KEY `id_kota` (`id_kota`);

--
-- Indexes for table `detail_akses`
--
ALTER TABLE `detail_akses`
  ADD PRIMARY KEY (`id_detail_akses`),
  ADD KEY `id_peran` (`id_peran`),
  ADD KEY `id_akses` (`id_akses`);

--
-- Indexes for table `kota`
--
ALTER TABLE `kota`
  ADD PRIMARY KEY (`id_kota`),
  ADD KEY `id_provinsi` (`id_provinsi`);

--
-- Indexes for table `pelacakan`
--
ALTER TABLE `pelacakan`
  ADD PRIMARY KEY (`id_pelacakan`),
  ADD KEY `no_resi` (`no_resi`),
  ADD KEY `id_cabang` (`id_cabang`),
  ADD KEY `id_status_pelacakan` (`id_status_pelacakan`);

--
-- Indexes for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`id_pengguna`),
  ADD KEY `id_cabang` (`id_cabang`),
  ADD KEY `id_peran` (`id_peran`);

--
-- Indexes for table `pengiriman`
--
ALTER TABLE `pengiriman`
  ADD PRIMARY KEY (`no_resi`),
  ADD KEY `id_kota_penerima` (`id_kota_penerima`),
  ADD KEY `id_cabang_pengirim` (`id_cabang_pengirim`);

--
-- Indexes for table `peran`
--
ALTER TABLE `peran`
  ADD PRIMARY KEY (`id_peran`),
  ADD UNIQUE KEY `nama_peran` (`nama_peran`);

--
-- Indexes for table `provinsi`
--
ALTER TABLE `provinsi`
  ADD PRIMARY KEY (`id_provinsi`);

--
-- Indexes for table `riwayat_peran`
--
ALTER TABLE `riwayat_peran`
  ADD PRIMARY KEY (`id_riwayat_peran`),
  ADD KEY `id_pengguna` (`id_pengguna`),
  ADD KEY `id_peran` (`id_peran`);

--
-- Indexes for table `status_pelacakan`
--
ALTER TABLE `status_pelacakan`
  ADD PRIMARY KEY (`id_status_pelacakan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `akses`
--
ALTER TABLE `akses`
  MODIFY `id_akses` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `biaya`
--
ALTER TABLE `biaya`
  MODIFY `id_biaya` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cabang`
--
ALTER TABLE `cabang`
  MODIFY `id_cabang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `detail_akses`
--
ALTER TABLE `detail_akses`
  MODIFY `id_detail_akses` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `kota`
--
ALTER TABLE `kota`
  MODIFY `id_kota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `pelacakan`
--
ALTER TABLE `pelacakan`
  MODIFY `id_pelacakan` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `id_pengguna` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `peran`
--
ALTER TABLE `peran`
  MODIFY `id_peran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `provinsi`
--
ALTER TABLE `provinsi`
  MODIFY `id_provinsi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `riwayat_peran`
--
ALTER TABLE `riwayat_peran`
  MODIFY `id_riwayat_peran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `status_pelacakan`
--
ALTER TABLE `status_pelacakan`
  MODIFY `id_status_pelacakan` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `biaya`
--
ALTER TABLE `biaya`
  ADD CONSTRAINT `CST-kota_asal-biaya` FOREIGN KEY (`id_kota_asal`) REFERENCES `kota` (`id_kota`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `CST-kota_tujuan-biaya` FOREIGN KEY (`id_kota_tujuan`) REFERENCES `kota` (`id_kota`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cabang`
--
ALTER TABLE `cabang`
  ADD CONSTRAINT `CST-kota-cabang` FOREIGN KEY (`id_kota`) REFERENCES `kota` (`id_kota`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detail_akses`
--
ALTER TABLE `detail_akses`
  ADD CONSTRAINT `CST-akses_detail_akses` FOREIGN KEY (`id_akses`) REFERENCES `akses` (`id_akses`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `CST-peran-detail_akses` FOREIGN KEY (`id_peran`) REFERENCES `peran` (`id_peran`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `kota`
--
ALTER TABLE `kota`
  ADD CONSTRAINT `CST-provinsi-kota` FOREIGN KEY (`id_provinsi`) REFERENCES `provinsi` (`id_provinsi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pelacakan`
--
ALTER TABLE `pelacakan`
  ADD CONSTRAINT `CST-cabang-pelacakan` FOREIGN KEY (`id_cabang`) REFERENCES `cabang` (`id_cabang`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `CST-pengiriman-pelacakan` FOREIGN KEY (`no_resi`) REFERENCES `pengiriman` (`no_resi`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `CST-status_pelacakan-pelacakan` FOREIGN KEY (`id_status_pelacakan`) REFERENCES `status_pelacakan` (`id_status_pelacakan`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD CONSTRAINT `CST-cabang-pengguna` FOREIGN KEY (`id_cabang`) REFERENCES `cabang` (`id_cabang`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `CST-peran-pengguna` FOREIGN KEY (`id_peran`) REFERENCES `peran` (`id_peran`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `pengiriman`
--
ALTER TABLE `pengiriman`
  ADD CONSTRAINT `CST-cabang-pengiriman` FOREIGN KEY (`id_cabang_pengirim`) REFERENCES `cabang` (`id_cabang`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `CST-kota-pengiriman` FOREIGN KEY (`id_kota_penerima`) REFERENCES `kota` (`id_kota`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `riwayat_peran`
--
ALTER TABLE `riwayat_peran`
  ADD CONSTRAINT `CST-pengguna-riwayat_peran` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `CST-peran-riwayat_peran` FOREIGN KEY (`id_peran`) REFERENCES `peran` (`id_peran`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
