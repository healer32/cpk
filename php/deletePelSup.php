<?php
require 'config.php';

$id_pelsup = $_POST['id_pelsup'];

$sql = "DELETE FROM pelsup WHERE id=".$id_pelsup;
if ($conn->query($sql) === TRUE) {
    $last_id = mysqli_insert_id($conn);
    echo json_encode(array('status_code'=>'1', 'status'=>'Success!'));
}else{
    echo json_encode(array('status_code'=>'0', 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>