<?php

require 'config.php';

$id_toko = $_POST['id_toko'];


$sql = "SELECT * FROM list_transaksi JOIN transaksi ON list_transaksi.id_transaksi = transaksi.id WHERE transaksi.tipe=1 AND list_transaksi.id_toko=".$id_toko;
$result = mysqli_query($conn,$sql);
$rows = array();
$temp = array();
$rows2 = array();

while($row = $result->fetch_assoc()) {
    $sql3 = "SELECT * FROM pesanan WHERE id_transaksi=".$row['id']; 
    $result3 = mysqli_query($conn,$sql3);
    $row3 = mysqli_fetch_array($result3,MYSQLI_ASSOC);
    if($row3!=null){
        $sql2 = "SELECT * FROM barang WHERE id=".$row['id_barang']; 
        $result2 = mysqli_query($conn,$sql2);
        $row2 = mysqli_fetch_array($result2,MYSQLI_ASSOC);
        $row['kode_barang'] = $row2['kode'];
        $row['nama_barang'] = $row2['nama'];
        $row['kategori_barang'] = $row2['id_kategori'];
        $row['stok'] = $row2['stok'];
        array_push($rows, $row);
        if(!in_array($row['id'], $temp)){
            array_push($temp, $row['id']);
            array_push($rows2, $row3);
        }
    }
}

$array = array();

if ($rows!=NULL){
    echo json_encode(array('status_code'=>'1', 'status'=>"found!", 'result_pembelian'=>$rows,'result_pesanan'=>$rows2));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>"not found!", 'result_pembelian'=>$rows));
}
mysqli_close($conn);

?>