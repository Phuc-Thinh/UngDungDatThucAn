<?php
include "ketnoi.php";
$TenKH = isset($_POST['TenKH']) ? $_POST['TenKH'] : '';
$SoDienThoai = isset($_POST['SoDienThoai']) ? $_POST['SoDienThoai'] : '';
$Email = isset($_POST['Email']) ? $_POST['Email'] : '';
$DiaChi = isset($_POST['DiaChi']) ? $_POST['DiaChi'] : '';
$MatKhau = isset($_POST['MatKhau']) ? $_POST['MatKhau'] : '';
$MaQuyen = isset($_POST['MaQuyen']) ? $_POST['MaQuyen'] : '';
$mkMau=hash_hmac("sha512", $MatKhau, "xinchao");

$query = "INSERT INTO khachhang (TenKH, SoDienThoai, Email, DiaChi, MatKhau,
 MaQuyen) VALUES ('$TenKH', 
'$SoDienThoai', '$Email', '$DiaChi', '$mkMau','$MaQuyen')"; 
$data = mysqli_query($conn, $query);

if ($data == true) {
        $arr = [
          'thanhcong' => true,
          'thongbao' => "Thêm khách hàng thành công!"
        ];
}else {
  $arr = [
    'thanhcong' => false,
    'thongbao' => "Thêm khách hàng không thành công"
  ];
}

print_r(json_encode($arr));

?>

