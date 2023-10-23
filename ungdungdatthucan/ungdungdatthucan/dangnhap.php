<?php
include "ketnoi.php";

$Email = isset($_POST['Email']) ? $_POST['Email'] : '';
$MatKhau = isset($_POST['MatKhau']) ? $_POST['MatKhau'] : '';

// Prepare the query using prepared statements
$query = 'SELECT * FROM `khachhang` WHERE `Email` ="'.$Email.'" AND `MatKhau` ="'.$MatKhau.'"';
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
            'message' => "Login Successful!!!",
            'result' => $result
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "Login Unsuccessful!!!",
            'result' => $result
        ];
    }

    print_r(json_encode($arr));
}
?>
