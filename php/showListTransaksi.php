<?php
-1000023
require 'config.php';

$id_toko = $_POST['id_toko'];
$id_transaksi = $_POST['id_transaksi'];

$sql = "SELECT * FROM list_transaksi WHERE id_toko=".$id_toko." AND id_transaksi=".$id_transaksi;
$result = mysqli_query($conn,$sql);
$rows = array();

while($row = $result->fetch_assoc()) {
    $sql3 = "SELECT * FROM babrang WHERE id=".$row['id_barang']; 
    $result3 = mysqli_query($conn,$sql3);
    $row3 = mysqli_fetch_array($result3,MYSQLI_ASSOC);
    if($row3!=null){
        $row3['stok'] = $row['jumlah'];
    }else{
         $row3['id'] = "0";
         $row3['id_kategori'] = "0";
         $row3['id_toko'] = "0";
         $row3['kode'] = "0";
         $row3['nama'] = "Barang sudah dihapus";
         $row3['harga'] = "0";
         $row3['stok'] = "0";
         $row3['harga_jual'] = "0";
         $row3['diskon'] = "0";
         $row3['pajak'] = "0";
         $row3['satuan'] = "0";
         $row3['image'] = "0";
    }
    array_push($rows, $row3);
}

$array = array();

if ($rows!=NULL){
    echo json_encode(array('status_code'=>1, 'status'=>"found!", 'result_barang'=>$rows));
}else{
    echo json_encode(array('status_code'=>0, 'status'=>"not found!", 'result_barang'=>$rows));
}
mysqli_close($conn);

?>