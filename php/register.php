<?php

require 'config.php';

$nama = $_POST['nama'];
$email = $_POST['email'];
$telephone = $_POST['telephone'];
$alamat = $_POST['alamat'];
$password = $_POST['password'];


$sql2 = "SELECT * FROM user WHERE email='".$email."'"; 
$result2 = mysqli_query($conn,$sql2);
$rows2 = mysqli_fetch_array($result2,MYSQLI_ASSOC);

if (($rows2 == NULL)&&(($email!="")||($password!=""))){
    $sql = "INSERT INTO user (id_toko,nama,email,telephone,alamat,password,tipe) VALUES (0,'".$nama."','".$email."','".$telephone."','".$alamat."','".$password."',0)";
    $result = array();
    if ($conn->query($sql) === TRUE) {
        $last_id = mysqli_insert_id($conn);
        echo json_encode(array('status_code'=>'1', 'status'=>'Register Success!','result_regis'=>$last_id));
        
    }else{
        echo json_encode(array('status_code'=>'0', 'status'=>'Register Failed!'));
    }
}else{
    echo json_encode(array('status_code'=>'2', 'status'=>'Email sudah terdaftar!'));
}
 
mysqli_close($conn);

?>