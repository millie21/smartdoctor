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
		$stmt->bind_param("ssis", $name, $realname, $rating, $teamaffiliation);
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
		$stmt = $this->con->prepare("DELETE FROM heroes WHERE id = ? ");
		$stmt->bind_param("i", $id);
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
		$stmt->bind_param("ssisa", $firstname, $lastname, $email, $phonenumber, $team);
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
	function createAppointment($specialization, $doctor, $fees, $appdate, $apptime){
		$stmt = $this->con->prepare("INSERT INTO appointments_table (specialization, doctor, fees, appdate, apptime) VALUES (?, ?, ?, ?, ?)");
		$stmt->bind_param("ssisa", $specialization, $doctor, $fees, $appdate, $apptime);
		if($stmt->execute())
			return true; 
		return false; 
	}

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getAppointment(){
		$stmt = $this->con->prepare("SELECT specialization, doctor, fees, appdate, apptime FROM appointments_table");
		$stmt->execute();
		$stmt->bind_result($specialization, $doctor, $fees, $appdate, $apptime);
		
		$nurses = array(); 
		
		while($stmt->fetch()){
			$appointment  = array();
			$appointment['appid'] = $appid; 
			$appointment['specialization'] = $specialization; 
			$appointment['doctor'] = $doctor; 
			$appointment['fees'] = $fees; 
			$appointment['appdate'] = $appdate; 
			$appointment['apptime'] = $apptime;
			
			array_push($appointments, $appointment); 
		}
		
		return $appointments; 
	}
	
	/*
	* The update operation
	* When this method is called the record with the given id is updated with the new given values
	*/
	// function updateNurse($nurseid, $firstname, $lastname, $email, $phonenumber, $team){
	// 	$stmt = $this->con->prepare("UPDATE nurses SET firstname = ?, lastname = ?, email = ?, phonenumber = ?, team = ? WHERE nurseid = ?");
	// 	$stmt->bind_param("ssisi", $firstname, $lastname, $email, $phonenumber, $team, $nurseid);
	// 	if($stmt->execute())
	// 		return true; 
	// 	return false; 
	// }
	
	
	// /*
	// * The delete operation
	// * When this method is called record is deleted for the given id 
	// */
	// function deleteNurse($nurseid){
	// 	$stmt = $this->con->prepare("DELETE FROM nurses WHERE nurseid = ? ");
	// 	$stmt->bind_param("i", $nurseid);
	// 	if($stmt->execute())
	// 		return true; 
		
	// 	return false; 
	// }

	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createContacts($name, $email,$contact, $message){
		$stmt = $this->con->prepare("INSERT INTO contact (name, email, contact, message) VALUES (?, ?, ?, ?)");
		$stmt->bind_param("ssss", $name, $email, $contact, $message);
		if($stmt->execute())
			return true; 
		return false; 
	}

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getContacts(){
		$stmt = $this->con->prepare("SELECT name, email, contact, message FROM contact");
		$stmt->execute();
		$stmt->bind_result($name, $email, $contact, $message);
		
		$issues = array(); 
		
		while($stmt->fetch()){
			$contact  = array();
			$contact['name'] = $name; 
			$contact['email'] = $email; 
			$contact['contact'] = $contact; 
			$contact['message'] = $message;
			
			array_push($contacts, $contact); 
		}
		
		return $contacts; 
	}


}