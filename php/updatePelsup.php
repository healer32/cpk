<?php

require 'config.php';

$nama = $_POST['nama'];
$id_pelsup = $_POST['id_pelsup'];
$telephone = $_POST['telephone'];
$alamat = $_POST['alamat'];


$sql = "UPDATE pelsup SET nama='".$nama."', telephone='".$telephone."', alamat='".$alamat."' WHERE id=".$id_pelsup;
if ($conn->query($sql) === TRUE) {
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>