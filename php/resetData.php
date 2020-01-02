<?php

require 'config.php';

$id_toko = $_POST['id_toko'];
$pajak = $_POST['pajak'];

$flag = 1;

$sql = "DELETE from barang WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

$sql = "DELETE from hutang_piutang WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

$sql = "DELETE from kategori WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

$sql = "DELETE from list_hutang_piutang WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

$sql = "DELETE from list_pelsup WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

$sql = "DELETE from pesanan WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

$sql = "DELETE from transaksi WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

$sql = "DELETE from list_transaksi WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

$sql = "DELETE from akun_kas WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

$sql = "DELETE from kas WHERE id_toko=".$id_toko;
if ($conn->query($sql) === TRUE) {
    $flag = 1;
}else{
    $flag = 0;
}

if($flag==1){
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Gagal!'));
}

mysqli_close($conn);

?>