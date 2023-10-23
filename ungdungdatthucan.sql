-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 23, 2023 lúc 11:53 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ungdungdatthucan`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `MaDH` int(11) NOT NULL,
  `MaMonAn` int(11) NOT NULL,
  `SoLuong` int(11) NOT NULL,
  `Gia` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`MaDH`, `MaMonAn`, `SoLuong`, `Gia`) VALUES
(6, 36, 1, '47000'),
(11, 35, 1, '32000'),
(12, 38, 1, '89000'),
(13, 38, 1, '89000'),
(14, 38, 1, '89000'),
(15, 31, 2, '89000'),
(16, 37, 1, '79000'),
(17, 33, 1, '91000'),
(18, 30, 1, '232000'),
(19, 37, 1, '79000'),
(20, 27, 1, '46000'),
(21, 30, 1, '232000'),
(22, 31, 1, '89000'),
(23, 25, 1, '46000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `MaDH` int(11) NOT NULL,
  `MaKH` int(11) NOT NULL,
  `DiaChi` varchar(100) NOT NULL,
  `TongTien` varchar(50) NOT NULL,
  `NgayMua` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`MaDH`, `MaKH`, `DiaChi`, `TongTien`, `NgayMua`) VALUES
(7, 19, 'jkjk', '79000', '2023-08-30'),
(8, 19, 'gjgj', '183000', '2023-08-30'),
(9, 19, 'iouiouio', '414000', '2023-08-30'),
(10, 19, 'ffgffg', '61000', '2023-08-30'),
(11, 19, 'fgf', '32000', '2023-08-30'),
(15, 20, 'srhdh', '178000', '2023-09-01'),
(16, 20, 'dhdht', '79000', '2023-09-18'),
(17, 20, 'sgsrhs', '441000', '2023-09-19'),
(18, 20, 'tan thoi hiep', '232000', '2023-09-19'),
(19, 20, '1832a', '79000', '2023-09-19'),
(20, 20, 'thoi an', '46000', '2023-09-19'),
(21, 26, '9/2, P.Thoi An, Q.12', '232000', '2023-10-05'),
(22, 26, '1832a, KP2, P.Tan Thoi Hiep, Q.12', '89000', '2023-10-05'),
(23, 28, '656/68/46', '92000', '2023-10-11');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `MaKH` int(11) NOT NULL,
  `TenKH` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `DiaChi` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `SoDienThoai` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `MatKhau` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `MaQuyen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`MaKH`, `TenKH`, `DiaChi`, `SoDienThoai`, `Email`, `MatKhau`, `MaQuyen`) VALUES
