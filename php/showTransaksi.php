<?php

require 'config.php';

$id_toko = $_POST['id_toko'];


$sql = "SELECT * FROM transaksi WHERE id_toko=".$id_toko;
$result = mysqli_query($conn,$sql);
$rows = array();

while($row = $result->fetch_assoc()) {
    $sql3 = "SELECT * FROM status_transaksi WHERE id_transaksi=".$row['id']; 
    $result3 = mysqli_query($conn,$sql3);
    $row3 = mysqli_fetch_array($result3,MYSQLI_ASSOC);
    if($row3!=null){
        $row['hutpit'] = $row3['jenis'];
        $row['status'] = $row3['status'];
    }else{
        $row['hutpit'] = '2';
        $row['status'] = '2';
    }
    array_push($rows, $row);
}

$array = array();

if ($rows!=NULL){
    echo json_encode(array('status_code'=>'1', 'status'=>"found!", 'result_transaksi'=>$rows));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>"not found!", 'result_transaksi'=>$rows));
}
mysqli_close($conn);

?>