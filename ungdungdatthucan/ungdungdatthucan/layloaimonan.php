<?php
include "ketnoi.php";
$query = "SELECT * FROM `loaimonan`";
$data = mysqli_query($conn, $query);
$result = array();
while($row = mysqli_fetch_assoc($data)){
  $result[] = ($row);
}

if(!empty($result)){
  $arr = [
    'success' => true,
    'message' => "Lấy loại món ăn thành công !",
    'result' => $result
  ];
}else{
  $arr = [
    'success' => false,
    'message' => "Lấy loại món ăn thất bại!",
    'result' => $result
  ];
}
print_r(json_encode($arr));
?>

