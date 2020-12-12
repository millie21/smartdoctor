<?php

//include_once ‘database.php’;

//getting the dboperation class
	require_once '../includes/DbOperation.php';

class Query{

private $tables;

public function __constructor(){

$this->tables = “rating”;

}

public function getAllCountries(){

$countries = array();

$com = new database();

$sql =”select * from countries”;

$result = mysqli_query($com->getDb(), $sql);

$rows = mysqli_num_rows($result);

if($rows > 0){

while ($row = $result->fetch_assoc()) {

$countries[] = $row;

}

echo json_encode($countries, JSON_PRETTY_PRINT);

}

}

}

?>