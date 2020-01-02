<?php

require 'config.php';

$id_toko = $_POST['id_toko'];


/*$sql = "SELECT * FROM pelsup NATURAL JOIN list_pelsup WHERE list_pelsup.id_toko=".$id_toko; */
$sql = "SELECT * FROM list_pelsup JOIN pelsup ON list_pelsup.id_pelsup = pelsup.id WHERE list_pelsup.id_toko=".$id_toko;
$result = mysqli_query($conn,$sql);
$rows = array();

while($row = $result->fetch_assoc()) {
    array_push($rows, $row);
}

$array = array();

if ($rows!=NULL){
    echo json_encode(array('status_code'=>'1', 'status'=>"found!", 'result_pelsup'=>$rows));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>"not found!", 'result_pelsup'=>$rows));
}
mysqli_close($conn);

?>