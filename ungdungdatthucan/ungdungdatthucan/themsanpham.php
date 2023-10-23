<?php
include "ketnoi.php";
$TenMonAn = isset($_POST['TenMonAn']) ? $_POST['TenMonAn'] : '';
$GiaMonAn = isset($_POST['GiaMonAn']) ? $_POST['GiaMonAn'] : '';
$HinhAnhMonAn = isset($_POST['HinhAnhMonAn']) ? $_POST['HinhAnhMonAn'] : '';
$MoTaMonAn = isset($_POST['MoTaMonAn']) ? $_POST['MoTaMonAn'] : '';
$MaLoaiMonAn = isset($_POST['MaLoaiMonAn']) ? $_POST['MaLoaiMonAn'] : '';


$query = "INSERT INTO monan (TenMonAn, GiaMonAn, HinhAnhMonAn, MoTaMonAn, MaLoaiMonAn) 
VALUES ('$TenMonAn', '$GiaMonAn', '$HinhAnhMonAn', '$MoTaMonAn', '$MaLoaiMonAn')"; 
$data = mysqli_query($conn, $query);

if ($data == true) {
        $arr = [
          'thanhcong' => true,
          'thongbao' => "Thêm thành công"
        ];
}else {
  $arr = [
    'thanhcong' => false,
    'thongbao' => "Thêm không thành công"
  ];
}

print_r(json_encode($arr));

?>

