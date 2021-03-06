<?php 
	//getting the dboperation class
	require_once '../includes/DbOperation.php';

	//function validating all the paramters are available
	//we will pass the required parameters to this function 
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
	
	//if it is an api call 
	//that means a get parameter named api call is set in the URL 
	//and with this parameter we are concluding that it is an api call
	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
			
			//the CREATE operation
			//if the api call value is 'createissue'
			//we will create a record in the database
			case 'createprescription':
				//first check the parameters required for this request are available or not 
				 isTheseParametersAvailable(array('doctor','disease','status','prescription'));
				
				//creating a new dboperation object
				$db = new DbOperation();
				
				//creating a new record in the database
				$result = $db->createPrescription(
				//	$_POST['appdate'],
					
  					$_POST['pid'],
  					$_POST['doctor'],
  					$_POST['fname'],
  					$_POST['lname'],
  					$_POST['disease'],
  					$_POST['status'],
  					$_POST['prescription']

				);
				
				//if the record is created adding success to response
				if($result){
					//record is created means there is no error
					$response['error'] = false; 

					//in message we have a success message
					$response['message'] = 'Prescription submitted successfully';

					//and we are getting all the heroes from the database in the response
					$response['prescriptions'] = $db->getPrescription();
				}else{

					//if record is not added that means there is an error 
					$response['error'] = true; 

					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 
			
			//the READ operation
			//if the call is getheroes
			case 'getprescription':
				$db = new DbOperation();
				$response['error'] = false; 
				$response['message'] = 'Request successfully completed';
				$response['prescriptions'] = $db->getPrescription();
			break; 
			
			
			//the UPDATE operation
			// case 'updatehero':
			// 	isTheseParametersAvailable(array('id','firstname','lastname','email','phonenumber'));
			// 	$db = new DbOperation();
			// 	$result = $db->updateHero(
			// 		$_POST['id'],
			// 		$_POST['firstname'],
			// 		$_POST['lastname'],
			// 		$_POST['email'],
			// 		$_POST['phonenumber']
			// 	);
				
			// 	if($result){
			// 		$response['error'] = false; 
			// 		$response['message'] = 'Hero updated successfully';
			// 		$response['heroes'] = $db->getHeroes();
			// 	}else{
			// 		$response['error'] = true; 
			// 		$response['message'] = 'Some error occurred please try again';
			// 	}
			// break; 
			
			//the delete operation
			// case 'deletehero':

			// 	//for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
			// 	if(isset($_GET['id'])){
			// 		$db = new DbOperation();
			// 		if($db->deleteHero($_GET['id'])){
			// 			$response['error'] = false; 
			// 			$response['message'] = 'Hero deleted successfully';
			// 			$response['heroes'] = $db->getHeroes();
			// 		}else{
			// 			$response['error'] = true; 
			// 			$response['message'] = 'Some error occurred please try again';
			// 		}
			// 	}else{
			// 		$response['error'] = true; 
			// 		$response['message'] = 'Nothing to delete, provide an id please';
			// 	}
			// break; 
		}
		
	}else{
		//if it is not api call 
		//pushing appropriate values to response array 
		$response['error'] = true; 
		$response['message'] = 'Invalid API Call';
	}
	
	//displaying the response in json structure 
	echo json_encode($response);