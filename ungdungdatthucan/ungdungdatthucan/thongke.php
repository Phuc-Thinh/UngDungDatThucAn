<?php
include "ketnoi.php";
$query = "SELECT chitietdonhang.MaMonAn, monan.TenMonAn, COUNT(`SoLuong`) AS Tong FROM `chitietdonhang` INNER JOIN monan ON monan.MaMonAn = chitietdonhang.MaMonAn GROUP BY chitietdonhang.MaMonAn";
$data = mysqli_query($conn, $query);

if (!$data) {
  $error = mysqli_error($conn);
  $arr = [
    'thanhcong' => false,
    'thongbao' => "Query execution failed: $error",
    'ketqua' => []
  ];
} else {
  $result = [];
  while ($row = mysqli_fetch_assoc($data)) {
    $result[] = $row;
  }

  if (!empty($result)) {
    $arr = [
      'thanhcong' => true,
      'thongbao' => "thanh cong",
      'ketqua' => $result
    ];
  } else {
    $arr = [
      'thanhcong' => false,
      'thongbao' => "khong thanh cong",
      'ketqua' => $result
    ];
  }
}

print_r(json_encode($arr));
?>
