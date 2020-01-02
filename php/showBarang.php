<?php
require 'config.php';

$id_toko = $_POST['id_toko'];


$sql = "SELECT * FROM barang WHERE stok>-1 AND id_toko=".$id_toko; 
$result = mysqli_query($conn,$sql);
$rows = array();

while($row = $result->fetch_assoc()) {
    $row['harga_asli'] = $row['harga_jual'];
    $harga_jual = $row['harga_jual'] - ($row['harga_jual']*($row['diskon']/100));
    $row['harga_jual'] = $harga_jual;
    
    array_push($rows, $row);
}

$array = array();

if ($rows!=NULL){
    echo json_encode(array('status_code'=>1, 'status'=>"found!", 'result_barang'=>$rows));
}else{
    echo json_encode(array('status_code'=>0, 'status'=>"not found!", 'result_barang'=>$rows));
}
mysqli_close($conn);

?>