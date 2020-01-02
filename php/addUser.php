<?php

require 'config.php';

$id_toko = $_POST['id_toko'];
$nama = $_POST['nama'];
$telephone = $_POST['telephone'];
$email = $_POST['email'];
$alamat = $_POST['alamat'];
$tipe = $_POST['tipe'];
$password = $_POST['password'];


// $sql2 = "SELECT * FROM user WHERE email='".$email."'"; 
// $result2 = mysqli_query($conn,$sql2);
// $rows2 = mysqli_fetch_array($result2,MYSQLI_ASSOC);

// if (($rows2 == NULL)&&(($email!="")||($password!=""))){
    $sql = "INSERT INTO user (id_toko,nama,email,telephone,alamat,password,tipe) VALUES (".$id_toko.",'".$nama."','".$email."','".$telephone."','".$alamat."','".$password."',".$tipe.")";
    $result = array();
    if ($conn->query($sql) === TRUE) {
        // $last_id = mysqli_insert_id($conn);
        echo json_encode(array('status_code'=>'1', 'status'=>'Register Success!'));
        
    }else{
        echo json_encode(array('status_code'=>'0', 'status'=>'Register Failed!'));
    }
// }else{
//     echo json_encode(array('status_code'=>'2', 'status'=>'Email sudah terdaftar!'));
// }
 
mysqli_close($conn);

?>