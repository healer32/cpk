<?php
require 'config.php';

$id_barang = $_POST['id_barang'];

$sql = "DELETE FROM barang WHERE id=".$id_barang;
if ($conn->query($sql) === TRUE) {
    $last_id = mysqli_insert_id($conn);
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>