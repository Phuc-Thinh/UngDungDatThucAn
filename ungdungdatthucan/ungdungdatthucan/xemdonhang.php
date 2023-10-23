<?php
include "ketnoi.php";
$MaKH = isset($_POST['MaKH']) ? (int)$_POST['MaKH'] : 0; // Sửa tên biến MaKH
$MaQuyen = isset($_POST['MaQuyen']) ? (int)$_POST['MaQuyen'] : 0; // Sửa tên biến MaQuyen

if ($MaKH === 0 && $MaQuyen === 1) {
    // User có user_id = 0 và id_role = 1, cho phép truy cập tất cả đơn hàng
    $query = 'SELECT donhang.MaDH, donhang.MaKH, donhang.DiaChi, donhang.TongTien, donhang.NgayMua, khachhang.TenKH FROM `donhang` INNER JOIN khachhang ON donhang.MaKH = khachhang.MaKH ORDER BY donhang.MaDH DESC';
} else {
    $query = 'SELECT donhang.MaDH, donhang.MaKH, donhang.DiaChi, donhang.TongTien, donhang.NgayMua, khachhang.TenKH FROM `donhang` INNER JOIN khachhang ON donhang.MaKH = khachhang.MaKH WHERE donhang.MaKH = ? ORDER BY donhang.MaDH DESC';
}

$stmt = mysqli_prepare($conn, $query);

if ($stmt) {
    if ($MaKH !== 0) {
        mysqli_stmt_bind_param($stmt, "i", $MaKH);
    }

    mysqli_stmt_execute($stmt);
    $data = mysqli_stmt_get_result($stmt);

    $result = array();

    while ($row = mysqli_fetch_assoc($data)) {
        $truyvan = 'SELECT * FROM `chitietdonhang` INNER JOIN monan ON chitietdonhang.MaMonAn = monan.MaMonAn WHERE chitietdonhang.MaDH = ?';
        $stmt1 = mysqli_prepare($conn, $truyvan);

        if ($stmt1) {
            mysqli_stmt_bind_param($stmt1, "i", $row['MaDH']);
            mysqli_stmt_execute($stmt1);
            $data1 = mysqli_stmt_get_result($stmt1);

            $item = array();
            while ($row1 = mysqli_fetch_assoc($data1)) {
                $item[] = $row1;
            }

            $row['item'] = $item;
            $result[] = $row;

            mysqli_stmt_close($stmt1);
        }
        if ($row['NgayMua'] == '0000-00-00') {
            $row['NgayMua'] = null;
        }
    }

    mysqli_stmt_close($stmt);
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

print_r(json_encode($arr));
?>
