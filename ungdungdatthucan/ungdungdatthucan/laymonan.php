<?php
include "ketnoi.php";
$query = "SELECT * FROM `monan` ORDER BY MaMonAn DESC";
$data = mysqli_query($conn, $query);

if (!$data) {
  $error = mysqli_error($conn);
  $arr = [
    'success' => false,
    'message' => "Query execution failed: $error",
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
      'message' => "thanh cong",
      'result' => $result
    ];
  } else {
    $arr = [
      'success' => false,
      'message' => "khong thanh cong",
      'result' => $result
    ];
  }
}

print_r(json_encode($arr));
?>
