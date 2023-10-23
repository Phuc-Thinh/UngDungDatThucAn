<?php
include "ketnoi.php";
$TenKH = isset($_POST['TenKH']) ? $_POST['TenKH'] : '';
$SoDienThoai = isset($_POST['SoDienThoai']) ? $_POST['SoDienThoai'] : '';
$Email = isset($_POST['Email']) ? $_POST['Email'] : '';
$DiaChi = isset($_POST['DiaChi']) ? $_POST['DiaChi'] : '';
$MatKhau = isset($_POST['MatKhau']) ? $_POST['MatKhau'] : '';
$MaQuyen = isset($_POST['MaQuyen']) ? $_POST['MaQuyen'] : '';
$MaKH = isset($_POST['MaKH']) ? $_POST['MaKH'] : '';
$mkHam=hash_hmac("sha512", $MatKhau, "xinchao");

$query = "UPDATE `khachhang` SET `TenKH`='$TenKH',`SoDienThoai`='$SoDienThoai',
`Email`='$Email',`DiaChi`='$DiaChi',`MatKhau`='$mkHam' ,`MaQuyen`='$MaQuyen' WHERE `MaKH` = ".$MaKH; 
$data = mysqli_query($conn, $query);
if ($data == true) {
        $arr = [
          'thanhcong' => true,
          'thongbao' => "Sửa thành công!"
        ];
}else {
  $arr = [
    'thanhcong' => false,
    'thongbao' => "Sửa không thành công!"
  ];
}

print_r(json_encode($arr));

?>

