<?php
 
class DbOperation
{
    //Database connection link
    private $con;
 
    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';
 
        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();
 
        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }
	
	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createHero($name, $realname, $rating, $teamaffiliation){
		$stmt = $this->con->prepare("INSERT INTO heroes (name, realname, rating, teamaffiliation) VALUES (?, ?, ?, ?)");
		$stmt->bind_param("ssss", $name, $realname, $rating, $teamaffiliation);
		if($stmt->execute())
			return true; 
		return false; 
	}

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getHeroes(){
		$stmt = $this->con->prepare("SELECT id, name, realname, rating, teamaffiliation FROM heroes");
		$stmt->execute();
		$stmt->bind_result($id, $name, $realname, $rating, $teamaffiliation);
		
		$heroes = array(); 
		
		while($stmt->fetch()){
			$hero  = array();
			$hero['id'] = $id; 
			$hero['name'] = $name; 
			$hero['realname'] = $realname; 
			$hero['rating'] = $rating; 
			$hero['teamaffiliation'] = $teamaffiliation; 
			
			array_push($heroes, $hero); 
		}
		
		return $heroes; 
	}
	
	/*
	* The update operation
	* When this method is called the record with the given id is updated with the new given values
	*/
	function updateHero($id, $name, $realname, $rating, $teamaffiliation){
		$stmt = $this->con->prepare("UPDATE heroes SET name = ?, realname = ?, rating = ?, teamaffiliation = ? WHERE id = ?");
		$stmt->bind_param("ssisi", $name, $realname, $rating, $teamaffiliation, $id);
		if($stmt->execute())
			return true; 
		return false; 
	}
	
	
	/*
	* The delete operation
	* When this method is called record is deleted for the given id 
	*/
	function deleteHero($id){
		//$stmt = $this->con->prepare("DELETE FROM heroes WHERE id = ? ");
		$ret = 0;
		$stmt = $this->con->prepare("UPDATE heroes SET name = '$ret' WHERE id = ?");
		$stmt->bind_param("i", $id);
		if($stmt->execute())
			return true; 
		
		return false; 
	}
	/*
	* The delete operation
	* When this method is called record is deleted for the given id 
	*/
	function deleteDoctors($email){
		$stmt = $this->con->prepare("DELETE FROM doctortable WHERE email = ? ");
		$stmt->bind_param("s", $email);
		if($stmt->execute())
			return true; 
		
		return false; 
	}

	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createIssue($title, $detail){
		$stmt = $this->con->prepare("INSERT INTO issues (title, detail) VALUES (?, ?)");
		$stmt->bind_param("ss", $title, $detail);
		if($stmt->execute())
			return true; 
		return false; 
	}

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getIssues(){
		$stmt = $this->con->prepare("SELECT helpid, title, detail FROM issues");
		$stmt->execute();
		$stmt->bind_result($helpid, $title, $detail);
		
		$issues = array(); 
		
		while($stmt->fetch()){
			$issue  = array();
			$issue['helpid'] = $helpid; 
			$issue['title'] = $title; 
			$issue['detail'] = $detail; 
			
			array_push($issues, $issue); 
		}
		
		return $issues; 
	}

	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createNurse($firstname, $lastname, $email, $phonenumber, $team){
		$stmt = $this->con->prepare("INSERT INTO nurses (firstname, lastname, email, phonenumber, team) VALUES (?, ?, ?, ?, ?)");
		$stmt->bind_param("sssss", $firstname, $lastname, $email, $phonenumber, $team);
		if($stmt->execute())
			return true; 
		return false; 
	}

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getNurses(){
		$stmt = $this->con->prepare("SELECT nurseid, firstname, lastname, email, phonenumber, team FROM nurses");
		$stmt->execute();
		$stmt->bind_result($firstname, $lastname, $email, $phonenumber, $team);
		
		$nurses = array(); 
		
		while($stmt->fetch()){
			$nurse  = array();
			$nurse['nurseid'] = $nurseid; 
			$nurse['firstname'] = $firstname; 
			$nurse['lastname'] = $lastname; 
			$nurse['email'] = $email; 
			$nurse['phonenumber'] = $phonenumber; 
			$nurse['team'] = $team;
			
			array_push($nurses, $nurse); 
		}
		
		return $nurses; 
	}


