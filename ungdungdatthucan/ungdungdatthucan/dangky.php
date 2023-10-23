<?php
include "ketnoi.php";
$TenKH = isset($_POST['TenKH']) ? $_POST['TenKH'] : '';
$DiaChi = isset($_POST['DiaChi']) ? $_POST['DiaChi'] : '';
$SoDienThoai = isset($_POST['SoDienThoai']) ? $_POST['SoDienThoai'] : '';
$Email = isset($_POST['Email']) ? $_POST['Email'] : '';
$MatKhau = isset($_POST['MatKhau']) ? $_POST['MatKhau'] : '';
//check data
$query = 'SELECT * FROM `khachhang` WHERE `Email` = "'.$Email.'"';
$query1 = 'SELECT * FROM `khachhang` WHERE `SoDienThoai` = "'.$SoDienThoai.'"';
$data = mysqli_query($conn, $query);
$data1 = mysqli_query($conn, $query1);
$numrow = mysqli_num_rows($data);
$numrow1 = mysqli_num_rows($data1);
$MaQuyen = 2;
if ($numrow > 0){
    $arr = [
        'success' => false, 'message' => "Email da ton tai!"
    ];
}else if($numrow1 > 0){
    $arr = [
        'success' => false, 'message' => "So dien thoai da ton tai!"
    ];
}
else{
    //insert
        $query = 'INSERT INTO `khachhang`(`TenKH`, `DiaChi`, `SoDienThoai`, `Email`, `MatKhau`, `MaQuyen`) VALUES ("'.$TenKH.'","'.$DiaChi.'","'.$SoDienThoai.'", "'.$Email.'","'.$MatKhau.'","'.$MaQuyen.'")';
        $data = mysqli_query($conn, $query);

        if($data == true){
            $arr = [
        'success' => true, 
        'message' =>"Dang ky thanh cong!"        
            ];
            }else{
            $arr = [
        'success' => false, 
        'message' =>"Dang ky khong thanh cong!"
                
            ];       
    }
}
print_r(json_encode($arr));
?>