<?php 
	//getting the dboperation class
	require_once '../includes/DbOperation.php';

	function isTheseParametersAvailable($params){
		//assuming all parameters are available 
		$available = true; 
		$missingparams = ""; 
		
		foreach($params as $param){
			if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
				$available = false; 
				$missingparams = $missingparams . ", " . $param; 
			}
		}
		
		//if parameters are missing 
		if(!$available){
			$response = array(); 
			$response['error'] = true; 
			$response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';
			
			//displaying error
			echo json_encode($response);
			
			//stopping further execution
			die();
		}
	}
	
	//an array to display response
	$response = array();
	
	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
			
			case 'createcontact':
				//first check the parameters required for this request are available or not 
				isTheseParametersAvailable(array('name','email','contact','message'));
				
				//creating a new dboperation object
				$db = new DbOperation();
				//creating a new record in the database
				
				$result = $db->createContact(
					$_POST['name'],
					$_POST['email'],
					$_POST['contact'],
					$_POST['message']
				);
				
				//if the record is created adding success to response
				if($result){
					//record is created means there is no error
					$response['error'] = false; 

					//in message we have a success message
					$response['message'] = 'Message submitted successfully';

					//and we are getting all the heroes from the database in the response
					$response['contacts'] = $db->getContact();
				}else{

					//if record is not added that means there is an error 
					$response['error'] = true; 

					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 
			
			//the READ operation
			//if the call is getheroes
			case 'getcontact':
				$db = new DbOperation();
				$response['error'] = false; 
				$response['message'] = 'Request successfully completed';
				$response['contacts'] = $db->getContact();
			break; 
			
		}
		
	}else{
		//if it is not api call 
		//pushing appropriate values to response array 
		$response['error'] = true; 
		$response['message'] = 'Invalid API Call';
	}
	
	//displaying the response in json structure 
	echo json_encode($response);