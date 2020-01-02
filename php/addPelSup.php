<?php

require 'config.php';

$nama = $_POST['nama'];
$id_toko = $_POST['id_toko'];
$telephone = $_POST['telephone'];
$alamat = $_POST['alamat'];
$tipe = $_POST['tipe'];


$sql = "INSERT INTO pelsup (nama,telephone,alamat,tipe) VALUES ('".$nama."','".$telephone."','".$alamat."',".$tipe.")";
if ($conn->query($sql) === TRUE) {
    $last_id = mysqli_insert_id($conn);
    $sql = "INSERT INTO list_pelsup (id_toko,id_pelsup) VALUES (".$id_toko.",".$last_id.")";
    if ($conn->query($sql) === TRUE) {
        echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
    }else{
        echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
    }
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>