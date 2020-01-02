<?php

require 'config.php';

$nama = $_POST['nama'];
$id_toko = $_POST['id_toko'];

$sql = "INSERT INTO kategori (nama,id_toko) VALUES ('".$nama."',".$id_toko.")";
if ($conn->query($sql) === TRUE) {
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>