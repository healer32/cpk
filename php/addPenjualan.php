<?php

require 'config.php';

$tes = file_get_contents('php://input');
$json_data = json_decode($tes , true);

if(TRUE){
    echo json_encode(array('status_code'=>1, 'status'=>'Success!', 'result_tes'=>$json_data));
}else{
    echo json_encode(array('status_code'=>0, 'status'=>'Failed!'));
}
 
mysqli_close($conn);

?>