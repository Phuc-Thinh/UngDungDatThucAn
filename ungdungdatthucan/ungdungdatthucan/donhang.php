<?php
include "ketnoi.php";
$TongTien = isset($_POST['TongTien']) ? $_POST['TongTien'] : '';
$MaKH = isset($_POST['MaKH']) ? $_POST['MaKH'] : '';
$DiaChi = isset($_POST['DiaChi']) ? $_POST['DiaChi'] : '';
$NgayMua = isset($_POST['NgayMua']) ? $_POST['NgayMua'] : '';
$ChiTiet = isset($_POST['ChiTiet']) ? $_POST['ChiTiet'] : '';

$query = 'INSERT INTO `donhang`(`MaKH`, `DiaChi`, `TongTien`, `NgayMua`) VALUES ('.$MaKH.',"'.$DiaChi.'", "'.$TongTien.'","'.$NgayMua.'")';

$data = mysqli_query($conn, $query);
if ($data == true) 
{
   $query = 'SELECT `MaDH` AS ThongTinMaDonHang FROM `donhang` WHERE `MaKH` = '.$MaKH.' ORDER BY `MaDH` DESC LIMIT 1';
   $data = mysqli_query($conn, $query);
   while ($row = mysqli_fetch_assoc($data))
   {
    $MaDH = ($row);
   }
   if (!empty($MaDH)) 
   {
       $ChiTiet = json_decode($ChiTiet, true);
       foreach ($ChiTiet as $key => $value) {
           $truyvan = 'INSERT INTO `chitietdonhang`(`MaDH`, `MaMonAn`, `SoLuong`, `Gia`) VALUES ('.$MaDH["ThongTinMaDonHang"].','.$value["MaMonAn"].','.$value["SoLuong"].',"'.$value["GiaMonAn"].'")';
            $data = mysqli_query($conn, $truyvan);
            if ($data == true) 
            {
                $arr = [
                    'success' => true,
                    'message' => "thanh cong",
                ];
            }
            else
            {
                $arr = [
                    'success' => false,
                    'message' => "khong thanh cong",
                ];
            }
       }
   }
}
else
{
    $arr = [
        'success' => false,
        'message' => "khong thanh cong",
    ];
}
print_r(json_encode($arr));
?>
