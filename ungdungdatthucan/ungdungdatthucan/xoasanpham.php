<?php
include "ketnoi.php";
$MaMonAn = isset($_POST['MaMonAn']) ? $_POST['MaMonAn'] : '';



$query = "DELETE FROM `monan` WHERE `MaMonAn`=".$MaMonAn; 
$data = mysqli_query($conn, $query);

if ($data == true) {
        $arr = [
          'thanhcong' => true,
          'thongbao' => "Xóa thành công"
        ];
}else {
  $arr = [
    'thanhcong' => false,
    'thongbao' => "Xóa không thành công"
  ];
}

print_r(json_encode($arr));

?>

