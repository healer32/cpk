<?php

require 'config.php';

$nama = $_POST['nama'];
$id_user = $_POST['id_user'];
$telephone = $_POST['telephone'];
$nama_pemilik = $_POST['nama_pemilik'];
$lokasi = $_POST['lokasi'];


if ($id_user>0){
    $sql = "INSERT INTO toko (nama,nama_pemilik,lokasi,telephone,pajak,diskon) VALUES ('".$nama."','".$nama_pemilik."','".$lokasi."','".$telephone."',0,0)";
    $result = array();
    if ($conn->query($sql) === TRUE) {
        $last_id = mysqli_insert_id($conn);
        $sql = "UPDATE user SET id_toko=".$last_id." WHERE id=".$id_user;
        if ($conn->query($sql) === TRUE) {
            echo json_encode(array('status_code'=>'1', 'status'=>'Register Success!'));
        }else{
            echo json_encode(array('status_code'=>'0', 'status'=>'Register Failed!'));
        }
    }else{
        echo json_encode(array('status_code'=>'0', 'status'=>'Register Failed!'));
    }
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Register Failed!'));
}
 
mysqli_close($conn);

?>