(0, 'admin', '251efsfs', '0901457761', 'admin@gmail.com', 'admin123', 1),
(20, 'huyhoang', '251 nguyen thi dang', '0938250905', 'hoang@gmail.com', 'hoang123', 2),
(26, 'Phuc Thinh', '1834a, KP2, P.Tan Thoi Hiep, Q.12', '0327338010', 'phucthinh@gmail.com', '5a544cb73792667e8cbc7e5b9520c87726d86059489179964f', 2),
(28, 'Phuong', '656/68/46', '0971644420', 'phuong@gmail.com', 'phuong123', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaimonan`
--

CREATE TABLE `loaimonan` (
  `MaLoaiMonAn` int(11) NOT NULL,
  `TenLoaiMonAn` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `loaimonan`
--

INSERT INTO `loaimonan` (`MaLoaiMonAn`, `TenLoaiMonAn`) VALUES
(1, 'Ưu đãi'),
(2, 'Món mới'),
(3, 'Combo 1 Người'),
(4, 'Combo Nhóm'),
(5, 'Burger - Cơm - Mì Ý'),
(6, 'Thức Uống & Tráng Miệng'),
(7, 'Đơn hàng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `monan`
--

CREATE TABLE `monan` (
  `MaMonAn` int(11) NOT NULL,
  `TenMonAn` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `GiaMonAn` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `HinhAnhMonAn` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `MoTaMonAn` text NOT NULL,
  `MaLoaiMonAn` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `monan`
--

INSERT INTO `monan` (`MaMonAn`, `TenMonAn`, `GiaMonAn`, `HinhAnhMonAn`, `MoTaMonAn`, `MaLoaiMonAn`) VALUES
(1, 'Combo Gà Rán', '89000', 'https://static.kfcvietnam.com.vn/images/items/lg/D1-new.jpg?v=gqOojL', '2 Miếng Gà +1 Khoai tây chiên vừa / 2 Gà Miếng Nuggets + 1 Lipton vừa', 2),
(2, 'Combo Summer 159k', '159000', 'https://static.kfcvietnam.com.vn/images/items/lg/CBO159K_Summer.jpg?v=gqOojL', '3 Miếng Gà + 1 Gà Viên (Vừa)/ 1 Burger Tôm + 2 lon Pepsi + 2 Phiếu Cào', 1),
(3, '2 Bánh Khoai Tây Chiên', '17000', 'https://static.kfcvietnam.com.vn/images/items/lg/2-Hash-Browns.jpg?v=gqOojL', '2 Bánh Khoai Tây Chiên', 2),
(4, 'Combo Nhóm 2\r\n', '195000', 'https://static.kfcvietnam.com.vn/images/items/lg/D7-new.jpg?v=gqOojL', '4 Miếng Gà + 1 Khoai tây chiên lớn / 2 Thanh Bí Phô-mai + 2 Pepsi Lon', 4),
(5, 'Burger Gà Quay Flava\r\n', '55000\n', 'https://static.kfcvietnam.com.vn/images/items/lg/Burger-Flava.jpg?v=gqOojL', '1 Burger Gà Quay Flava', 5),
(6, 'Cơm Gà Teriyaki\r\n', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-Teriyaki.jpg?v=gqOojL', '1 Cơm Gà Teriyaki', 5),
(7, 'Mì Ý Xốt Cà Xúc Xích Gà Viên\r\n', '41000', 'https://static.kfcvietnam.com.vn/images/items/lg/MY-Y-POP.jpg?v=gqOojL', '1 Mì Ý Xốt Cà Xúc Xích Gà Viên', 5),
(8, '4 Bánh Trứng\r\n', '59000', 'https://static.kfcvietnam.com.vn/images/items/lg/4-eggtart.jpg?v=gqOojL', '4 Bánh Trứng', 6),
(9, 'Pepsi Lon\r\n', '17000', 'https://static.kfcvietnam.com.vn/images/items/lg/Pepsi-Can.jpg?v=gqOojL', 'Pepsi Lon', 6),
(10, 'Trà Chanh Lipton (Vừa)\r\n', '10000', 'https://static.kfcvietnam.com.vn/images/items/lg/Lipton.jpg?v=gqOojL', 'Trà Chanh Lipton (Vừa)', 6),
(11, '1 Bánh Trứng', '18000', 'https://static.kfcvietnam.com.vn/images/items/lg/1-eggtart.jpg?v=gqOojL', '1 Bánh Trứng', 6),
(12, '2 Viên Khoai Môn Kim Sa', '26000', 'https://static.kfcvietnam.com.vn/images/items/lg/2-taro.jpg?v=gqOojL', '2 Viên Khoai Môn Kim Sa', 6),
(13, '3 Viên Khoai Môn Kim Sa', '35000', 'https://static.kfcvietnam.com.vn/images/items/lg/3-taro.jpg?v=gqOojL', '3 Viên Khoai Môn Kim Sa', 6),
(14, '5 Viên Khoai Môn Kim Sa', '55000', 'https://static.kfcvietnam.com.vn/images/items/lg/5-taro.jpg?v=gqOojL', '5 Viên Khoai Môn Kim Sa', 6),
(15, '2 Thanh Bí Phô Mai', '29000', 'https://static.kfcvietnam.com.vn/images/items/lg/2-Pumcheese.jpg?v=gqOojL', '2 Thanh Bí Phô Mai', 6),
(16, '3 Thanh Bí Phô Mai', '39000', 'https://static.kfcvietnam.com.vn/images/items/lg/3-Pumcheese.jpg?v=gqOojL', '3 Thanh Bí Phô Mai', 6),
(17, '5 Thanh Bí Phô Mai', '59000', 'https://static.kfcvietnam.com.vn/images/items/lg/5-Pumcheese.jpg?v=gqOojL', '5 Thanh Bí Phô Mai', 6),
(18, '7Up Lon', '17000', 'https://static.kfcvietnam.com.vn/images/items/lg/7Up-Can.jpg?v=gqOojL', '7Up Lon', 6),
(19, 'Aquafina 500ml', '15000', 'https://static.kfcvietnam.com.vn/images/items/lg/Aquafina-500ml.jpg?v=gqOojL', 'Aquafina 500ml', 6),
(20, 'Pepsi Black Lime Lon', '17000', 'https://static.kfcvietnam.com.vn/images/items/lg/pepsi-lime-can.jpg?v=gqOojL', 'Pepsi Black Lime Lon', 6),
(21, '1 Burger Zinger', '55000', 'https://static.kfcvietnam.com.vn/images/items/lg/Burger-Zinger.jpg?v=gqOojL', '1 Burger Zinger', 5),
(22, '1 Burger Tôm', '45000', 'https://static.kfcvietnam.com.vn/images/items/lg/Burger-Shrimp.jpg?v=gqOojL', '1 Burger Tôm', 5),
(23, '1 Cơm Gà Xiên Que', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-Skewer.jpg?v=gqOojL', '1 Cơm Gà Xiên Que', 5),
(24, '1 Cơm Xiên Gà Tenderods', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-TENDERODS.jpg?v=gqOojL', '1 Cơm Xiên Gà Tenderods', 5),
(25, '1 Cơm Gà Bít-tết', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-Steak.jpg?v=gqOojL', '1 Cơm Gà Bít-tết', 5),
(26, '1 Mì Ý Xốt Cà Xúc Xích Gà Zinger', '61000', 'https://static.kfcvietnam.com.vn/images/items/lg/MY-Y-ZINGER.jpg?v=gqOojL', '1 Mì Ý Xốt Cà Xúc Xích Gà Zinger', 5),
(27, '1 Cơm Gà Rán', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-F.Chicken.jpg?v=gqOojL', '1 Cơm Gà Rán', 5),
(28, '1 Cơm Phi-lê Gà Quay', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-Flava.jpg?v=gqOojL', '1 Cơm Phi-lê Gà Quay', 5),
(29, 'Combo Nhóm 1', '175000', 'https://static.kfcvietnam.com.vn/images/items/lg/D6.jpg?v=gqOojL', '3 Miếng Gà + 1 Burger Zinger/Burger Tôm/Burger Phi-lê Gà Quay + 2 Lon Pepsi', 4),
(30, 'Combo Nhóm 3', '232000', 'https://static.kfcvietnam.com.vn/images/items/lg/D8-new.jpg?v=gqOojL', '5 Miếng Gà + 1 Popcorn (Vừa) / 4 Gà Miếng Nuggets+ 2 Pepsi Lon', 4),
(31, 'Combo Mì Ý', '89000', 'https://static.kfcvietnam.com.vn/images/items/lg/D3-new.jpg?v=gqOojL', '1 Mì Ý Xốt Cà Gà Viên + 1 Miếng Gà+ 1 Lon Pepsi Can', 3),
(32, 'Combo Salad Hạt', '79000', 'https://static.kfcvietnam.com.vn/images/items/lg/D4-new.jpg?v=gqOojL', '1 Miếng Gà + 1 Salad Hạt + 1 Lon Pepsi', 3),
(33, 'Combo Burger', '91000', 'https://static.kfcvietnam.com.vn/images/items/lg/D5.jpg?v=gqOojL', '1 Burger Zinger/Burger Gà Quay Flava/Burger Tôm + 1 Miếng Gà Rán + 1 Lon Pepsi', 3),
(34, '3 Bánh Khoai Tây Chiên', '25000', 'https://static.kfcvietnam.com.vn/images/items/lg/3-Hash-Browns.jpg?v=gqOojL', '3 Bánh Khoai Tây Chiên', 2),
(35, '4 Bánh Khoai Tây Chiên', '32000', 'https://static.kfcvietnam.com.vn/images/items/lg/4-Hash-Browns.jpg?v=gqOojL', '4 Bánh Khoai Tây Chiên', 2),
(36, '6 Bánh Khoai Tây Chiên', '47000', 'https://static.kfcvietnam.com.vn/images/items/lg/6-Hash-Browns.jpg?v=gqOojL', '6 Bánh Khoai Tây Chiên', 2),
(37, 'Combo Summer 79k', '79000', 'https://static.kfcvietnam.com.vn/images/items/lg/CBO79K_Summer.jpg?v=gqOojL', '1 Miếng Gà + 1 Gà Viên (Vừa)/ 1 Burger Tôm + 1 lon Pepsi + 1 Phiếu cào', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quyen`
--

CREATE TABLE `quyen` (
  `MaQuyen` int(11) NOT NULL,
  `TenQuyen` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `quyen`
--

INSERT INTO `quyen` (`MaQuyen`, `TenQuyen`) VALUES
(1, 'admin'),
(2, 'user');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`MaDH`),
  ADD KEY `MaMonAn` (`MaMonAn`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`MaDH`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MaKH`),
  ADD KEY `MaQuyen` (`MaQuyen`);

--
-- Chỉ mục cho bảng `loaimonan`
--
ALTER TABLE `loaimonan`
  ADD PRIMARY KEY (`MaLoaiMonAn`);

--
-- Chỉ mục cho bảng `monan`
--
ALTER TABLE `monan`
  ADD PRIMARY KEY (`MaMonAn`),
  ADD KEY `MaLoaiMonAn` (`MaLoaiMonAn`);

--
-- Chỉ mục cho bảng `quyen`
--
ALTER TABLE `quyen`
  ADD PRIMARY KEY (`MaQuyen`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  MODIFY `MaDH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `MaDH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MaKH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT cho bảng `loaimonan`
--
ALTER TABLE `loaimonan`
  MODIFY `MaLoaiMonAn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `monan`
--
ALTER TABLE `monan`
  MODIFY `MaMonAn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT cho bảng `quyen`
--
ALTER TABLE `quyen`
  MODIFY `MaQuyen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
