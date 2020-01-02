<?php
    $destination_path = getcwd().DIRECTORY_SEPARATOR;
    $destination_path =  substr($destination_path,0,(strlen($destination_path)-4));
    $file = $destination_path.'uploads/Screenshot from 2018-12-03 10-30-14.png';
    if (file_exists($file)) {
        unlink($file);
        echo 'sukses delete';
    }
?>