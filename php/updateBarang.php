<?php

require 'config.php';

$id = $_POST['id'];
$id_kategori = $_POST['id_kategori'];
$nama = $_POST['nama'];
$kode = $_POST['kode'];
$harga = $_POST['harga'];
$stok = $_POST['stok'];
$harga_jual = $_POST['harga_jual'];
$diskon = $_POST['diskon'];
$satuan = $_POST['satuan'];
$image = $_POST['image'];

$deleteImage = 0;

$sql = "SELECT * FROM barang WHERE id=".$id; 
$result = mysqli_query($conn,$sql);
$row = mysqli_fetch_array($result,MYSQLI_ASSOC);

if ($row!=NULL){
    if($row['image']==$image){
        $deleteImage = 1;
    }else{
        $destination_path = getcwd().DIRECTORY_SEPARATOR;
        $destination_path =  substr($destination_path,0,(strlen($destination_path)-4));
        $target_file = preg_split("/kasir/", $row['image']);
        $target_file = $destination_path.substr($target_file[2],1);
        if (file_exists($target_file)) {
            unlink($target_file);
        }
        $deleteImage = 0;
    }
}

$sql = "UPDATE barang SET id_kategori=".$id_kategori.", nama='".$nama."', kode='".$kode."', harga='".$harga."', stok=".$stok.", harga_jual='".$harga_jual."', diskon=".$diskon.", satuan='".$satuan."',image='".$image."' WHERE id=".$id;
if ($conn->query($sql) === TRUE) {
    $last_id = mysqli_insert_id($conn);
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>