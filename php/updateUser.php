<?php

require 'config.php';

$id = $_POST['id'];
$nama = $_POST['nama'];
$email = $_POST['email'];
$telephone = $_POST['telephone'];
$alamat = $_POST['alamat'];
$password = $_POST['password'];

$sql = "UPDATE user SET nama='".$nama."', email='".$email."', telephone='".$telephone."', alamat='".$alamat."', password='".$password."' WHERE id=".$id;
if ($conn->query($sql) === TRUE) {
    $last_id = mysqli_insert_id($conn);
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>