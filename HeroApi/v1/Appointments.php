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
		
			case 'createappointment':
				
				$db = new DbOperation();
				
				$result = $db->createAppointment(
				
					$_POST['id'],
  					$_POST['fname'],
  					$_POST['lname'],
  					$_POST['gender'],
  					$_POST['email'],
  					$_POST['contact'],
  					$_POST['doctor'],
  					$_POST['docFees'],
  					$_POST['appdate'],
  					$_POST['apptime'],
  					$_POST['userStatus'],
  					$_POST['doctorStatus']

				);
				

				//if the record is created adding success to response
				if($result){
					//record is created means there is no error
					$response['error'] = false; 

					//in message we have a success message
					$response['message'] = 'App submitted successfully';

					//and we are getting all the heroes from the database in the response
					$response['appointments'] = $db->getAppointment();
				}else{

					//if record is not added that means there is an error 
					$response['error'] = true; 

					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 
			
			//the READ operation
			//if the call is getheroes
			case 'getappointment':
				$db = new DbOperation();
				$response['error'] = false; 
				$response['message'] = 'Request successfully completed';
				$response['appointments'] = $db->getAppointment();
			break; 

			case 'getactiveappointment':
				$db = new DbOperation();
				$response['error'] = false; 
				$response['message'] = 'Request successfully completed';
				$response['appointments'] = $db->getActiveAppointment();
			break; 
			
			//getActiveAppointmentPatients

			case 'getactivepatientsappointment':
				$db = new DbOperation();
				$response['error'] = false; 
				$response['message'] = 'Request successfully completed';
				$response['appointments'] = $db->getActiveAppointmentPatients();
			break; 

			//the UPDATE operation
			// case 'cancelappointment':
			// 	// isTheseParametersAvailable(array('id','firstname','lastname','email','phonenumber'));
			// 	$db = new DbOperation();
			// 	$result = $db->cancelAppointment(
			// 		$_POST['id'],
			// 		$_POST['doctorStatus']
			// 		//$_POST['phonenumber']
			// 	);
				
			// 	if($result){
			// 		$response['error'] = false; 
			// 		$response['message'] = 'Hero updated successfully';
			// 		$response['heroes'] = $db->getAppointment();
			// 	}else{
			// 		$response['error'] = true; 
			// 		$response['message'] = 'Some error occurred please try again';
			// 	}
			// break; 
			
		//	the cancel appointment operation
			case 'cancelappointment':

				//for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
				if(isset($_GET['id'])){
					$db = new DbOperation();
					if($db->cancelAppointment($_GET['id'])){
						$response['error'] = false; 
						$response['message'] = 'Appointment cancelled';
						$response['appointments'] = $db->getAppointment();
					}else{
						$response['error'] = true; 
						$response['message'] = 'Some error occurred please try again';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to delete, provide an id please';
				}
			break; 

			//	the cancel appointment operation
			case 'patientcancelappointment':

				//for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
				if(isset($_GET['id'])){
					$db = new DbOperation();
					if($db->patientcancelAppointment($_GET['id'])){
						$response['error'] = false; 
						$response['message'] = 'Appointment cancelled';
						$response['appointments'] = $db->getAppointment();
					}else{
						$response['error'] = true; 
						$response['message'] = 'Some error occurred please try again';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to delete, provide an id please';
				}
			break; 

			case 'getmyappointment':
				if(isset($_GET['pid'])){
					$db = new DbOperation();
					if($db->getmyAppointment($_GET['pid'])){
						$response['error'] = false; 
						$response['message'] = 'Appointment cancelled';
						$response['appointments'] = $db->getAppointment();
					}else{
						$response['error'] = true; 
						$response['message'] = 'Some error occurred please try again';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to delete, provide an id please';
				}
			break; 
		// 	case 'getmyappointment':

		// 		//for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
		// 		if(isset($_GET['id'])){
		// 			$db = new DbOperation();
		// 			if($db->cancelAppointment($_GET['id'])){
		// 				$response['error'] = false; 
		// 				$response['message'] = 'Appointment cancelled';
		// 				$response['appointments'] = $db->getAppointment();
		// 			}else{
		// 				$response['error'] = true; 
		// 				$response['message'] = 'Some error occurred please try again';
		// 			}
		// 		}else{
		// 			$response['error'] = true; 
		// 			$response['message'] = 'Nothing to delete, provide an id please';
		// 		}
		// 	break; 
		 }
		
	}else{
		//if it is not api call 
		//pushing appropriate values to response array 
		$response['error'] = true; 
		$response['message'] = 'Invalid API Call';
	}
	
	//displaying the response in json structure 
	echo json_encode($response);