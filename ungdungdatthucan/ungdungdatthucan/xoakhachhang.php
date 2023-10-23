<?php
include "ketnoi.php";
$MaKH = isset($_POST['MaKH']) ? $_POST['MaKH'] : '';
$query =  "DELETE FROM `khachhang` WHERE `MaKH` =" .$MaKH;
$data = mysqli_query($conn, $query);


if ($data == true) {
        $arr = [
          'thanhcong' => true,
          'thongbao' => "Xóa thành công!"
        ];
}else {
  $arr = [
    'thanhcong' => false,
    'thongbao' => "Xóa không thành công!"
  ];
}

print_r(json_encode($arr));

?>

