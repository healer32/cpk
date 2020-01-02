<?php
require 'config.php';

$id_kategori = $_POST['id_kategori'];

$sql = "SELECT * FROM barang WHERE id_kategori = ".$id_kategori; 
$result = mysqli_query($conn,$sql);
$row = mysqli_fetch_array($result,MYSQLI_ASSOC);

if ($row==NULL){
    $sql = "DELETE FROM kategori WHERE id=".$id_kategori;
    if ($conn->query($sql) === TRUE) {
        $last_id = mysqli_insert_id($conn);
        echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
    }else{
        echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
    }
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>