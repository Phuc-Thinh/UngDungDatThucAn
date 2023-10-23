<?php
include "ketnoi.php";
$page = isset($_POST['page']) ? $_POST['page'] : 1;
$total = 5;
$pos = ($page - 1) * $total;
$MaLoaiMonAn = isset($_POST['MaLoaiMonAn']) ? $_POST['MaLoaiMonAn'] : '';

$query = 'SELECT * FROM `monan` WHERE `MaLoaiMonAn` = \'' . $MaLoaiMonAn . '\' LIMIT ' . $pos . ',' . $total;

$data = mysqli_query($conn, $query);

if (!$data) {
    echo "Lỗi truy vấn: " . mysqli_error($conn);
    // Hoặc ghi log lỗi, xử lý lỗi phù hợp...
} else {
    $result = array();
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

    print_r(json_encode($arr));
}
?>
