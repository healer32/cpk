<?php

require 'config.php';

$tanggal = date("Y-m-d");
$tanggalKas = date("Y-m-d H:i:s");
$list_barang = file_get_contents('php://input');
$json_list = json_decode($list_barang , true);
$flag = 1;
$flagJenis = 0;

if($json_list['tipe']=='1'){
    foreach($json_list['barangList'] as $item){
        $sql = "UPDATE barang SET stok=stok-".$item['stok']." WHERE id=".$item['id'];
        if ($conn->query($sql) === TRUE) {
            $flag = 1;
        }else{
            $flag = 0;
        }
    }
}else{
    foreach($json_list['barangList'] as $item){
        $sql = "UPDATE barang SET stok=stok+".$item['stok'].",harga='".$item['harga']."' WHERE id=".$item['id'];
        if ($conn->query($sql) === TRUE) {
            $flag = 1;
        }else{
            $flag = 0;
        }
    }
}

$last_id = 0;
if($flag==1){
    $sql = "INSERT INTO transaksi (id_toko,tanggal,modal,jual,jumlah,tipe) VALUES (".$json_list['id_toko'].",'".$tanggal."','".$json_list['modal']."','".$json_list['jual']."',".$json_list['jumlah'].",".$json_list['tipe'].")";
    if ($conn->query($sql) === TRUE) {
        $flagJenis = 1;
        $last_id = mysqli_insert_id($conn);
        if(($json_list['cash']!='1')){
            $jenis = 0;
            if($json_list['tipe']==1){
                $jenis = 0;
                $flagJenis = 2;
            }else{
                $jenis = 1;
                $flagJenis = 3;
            }
            $sql = "INSERT INTO hutang_piutang (id_toko, id_pelsup, id_transaksi,jumlah,keterangan,jenis,jatuh_tempo,status) VALUES (".$json_list['id_toko'].",".$json_list['id_pelsup'].",".$last_id.",".$json_list['jumlah'].",'".$json_list['keterangan']."',".$jenis.",'".$json_list['jatuh_tempo']."',0)";
            if ($conn->query($sql) === TRUE) {
                // echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
                $flag = 1;
            }else{
                $flag = 0;
            }
        }else if(($json_list['cash']=='1')){
            $flag = 1;
        }else{
            $flag = 1;
        }
    }else{
        $flag = 1;
    }
}else{
    $flag = 0;
}


if($flag==1){
    foreach($json_list['barangList'] as $item){
        $sql = "INSERT INTO list_transaksi (id_toko,id_transaksi,id_barang,jumlah,harga_jual,harga_dasar) VALUES (".$json_list['id_toko'].",".$last_id.",".$item['id'].",".$item['stok'].",'".$item['harga_jual']."','".$item['harga']."')";
        if ($conn->query($sql) === TRUE) {
            $flag = 1;
        }else{
            echo json_encode(array('status_code'=>'0', 'status'=>'Gagal list_transaksi'));
        }
    }
    if($flag==1){
        echo json_encode(array('status_code'=>'1', 'status'=>'Success!', 'id_transaksi'=>$last_id));
    }
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Gagal!'));
}
 
mysqli_close($conn);

?>