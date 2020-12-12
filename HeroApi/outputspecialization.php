<?php

//require_once ‘query.php’;
//getting the dboperation class
	require_once '../includes/DbOperation.php';

$queryObject = new Query();

$queryObject->getAllCountries();

?>