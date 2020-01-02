<?php

require 'config.php';

$id_toko = $_POST['id_toko'];
$diskon = $_POST['diskon'];

$sql = "UPDATE toko SET diskon=".$diskon." WHERE id=".$id_toko;
if ($conn->query($sql) === TRUE) {
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}

mysqli_close($conn);

?>