/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createDoctors($username,$password, $email, $spec, $docFees){
		$stmt = $this->con->prepare("INSERT INTO doctortable (username, password, email, spec, docFees) VALUES (?, ?, ?, ?, ?)");
		$stmt->bind_param("sssss", $username, $password, $email, $spec, $docFees);
		if($stmt->execute())
			return true; 
		return false; 
	}
	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getDoctors(){
		$stmt = $this->con->prepare("SELECT username, email, spec, docFees FROM doctortable");
		$stmt->execute();
		$stmt->bind_result($username, $email, $spec, $docFees);
		
		$doctors = array(); 
		
		while($stmt->fetch()){
			$doctor  = array();
			$doctor['username'] = $username; 
			$doctor['email'] = $email; 
			$doctor['spec'] = $spec; 
			$doctor['docFees'] = $docFees; 
			
			
			array_push($doctors, $doctor); 
		}
		
		return $doctors; 
	}
	
	/*
	* The update operation
	* When this method is called the record with the given id is updated with the new given values
	*/
	function updateNurse($nurseid, $firstname, $lastname, $email, $phonenumber, $team){
		$stmt = $this->con->prepare("UPDATE nurses SET firstname = ?, lastname = ?, email = ?, phonenumber = ?, team = ? WHERE nurseid = ?");
		$stmt->bind_param("ssisi", $firstname, $lastname, $email, $phonenumber, $team, $nurseid);
		if($stmt->execute())
			return true; 
		return false; 
	}
	
	
	/*
	* The delete operation
	* When this method is called record is deleted for the given id 
	*/
	function deleteNurse($nurseid){
		$stmt = $this->con->prepare("DELETE FROM nurses WHERE nurseid = ? ");
		$stmt->bind_param("i", $nurseid);
		if($stmt->execute())
			return true; 
		
		return false; 
	}

