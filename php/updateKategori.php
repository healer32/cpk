<?php

require 'config.php';

$id = $_POST['id'];
$nama = $_POST['nama'];

$sql = "UPDATE kategori SET nama='".$nama."' WHERE id=".$id;
if ($conn->query($sql) === TRUE) {
    $last_id = mysqli_insert_id($conn);
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>