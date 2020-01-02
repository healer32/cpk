<?php

require 'config.php';

$id_toko = $_POST['id_toko'];


$sql = "SELECT * FROM list_transaksi JOIN transaksi ON list_transaksi.id_transaksi = transaksi.id WHERE transaksi.tipe=0 AND list_transaksi.id_toko=".$id_toko;
$result = mysqli_query($conn,$sql);
$rows = array();

while($row = $result->fetch_assoc()) {
    $sql2 = "SELECT * FROM barang WHERE id=".$row['id_barang']; 
    $result2 = mysqli_query($conn,$sql2);
    $row2 = mysqli_fetch_array($result2,MYSQLI_ASSOC);
    $row['kode_barang'] = $row2['kode'];
    $row['nama_barang'] = $row2['nama'];
    $row['kategori_barang'] = $row2['id_kategori'];
    $row['stok'] = $row2['stok'];
    array_push($rows, $row);
}

$array = array();

if ($rows!=NULL){
    echo json_encode(array('status_code'=>'1', 'status'=>"found!", 'result_pembelian'=>$rows));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>"not found!", 'result_pembelian'=>$rows));
}
mysqli_close($conn);

?>