/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createAppointment($id,$fname,$lname,$gender,$email,$contact,$doctor,$docFees,$appdate,$apptime,$userStatus,$doctorStatus){
		$stmt = $this->con->prepare("INSERT INTO appointmenttable (id,fname,lname,gender,email,contact,doctor,docFees,appdate,apptime,userStatus,doctorStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		$stmt->bind_param("ssssssssssss", $id,$fname,$lname,$gender,$email,$contact,$doctor,$docFees,$appdate,$apptime,$userStatus,$doctorStatus);
		if($stmt->execute())
			return true; 
		return false; 
	}

function getActiveAppointment(){
	$userStatus = 1;
	$doctorStatus = 1;
		$stmt = $this->con->prepare("SELECT id,fname,lname,gender,email,contact,doctor,docFees,appdate,apptime,userStatus,doctorStatus FROM appointmenttable WHERE doctorStatus = '$doctorStatus' AND  userStatus = '$userStatus'");
		$stmt->execute();
		$stmt->bind_result($id,$fname,$lname,$gender,$email,$contact,$doctor,$docFees,$appdate,$apptime,$userStatus,$doctorStatus);
		
		$appointments = array(); 
		
		while($stmt->fetch()){
			$appointment  = array();
			$appointment['id']= $id;
  			$appointment['fname']=$fname;
  			$appointment['lname']=$lname;
  			$appointment['gender']=$gender;
  			$appointment['email']=$email;
  			$appointment['contact']=$contact;
  			$appointment['doctor']=$doctor;
  			$appointment['docFees']=$docFees;
  			$appointment['appdate']=$appdate;
  			$appointment['apptime']=$apptime;
  			$appointment['userStatus']=$userStatus;
  			$appointment['doctorStatus']=$doctorStatus;
			
			array_push($appointments, $appointment); 
		}
		
		return $appointments; 
	}

	function getActiveAppointmentPatients(){
	$userStatus = 1;
	$doctorStatus = 1;
		$stmt = $this->con->prepare("SELECT id,fname,lname,gender,email,contact,doctor,docFees,appdate,apptime,userStatus,doctorStatus FROM appointmenttable WHERE doctorStatus = '$doctorStatus' AND  userStatus = '$userStatus'");
		$stmt->execute();
		$stmt->bind_result($id,$fname,$lname,$gender,$email,$contact,$doctor,$docFees,$appdate,$apptime,$userStatus,$doctorStatus);
		
		$appointments = array(); 
		
		while($stmt->fetch()){
			$appointment  = array();
			$appointment['id']= $id;
  			$appointment['fname']=$fname;
  			$appointment['lname']=$lname;
  			$appointment['gender']=$gender;
  			$appointment['email']=$email;
  			$appointment['contact']=$contact;
  			$appointment['doctor']=$doctor;
  			$appointment['docFees']=$docFees;
  			$appointment['appdate']=$appdate;
  			$appointment['apptime']=$apptime;
  			$appointment['userStatus']=$userStatus;
  			$appointment['doctorStatus']=$doctorStatus;
			
			array_push($appointments, $appointment); 
		}
		
		return $appointments; 
	}
	function getAppointment(){
		$stmt = $this->con->prepare("SELECT id,fname,lname,gender,email,contact,doctor,docFees,appdate,apptime,userStatus,doctorStatus FROM appointmenttable");
		$stmt->execute();
		$stmt->bind_result($id,$fname,$lname,$gender,$email,$contact,$doctor,$docFees,$appdate,$apptime,$userStatus,$doctorStatus);
		
		$appointments = array(); 
		
		while($stmt->fetch()){
			$appointment  = array();
			$appointment['id']= $id;
  			$appointment['fname']=$fname;
  			$appointment['lname']=$lname;
  			$appointment['gender']=$gender;
  			$appointment['email']=$email;
  			$appointment['contact']=$contact;
  			$appointment['doctor']=$doctor;
  			$appointment['docFees']=$docFees;
  			$appointment['appdate']=$appdate;
  			$appointment['apptime']=$apptime;
  			$appointment['userStatus']=$userStatus;
  			$appointment['doctorStatus']=$doctorStatus;
			
			array_push($appointments, $appointment); 
		}
		
		return $appointments; 
	}

	function getmyAppointment($pid){
		$stmt = $this->con->prepare("SELECT pid, id,fname,lname,gender,email,contact,doctor,docFees,appdate,apptime,userStatus,doctorStatus FROM appointmenttable WHERE pid = ?");
		$stmt->execute();
		$stmt->bind_result($pid,$id,$fname,$lname,$gender,$email,$contact,$doctor,$docFees,$appdate,$apptime,$userStatus,$doctorStatus);
		
		$appointments = array(); 
		
		while($stmt->fetch()){
			$appointment  = array();
			$appointment['pid']= $id;
			$appointment['id']= $id;
  			$appointment['fname']=$fname;
  			$appointment['lname']=$lname;
  			$appointment['gender']=$gender;
  			$appointment['email']=$email;
  			$appointment['contact']=$contact;
  			$appointment['doctor']=$doctor;
  			$appointment['docFees']=$docFees;
  			$appointment['appdate']=$appdate;
  			$appointment['apptime']=$apptime;
  			$appointment['userStatus']=$userStatus;
  			$appointment['doctorStatus']=$doctorStatus;
			
			array_push($appointments, $appointment); 
		}
		
		return $appointments; 
	}


	// function cancelAppointment($id){

	// 	$testid = 0;

	// 	$stmt = $this->con->prepare("UPDATE appointmenttable SET doctorStatus = '$testid' WHERE id = ?");
	// 	$stmt->bind_param("s",$id);
	// 	if($stmt->execute())
	// 		return true; 
	// 	return false; 
	// }

	function cancelAppointment($id){
		//$stmt = $this->con->prepare("DELETE FROM heroes WHERE id = ? ");
		$ret = 0;
		$stmt = $this->con->prepare("UPDATE appointmenttable SET doctorStatus = '$ret' WHERE id = ?");
		$stmt->bind_param("i", $id);
		if($stmt->execute())
			return true; 
		
		return false; 
	}

	function patientcancelAppointment($id){
		//$stmt = $this->con->prepare("DELETE FROM heroes WHERE id = ? ");
		$ret = 0;
		$stmt = $this->con->prepare("UPDATE appointmenttable SET userStatus = '$ret' WHERE id = ?");
		$stmt->bind_param("i", $id);
		if($stmt->execute())
			return true; 
		
		return false; 
	}

//
// function deleteDoctors($email){
// 		$stmt = $this->con->prepare("DELETE FROM doctortable WHERE email = ? ");
// 		$stmt->bind_param("s", $email);
// 		if($stmt->execute())
// 			return true; 
		
// 		return false; 
// 	}
	
	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createContact($name, $email,$contact, $message){
		$stmt = $this->con->prepare("INSERT INTO contact (name, email, contact, message) VALUES (?, ?, ?, ?)");
		$stmt->bind_param("siss", $name, $email, $contact, $message);
		if($stmt->execute())
			return true; 
		return false; 
	}

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getContact(){
		$stmt = $this->con->prepare("SELECT name, email, contact, message FROM contact");
		$stmt->execute();
		$stmt->bind_result($name, $email, $contact, $message);
		
		$contacts = array(); 
		
		while($stmt->fetch()){
			$contactd  = array();
			$contactd['name'] = $name; 
			$contactd['email'] = $email; 
			$contactd['contact'] = $contact; 
			$contactd['message'] = $message;
			
			array_push($contacts, $contactd); 
		}
		
		return $contacts; 
	}

	function createPrescription ($pid,$username,$fname,$lname,$disease, $status, $prescription){
		$stmt = $this->con->prepare("INSERT INTO prescriptiontable (pid,doctor,fname,lname, disease, status, prescription ) VALUES (?, ?, ?, ?, ?, ?, ?)");
		$stmt->bind_param("issssss",$pid, $username,$fname,$lname, $disease, $status, $prescription);
		if($stmt->execute())
			return true; 
		return false; 
	}

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getPrescription(){
		$stmt = $this->con->prepare("SELECT doctor, pid, id, fname, lname, appdate, apptime, disease, status, prescription FROM prescriptiontable");
		$stmt->execute();
		$stmt->bind_result($doctor, $pid, $id, $fname, $lname, $appdate, $apptime, $disease, $status, $prescription);
		
		$prescriptions = array(); 
		
		while($stmt->fetch()){
			$prescriptionz  = array();
			$prescriptionz['doctor'] = $doctor; 
			$prescriptionz['appdate'] = $appdate; 
			$prescriptionz['prescription'] = $prescription; 
		//	$prescription['message'] = $message;
			
			array_push($prescriptions, $prescriptionz); 
		}
		
		return $prescriptions; 
	}

		/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createData($Temperature, $Heartrate,$Respiration, $Spo2){
		$stmt = $this->con->prepare("INSERT INTO healthtable (Temperature, Heartrate,Respiration, Spo2) VALUES (?, ?, ?, ?)");
		$stmt->bind_param("siss", $Temperature, $Heartrate,$Respiration, $Spo2);
		if($stmt->execute())
			return true; 
		return false; 
	}

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getData(){
		$stmt = $this->con->prepare("SELECT Temperature, Heartrate,Respiration, Spo2 FROM healthtable");
		$stmt->execute();
		$stmt->bind_result($Temperature, $Heartrate,$Respiration, $Spo2);
		
		$datas = array(); 
		
		while($stmt->fetch()){
			$data  = array();
			$data['Temperature'] = $Temperature; 
			$data['Heartrate'] = $Heartrate; 
			$data['Respiration'] = $Respiration; 
			$data['Spo2'] = $Spo2;
			
			array_push($datas, $data); 
		}
		
		return $datas; 
	}
	//getblockchaindata
	function getBlockchainData(){
		$stmt = $this->con->prepare("SELECT h.ID,h.Timestamp,h.Heartrate,h.Respiration,h.Spo2,h.Temperature,a.age,a.gender,a.fname,b.Block,b.PreviousHash from healthtable h JOIN appointmenttable a ON h.ID=a.ID JOIN blockchain b ON a.ID=b.ID");
		$stmt->execute();
		$stmt->bind_result($ID,$Timestamp,$Temperature,$Heartrate,$Respiration,$Spo2, $age, $gender, $fname, $Block, $PreviousHash);
		
		$datas = array(); 
		
		while($stmt->fetch()){
			$data  = array();
			//$data['ID'] = $ID;
			//$data['Timestamp'] = $Timestamp; 
			$data['Heartrate'] = $Heartrate;
			$data['Respiration'] = $Respiration;
			$data['Spo2'] = $Spo2;
			$data['Temperature'] = $Temperature;
			//$data['age'] = $age; 
			//$data['gender'] = $gender; 
			$data['fname'] = $fname;
			//$data['Block'] = $Block; 
			$data['PreviousHash'] = $PreviousHash;
			
			array_push($datas, $data); 
		}
		
		return $datas; 
	}



}