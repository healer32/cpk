<?php
require 'config.php';

$email = $_POST['email'];
$password = $_POST['password'];


$sql = "SELECT * FROM user WHERE email = '".$email."' AND password = '".$password."' AND id_toko>0"; 
$result = mysqli_query($conn,$sql);
$row = mysqli_fetch_array($result,MYSQLI_ASSOC);

if ($row!=NULL){
    $sql = "SELECT * FROM toko WHERE id = ".$row['id_toko']; 
    $result2 = mysqli_query($conn,$sql);
    $row2 = mysqli_fetch_array($result2,MYSQLI_ASSOC);
    echo json_encode(array('status_code'=>1, 'status'=>"User found!", 'result_login'=>array($row),'result_toko'=>array($row2)));
}else{
    echo json_encode(array('status_code'=>0, 'status'=>"User not found!"));
}

mysqli_close($conn);

?>