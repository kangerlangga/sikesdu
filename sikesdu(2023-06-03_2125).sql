-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 03 Jun 2023 pada 16.25
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sikesdu`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_balita`
--

CREATE TABLE `data_balita` (
  `id_balita` char(8) NOT NULL,
  `nik_balita` char(16) DEFAULT NULL,
  `nama_balita` varchar(50) NOT NULL,
  `nama_ortu` varchar(50) NOT NULL,
  `alamat` tinytext NOT NULL,
  `jenis_kelamin` varchar(11) NOT NULL,
  `tgl_lahir_balita` date NOT NULL,
  `bbl_balita` tinyint(4) DEFAULT NULL,
  `pbl_balita` tinyint(11) DEFAULT NULL,
  `waktu_daftar` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `data_balita`
--

INSERT INTO `data_balita` (`id_balita`, `nik_balita`, `nama_balita`, `nama_ortu`, `alamat`, `jenis_kelamin`, `tgl_lahir_balita`, `bbl_balita`, `pbl_balita`, `waktu_daftar`) VALUES
('BL017001', '351xxxxxxxxxxxx5', 'Albert', 'Luluk', 'JL. Kutuk Barat RT 10/RW 7', 'Laki - Laki', '2019-04-11', NULL, NULL, '2023-04-15 06:28:33'),
('BL017002', NULL, 'Muhammad Agus', 'Betty', 'JL. Kutuk Barat RT 11/RW 7', 'Laki - Laki', '2019-06-09', NULL, NULL, '2023-04-15 06:29:19'),
('BL017003', NULL, 'Lailatul Puspita', 'Yuli', 'JL. Kutuk Barat RT 10/RW 7', 'Perempuan', '2019-07-02', NULL, NULL, '2023-04-15 06:30:33'),
('BL017004', NULL, 'Citra Ramadhani', 'Ani', 'JL. Kutuk Barat RT 12/RW 7', 'Perempuan', '2019-09-02', NULL, NULL, '2023-04-15 08:31:55'),
('BL017005', '351xxxxxxxxxxxx3', 'Pandu Candra', 'Riska', 'JL. Kutuk Barat RT 11/RW 7', 'Laki - Laki', '2019-10-09', NULL, NULL, '2023-04-15 08:38:53'),
('BL017006', NULL, 'Aqlan Harith Ridauddin', 'Nurul', 'JL. Kutuk Barat', 'Laki - Laki', '2022-05-12', NULL, NULL, '2023-05-21 05:55:38'),
('BL017007', NULL, 'Abbad Nailun Nabhan', 'Ayu Lestari', 'JL. Kutuk Barat', 'Laki - Laki', '2022-05-03', NULL, NULL, '2023-05-21 05:56:14'),
('BL017008', NULL, 'Damar Pramudya Bayanaka', 'Widji', 'JL. Kutuk Barat', 'Laki - Laki', '2021-05-07', NULL, NULL, '2023-05-21 05:57:33'),
('BL017009', NULL, 'Cahari Jayantaka Jagat', 'Laila', 'JL. Kutuk Barat', 'Laki - Laki', '2020-05-08', NULL, NULL, '2023-05-21 05:58:09'),
('BL017010', NULL, 'Davina Zaila Hamidah', 'Nurul', 'JL. Kutuk Barat', 'Perempuan', '2020-05-02', NULL, NULL, '2023-05-21 05:59:01'),
('BL017011', NULL, 'Erlangga', 'LULUK', 'sidokare', 'Laki - Laki', '2023-05-03', 3, 50, '2023-05-22 01:39:33');

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_ibuhamil`
--

CREATE TABLE `data_ibuhamil` (
  `nik_ibuhamil` char(16) NOT NULL,
  `nama_ibuHamil` varchar(50) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `alamat` tinytext NOT NULL,
  `telepon` varchar(15) NOT NULL,
  `rencana_kelahiran` date DEFAULT NULL,
  `usia_kehamilan` tinyint(4) NOT NULL,
  `berat_badan` tinyint(4) NOT NULL,
  `tinggi_badan` smallint(6) NOT NULL,
  `waktu_daftar` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `data_ibuhamil`
--

INSERT INTO `data_ibuhamil` (`nik_ibuhamil`, `nama_ibuHamil`, `tgl_lahir`, `alamat`, `telepon`, `rencana_kelahiran`, `usia_kehamilan`, `berat_badan`, `tinggi_badan`, `waktu_daftar`) VALUES
('351508xxxxxxxxx5', 'Laura Claudia', '1999-06-10', 'Sidokare', '087954376534', '2023-06-01', 44, 80, 160, '2023-05-15 03:38:27'),
('3519384902817683', 'Erina Syifa Muthmainnah', '1994-05-12', 'Sidokare', '082139018376', '2023-08-04', 23, 70, 165, '2023-05-21 06:19:39'),
('3567281926354788', 'Kimberli', '1995-05-05', 'Sidokare', '09834273653', '2023-10-12', 24, 60, 157, '2023-05-22 01:31:02'),
('3619027389029173', 'Eri Fajrina', '1998-05-13', 'Sidokare', '089583928176', '2023-07-19', 29, 79, 170, '2023-05-21 06:25:11'),
('3672091728901927', 'Eiko Queisha Fernanda', '2000-01-04', 'Sidokare', '082138918921', '2023-07-31', 28, 70, 163, '2023-05-21 06:26:42'),
('3782091829019284', 'Nabila Tsaniya Akbari', '1993-05-14', 'Sidokare', '082193728901', '2023-08-15', 30, 65, 159, '2023-05-21 06:27:42');

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_imunisasi`
--

CREATE TABLE `data_imunisasi` (
  `id_imunisasi` char(7) NOT NULL,
  `nama_vaksin` tinytext NOT NULL,
  `dosis` float NOT NULL,
  `level` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `data_imunisasi`
--

INSERT INTO `data_imunisasi` (`id_imunisasi`, `nama_vaksin`, `dosis`, `level`) VALUES
('VB01001', 'BCG Polio 1', 0.05, 'Dasar Lengkap Bayi'),
('VB01002', 'DPT-HB-Hib 1 Polio 2', 0.5, 'Dasar Lengkap Bayi'),
('VB01003', 'DPT-HB-Hib 2 Polio 3', 0.5, 'Dasar Lengkap Bayi'),
('VB01004', 'DPT-HB-Hib 3 Polio 4', 0.5, 'Dasar Lengkap Bayi'),
('VB01005', 'Campak', 0.5, 'Dasar Lengkap Bayi'),
('VB02001', 'DPT-HB-Hib 1', 0.5, 'Lanjutan Bayi'),
('VB02002', 'Campak Rubella', 0.5, 'Lanjutan Bayi'),
('VB03001', 'Campak Rubella dan DT', 0.5, 'Lanjutan Anak'),
('VB03002', 'Tethanus Diptheria TD', 0.5, 'Lanjutan Anak'),
('VB04001', 'Pneumococcal Conjugate Vaccine (PCV)', 0.5, 'Tambahan'),
('VB04002', 'Rotavirus', 2, 'Tambahan'),
('VB04003', 'Human Papilloma Virus (HPV)', 0.5, 'Tambahan'),
('VB04004', 'Tethanus', 0.5, 'Tambahan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `imunisasi_balita`
--

CREATE TABLE `imunisasi_balita` (
  `id_imunBalita` char(15) NOT NULL,
  `id_balita` char(8) NOT NULL,
  `id_vaksin` char(7) NOT NULL,
  `id_vaksinator` char(7) NOT NULL,
  `tgl_imunisasi` date NOT NULL,
  `kondisi_balita` tinytext NOT NULL,
  `waktu_pengisian` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `imunisasi_balita`
--

INSERT INTO `imunisasi_balita` (`id_imunBalita`, `id_balita`, `id_vaksin`, `id_vaksinator`, `tgl_imunisasi`, `kondisi_balita`, `waktu_pengisian`) VALUES
('BL017001VB01001', 'BL017001', 'VB01001', 'BI01701', '2023-04-10', 'Sehat', '2023-05-21 12:58:13'),
('BL017001VB01002', 'BL017001', 'VB01002', 'BI01701', '2023-05-15', 'Sehat', '2023-05-21 13:00:28'),
('BL017002VB01001', 'BL017002', 'VB01001', 'BI01701', '2023-04-10', 'Sehat', '2023-05-21 12:58:46'),
('BL017002VB01002', 'BL017002', 'VB01002', 'BI01701', '2023-05-15', 'Sehat', '2023-05-21 13:00:50'),
('BL017003VB01001', 'BL017003', 'VB01001', 'BI01701', '2023-04-10', 'Sehat', '2023-05-21 12:59:17'),
('BL017003VB01002', 'BL017003', 'VB01002', 'BI01701', '2023-05-15', 'Sehat', '2023-05-21 13:06:33'),
('BL017004VB01001', 'BL017004', 'VB01001', 'BI01701', '2023-04-10', 'Sehat', '2023-05-21 12:59:40'),
('BL017004VB01002', 'BL017004', 'VB01002', 'BI01701', '2023-05-15', 'Sehat', '2023-05-21 13:06:56'),
('BL017005VB01001', 'BL017005', 'VB01001', 'BI01701', '2023-04-10', 'Sehat', '2023-05-21 13:00:02'),
('BL017005VB01002', 'BL017005', 'VB01002', 'BI01701', '2023-05-15', 'Sehat', '2023-05-21 13:07:14');

-- --------------------------------------------------------

--
-- Struktur dari tabel `perkembangan_balita`
--

CREATE TABLE `perkembangan_balita` (
  `id_pk_balita` char(10) NOT NULL,
  `id_balita` char(8) NOT NULL,
  `tgl_periksa` date NOT NULL,
  `berat_balita` tinyint(4) NOT NULL,
  `tinggi_balita` smallint(4) DEFAULT NULL,
  `kondisi_balita` tinytext DEFAULT NULL,
  `waktu_pengisian` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `perkembangan_balita`
--

INSERT INTO `perkembangan_balita` (`id_pk_balita`, `id_balita`, `tgl_periksa`, `berat_balita`, `tinggi_balita`, `kondisi_balita`, `waktu_pengisian`) VALUES
('BL01700102', 'BL017001', '2023-05-08', 34, 120, 'Sehat', '2023-05-08 04:32:15'),
('BL01700201', 'BL017002', '2023-05-22', 23, 110, 'Sehat', '2023-05-21 06:00:53'),
('BL01700301', 'BL017003', '2023-05-22', 26, 109, 'Sehat', '2023-05-21 06:01:13'),
('BL01700401', 'BL017004', '2023-05-22', 30, 118, 'Sehat', '2023-05-21 06:01:46'),
('BL01700501', 'BL017005', '2023-05-22', 29, 120, 'Sehat', '2023-05-21 06:02:05'),
('BL01700601', 'BL017006', '2023-05-22', 34, 121, 'Sehat', '2023-05-21 06:02:30'),
('BL01700701', 'BL017007', '2023-05-22', 27, 116, 'Sehat', '2023-05-21 06:03:02'),
('BL01700801', 'BL017008', '2023-05-22', 30, 120, 'Sehat', '2023-05-21 06:03:20'),
('BL01700901', 'BL017009', '2023-05-22', 28, 120, 'Sakit Batuk Ringan', '2023-05-21 06:03:56'),
('BL01701001', 'BL017010', '2023-05-22', 29, 114, 'Sehat', '2023-05-21 06:04:20'),
('BL01701101', 'BL017011', '2023-05-22', 25, 60, 'Sehat', '2023-05-22 01:40:52');

-- --------------------------------------------------------

--
-- Struktur dari tabel `perkembangan_ibuhamil`
--

CREATE TABLE `perkembangan_ibuhamil` (
  `id_pk_ibuhamil` char(19) NOT NULL,
  `nik_ibuhamil` char(16) NOT NULL,
  `tgl_periksa` date NOT NULL,
  `keluhan` tinytext NOT NULL,
  `usia_kehamilan` tinyint(4) NOT NULL,
  `tensi_sistole` smallint(6) NOT NULL,
  `tensi_diastole` smallint(6) NOT NULL,
  `berat_badan` tinyint(4) NOT NULL,
  `waktu_pengisian` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `perkembangan_ibuhamil`
--

INSERT INTO `perkembangan_ibuhamil` (`id_pk_ibuhamil`, `nik_ibuhamil`, `tgl_periksa`, `keluhan`, `usia_kehamilan`, `tensi_sistole`, `tensi_diastole`, `berat_badan`, `waktu_pengisian`) VALUES
('351508xxxxxxxxx5001', '351508xxxxxxxxx5', '2023-05-15', 'Pusing', 27, 120, 90, 80, '2023-05-15 03:40:56'),
('351508xxxxxxxxx5002', '351508xxxxxxxxx5', '2023-05-22', 'Mual', 20, 120, 70, 80, '2023-05-21 06:28:26'),
('3519384902817683001', '3519384902817683', '2023-05-22', 'Kaki Bengkak', 24, 120, 80, 75, '2023-05-21 06:29:18'),
('3567281926354788001', '3567281926354788', '2023-05-22', 'Pusing', 10, 120, 90, 60, '2023-05-22 01:42:24'),
('3619027389029173001', '3619027389029173', '2023-05-22', 'Kepala Pusing', 30, 110, 80, 80, '2023-05-21 06:29:57'),
('3672091728901927001', '3672091728901927', '2023-05-22', 'Tidak ada', 29, 120, 80, 75, '2023-05-21 06:30:25'),
('3782091829019284001', '3782091829019284', '2023-05-22', 'Kepala Pusing', 31, 120, 80, 68, '2023-05-21 06:30:50');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_pegawai` char(7) NOT NULL,
  `nama_lengkap` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `jabatan` varchar(20) NOT NULL,
  `telpon` varchar(15) NOT NULL,
  `id_kartu` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_pegawai`, `nama_lengkap`, `password`, `jabatan`, `telpon`, `id_kartu`) VALUES
('BI01701', 'Dewi Anggraeni', '5e8edd851d2fdfbd7415232c67367cc3', 'Bidan Posyandu', '082109261790', '323f0bc14b6d847f5fb4020ed1ff67c5'),
('KD01701', 'Siti Aisyah', '5e8edd851d2fdfbd7415232c67367cc3', 'Kader Posyandu', '082192835180', '07d7242a69b6dbf89ef2eca39acb3f7a'),
('KD01702', 'Tantio', 'e3e121908fbbcfd56af8571b5172fdf5', 'Kader Posyandu', '082139188278', NULL),
('KP01701', 'Hafifah Hartini Widyawati', '5e8edd851d2fdfbd7415232c67367cc3', 'Kepala Posyandu', '082139092372', '716b0d1f2f95014a3b6d029284ff0fcf');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `data_balita`
--
ALTER TABLE `data_balita`
  ADD PRIMARY KEY (`id_balita`);

--
-- Indeks untuk tabel `data_ibuhamil`
--
ALTER TABLE `data_ibuhamil`
  ADD PRIMARY KEY (`nik_ibuhamil`);

--
-- Indeks untuk tabel `data_imunisasi`
--
ALTER TABLE `data_imunisasi`
  ADD PRIMARY KEY (`id_imunisasi`);

--
-- Indeks untuk tabel `imunisasi_balita`
--
ALTER TABLE `imunisasi_balita`
  ADD PRIMARY KEY (`id_imunBalita`),
  ADD KEY `id_balita` (`id_balita`),
  ADD KEY `id_vaksin` (`id_vaksin`),
  ADD KEY `id_vaksinator` (`id_vaksinator`);

--
-- Indeks untuk tabel `perkembangan_balita`
--
ALTER TABLE `perkembangan_balita`
  ADD PRIMARY KEY (`id_pk_balita`),
  ADD KEY `nik_balita` (`id_balita`);

--
-- Indeks untuk tabel `perkembangan_ibuhamil`
--
ALTER TABLE `perkembangan_ibuhamil`
  ADD PRIMARY KEY (`id_pk_ibuhamil`),
  ADD KEY `nik_ibuhamil` (`nik_ibuhamil`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_pegawai`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `imunisasi_balita`
--
ALTER TABLE `imunisasi_balita`
  ADD CONSTRAINT `imunisasi_balita_ibfk_2` FOREIGN KEY (`id_vaksinator`) REFERENCES `user` (`id_pegawai`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `imunisasi_balita_ibfk_3` FOREIGN KEY (`id_vaksin`) REFERENCES `data_imunisasi` (`id_imunisasi`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `imunisasi_balita_ibfk_4` FOREIGN KEY (`id_balita`) REFERENCES `data_balita` (`id_balita`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `perkembangan_balita`
--
ALTER TABLE `perkembangan_balita`
  ADD CONSTRAINT `perkembangan_balita_ibfk_1` FOREIGN KEY (`id_balita`) REFERENCES `data_balita` (`id_balita`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `perkembangan_ibuhamil`
--
ALTER TABLE `perkembangan_ibuhamil`
  ADD CONSTRAINT `perkembangan_ibuhamil_ibfk_1` FOREIGN KEY (`nik_ibuhamil`) REFERENCES `data_ibuhamil` (`nik_ibuhamil`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
