<?php

require 'config.php';

$nama = $_POST['nama'];
$id_kategori = $_POST['id_kategori'];
$id_toko = $_POST['id_toko'];
$kode = $_POST['kode'];
$harga = $_POST['harga'];
$stok = $_POST['stok'];
$harga_jual = $_POST['harga_jual'];
$diskon = $_POST['diskon'];
$satuan = $_POST['satuan'];
$image = $_POST['image'];


$sql = "INSERT INTO barang (id_kategori,id_toko,kode,nama,harga,stok,harga_jual,diskon,pajak,satuan,image) VALUES (".$id_kategori.",".$id_toko.",'".$kode."','".$nama."','".$harga."','".$stok."','".$harga_jual."',".$diskon.",0,'".$satuan."','".$image."')";
if ($conn->query($sql) === TRUE) {
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>