<?php
require 'config.php';

$id = $_POST['id'];


$sql = "SELECT * FROM toko WHERE id=".$id; 
$result = mysqli_query($conn,$sql);
$rows = array();

while($row = $result->fetch_assoc()) {
    array_push($rows, $row);
}

$array = array();

if ($rows!=NULL){
    echo json_encode(array('status_code'=>1, 'status'=>"found!", 'result_toko'=>$rows));
}else{
    echo json_encode(array('status_code'=>0, 'status'=>"not found!", 'result_toko'=>$rows));
}
mysqli_close($conn);

?>