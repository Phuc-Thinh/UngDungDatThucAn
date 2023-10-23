<?php  
include "ketnoi.php";
$target_dir = "hinhanh/";  
$target_file_name = $target_dir .basename($_FILES["file"]["name"]);  

$query = "select max(MaMonAn) as MaMonAn from monan";
$data = mysqli_query($conn, $query);
$result = array();
while($row = mysqli_fetch_assoc($data)){
  $result[] = ($row);
} 



if ($result[0]['MaMonAn'] == null){
   $name = 1;
}else{
   $name = ++$result[0]['MaMonAn'];
}

$name = $name. ".jpg";  
$target_file_name = $target_dir .$name;

if (isset($_FILES["file"]))   
   {  
   if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name))  
      {  
         $arr = [
          'thanhcong' => true,
          'thongbao' => "Thêm thành công",
          "ten" => $name
        ];  
      }  
   else  
      {  
         $arr = [
          'thanhcong' => false,
          'thongbao' => "Thêm không thành công"
        ];  
      }  
   }  
else  
   {  
      $arr = [
          'thanhcong' => false,
          'thongbao' => "Lỗi"
        ]; 
   }  
   
   echo json_encode($arr);  
?>  