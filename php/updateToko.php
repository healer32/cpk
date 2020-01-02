<?php

require 'config.php';

$nama = $_POST['nama'];
$id = $_POST['id'];
$telephone = $_POST['telephone'];
$nama_pemilik = $_POST['nama_pemilik'];
$lokasi = $_POST['lokasi'];

$sql = "UPDATE toko SET nama='".$nama."', telephone='".$telephone."', nama_pemilik='".$nama_pemilik."', lokasi='".$lokasi."' WHERE id=".$id;
if ($conn->query($sql) === TRUE) {
    $last_id = mysqli_insert_id($conn);
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>