<?php
include "ketnoi.php";
$TenMonAn = isset($_POST['TenMonAn']) ? $_POST['TenMonAn'] : '';
$GiaMonAn = isset($_POST['GiaMonAn']) ? $_POST['GiaMonAn'] : '';
$HinhAnhMonAn = isset($_POST['HinhAnhMonAn']) ? $_POST['HinhAnhMonAn'] : '';
$MoTaMonAn = isset($_POST['MoTaMonAn']) ? $_POST['MoTaMonAn'] : '';
$MaLoaiMonAn = isset($_POST['MaLoaiMonAn']) ? $_POST['MaLoaiMonAn'] : '';
$MaMonAn= isset($_POST['MaMonAn']) ? $_POST['MaMonAn'] : '';


$query = "UPDATE `monan` SET `TenMonAn`='$TenMonAn',`GiaMonAn`='$GiaMonAn',`HinhAnhMonAn`='$HinhAnhMonAn',`MoTaMonAn`='$MoTaMonAn',
`MaLoaiMonAn`='$MaLoaiMonAn' WHERE `MaMonAn` = ".$MaMonAn; 
$data = mysqli_query($conn, $query);

if ($data == true) {
        $arr = [
          'thanhcong' => true,
          'thongbao' => "Sửa thành công"
        ];
}else {
  $arr = [
    'thanhcong' => false,
    'thongbao' => "Sửa không thành công"
  ];
}

print_r(json_encode($arr));

?>

