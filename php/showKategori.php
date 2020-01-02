<?php

require 'config.php';

$id_toko = $_POST['id_toko'];


$sql = "SELECT * FROM kategori WHERE id_toko=".$id_toko; 
$result = mysqli_query($conn,$sql);
$rows = array();

while($row = $result->fetch_assoc()) {
    array_push($rows, $row);
}

$array = array();

if ($rows!=NULL){
    echo json_encode(array('status_code'=>'1', 'status'=>"found!", 'result_kategori'=>$rows));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>"not found!", 'result_kategori'=>$rows));
}
mysqli_close($conn);

?>