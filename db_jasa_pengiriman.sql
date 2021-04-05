-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 05, 2021 at 05:07 AM
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

--
-- Dumping data for table `akses`
--

INSERT INTO `akses` (`id_akses`, `nama_akses`, `operasi`) VALUES
(1, 'Pengiriman', 'CRUD'),
(2, 'Pelacakan', 'CRUD'),
(3, 'Pengguna', 'CRUD'),
(4, 'Cabang', 'CRUD'),
(5, 'Provinsi', 'CRUD'),
(6, 'Kota', 'CRUD'),
(7, 'Biaya', 'CRUD'),
(8, 'Peran', 'CRUD'),
(9, 'Akses', 'CRUD');

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

--
-- Dumping data for table `biaya`
--

INSERT INTO `biaya` (`id_biaya`, `id_kota_asal`, `id_kota_tujuan`, `harga`) VALUES
(10, 1, 3, 10000);

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
(10, 1, 'Cab. Salatiga - Dipo', 'Jl. Diponegoro', '082144839483'),
(11, 3, 'Cab. Purworejo', 'Jl. Mangga 1 No.22', '082248665791');

-- --------------------------------------------------------

--
-- Table structure for table `detail_akses`
--

CREATE TABLE `detail_akses` (
  `id_detail_akses` int(11) NOT NULL,
  `id_peran` int(11) DEFAULT NULL,
  `id_akses` int(11) NOT NULL,
  `batasan_operasi` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_akses`
--

INSERT INTO `detail_akses` (`id_detail_akses`, `id_peran`, `id_akses`, `batasan_operasi`) VALUES
(9, 5, 1, 'CRUD'),
(10, 5, 2, 'CRUD'),
(11, 5, 3, 'CRUD'),
(12, 5, 4, 'CRUD'),
(13, 5, 5, 'CRUD'),
(14, 5, 6, 'CRUD'),
(15, 5, 7, 'CRUD'),
(16, 5, 8, 'CRUD'),
(17, 5, 9, 'CRUD');

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
(1, 1, 'Salatiga'),
(3, 1, 'Purworejo'),
(5, 4, 'Ambon'),
(6, 5, 'Manado');

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

--
-- Dumping data for table `pelacakan`
--

INSERT INTO `pelacakan` (`id_pelacakan`, `no_resi`, `id_cabang`, `id_status_pelacakan`, `waktu_lapor`, `keterangan`) VALUES
(4, 'RS20210330073044', 10, 12, '2021-03-30 00:32:52', 'Sedang disortir');

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
  `password` varchar(200) NOT NULL DEFAULT 'secret',
  `terakhir_login` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`id_pengguna`, `id_cabang`, `id_peran`, `nama`, `email`, `password`, `terakhir_login`) VALUES
(4, 10, 5, 'Admin Cab. Salatiga', 'admin.salatiga@jasakirim.com', 'admin123', '2021-04-05 03:07:43');

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

--
-- Dumping data for table `pengiriman`
--

INSERT INTO `pengiriman` (`no_resi`, `id_cabang_pengirim`, `id_kota_penerima`, `isi_barang`, `berat`, `waktu_kirim`, `nama_penerima`, `alamat_penerima`, `no_hp_penerima`, `biaya`) VALUES
('RS20210330073044', 10, 3, 'Buku', 1.5, '2021-03-30 00:31:46', 'Rietts', 'Test', '08132247882773', 15000);

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
(5, 'SuperAdmin');

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
(1, 'Jawa Tengah'),
(4, 'Maluku'),
(5, 'Sulawesi Utara');

-- --------------------------------------------------------

--
-- Table structure for table `riwayat_peran`
--

CREATE TABLE `riwayat_peran` (
  `id_riwayat_peran` int(11) NOT NULL,
  `id_pengguna` int(11) NOT NULL,
  `id_peran` int(11) DEFAULT NULL,
  `tanggal_mulai` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `riwayat_peran`
--

INSERT INTO `riwayat_peran` (`id_riwayat_peran`, `id_pengguna`, `id_peran`, `tanggal_mulai`) VALUES
(3, 4, 5, '2021-03-02');

-- --------------------------------------------------------

--
-- Table structure for table `status_pelacakan`
--

CREATE TABLE `status_pelacakan` (
  `id_status_pelacakan` int(11) NOT NULL,
  `nama_status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `status_pelacakan`
--

INSERT INTO `status_pelacakan` (`id_status_pelacakan`, `nama_status`) VALUES
(9, 'DELIVERED'),
(10, 'REDELIVERY'),
(11, 'HOLD'),
(12, 'Received at Sorting Center'),
(13, 'Received at Origin Gateway'),
(14, 'Received at Warehouse'),
(15, 'With Delivery Courier');

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
  MODIFY `id_akses` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `biaya`
--
ALTER TABLE `biaya`
  MODIFY `id_biaya` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `cabang`
--
ALTER TABLE `cabang`
  MODIFY `id_cabang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `detail_akses`
--
ALTER TABLE `detail_akses`
  MODIFY `id_detail_akses` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `kota`
--
ALTER TABLE `kota`
  MODIFY `id_kota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `pelacakan`
--
ALTER TABLE `pelacakan`
  MODIFY `id_pelacakan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `id_pengguna` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `peran`
--
ALTER TABLE `peran`
  MODIFY `id_peran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `provinsi`
--
ALTER TABLE `provinsi`
  MODIFY `id_provinsi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `riwayat_peran`
--
ALTER TABLE `riwayat_peran`
  MODIFY `id_riwayat_peran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `status_pelacakan`
--
ALTER TABLE `status_pelacakan`
  MODIFY `id_status_pelacakan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

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
  ADD CONSTRAINT `CST-peran-detail_akses` FOREIGN KEY (`id_peran`) REFERENCES `peran` (`id_peran`) ON DELETE SET NULL ON UPDATE CASCADE;

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
