<?php
include "ketnoi.php";
$query = "SELECT * FROM `khachhang` ORDER BY MaKH DESC";
$data = mysqli_query($conn, $query);

if (!$data) {
  $error = mysqli_error($conn);
  $arr = [
    'success' => false,
    'message' => "Truy vấn không thành công: $error",
    'result' => []
  ];
} else {
  $result = [];
  while ($row = mysqli_fetch_assoc($data)) {
    $result[] = $row;
  }

  if (!empty($result)) {
    $arr = [
      'success' => true,
      'message' => "Lấy tất cả khách hàng thành công!!!",
      'result' => $result
    ];
  } else {
    $arr = [
      'success' => false,
      'message' => "Lấy tất cả khách hàng không thành công!!!",
      'result' => $result
    ];
  }
}

print_r(json_encode($arr));
?>
