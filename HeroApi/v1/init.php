<?php

$server_name = "localhost";
$mysql_user = "root";
$mysql_pass = "";
$db_name = "healthdb";

$conn = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);

if(!$conn){
//echo "Connection Error..." . mysqli_connect_error();
}else{

}


?>