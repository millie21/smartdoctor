<?php   
  require_once 'Connection2.php';  
  $response = array();  
  if(isset($_GET['apicall'])){  
  switch($_GET['apicall']){  
  case 'signup':  
    if(isTheseParametersAvailable(array('fname','lname','email','contact','password','cpassword','gender'))){  
    $fname = $_POST['fname'];
    $lname = $_POST['lname'];   
    $email = $_POST['email'];   
    $password = $_POST['password'];
    $contact = $_POST['contact'];
    $cpassword = $_POST['cpassword'];  
    $gender = $_POST['gender'];   
   
    $stmt = $conn->prepare("SELECT pid FROM patientregistration WHERE email = ?");  
    $stmt->bind_param("s", $email);  
    $stmt->execute();  
    $stmt->store_result();  
   
    if($stmt->num_rows > 0){  
        $response['error'] = true;  
        $response['message'] = 'User already registered';  
        $stmt->close();  
    }  
    else{  
        $stmt = $conn->prepare("INSERT INTO patientregistration (fname, lname, gender, email, contact, password, cpassword) VALUES (?, ?, ?, ?, ?, ?, ?)");  
        $stmt->bind_param("sssssss", $fname, $lname, $gender, $email, $contact, $password, $cpassword);  
   
        if($stmt->execute()){  
            $stmt = $conn->prepare("SELECT pid, fname, lname, gender, email, contact, password, cpassword FROM patientregistration WHERE email = ?");   
            $stmt->bind_param("s",$email);  
            $stmt->execute();  
            $stmt->bind_result($pid, $fname, $lname, $gender, $email, $contact, $password, $cpassword);  
            $stmt->fetch();  
   
            $user = array(  
            'pid'=>$pid,    
            'fname'=>$fname,
            'lname'=>$lname,
            'contact'=>$contact,
            'password'=>$password,
            'cpassword'=>$cpassword,
            'email'=>$email,
            'gender'=>$gender  
            );  
   
            $stmt->close();  
   
            $response['error'] = false;   
            $response['message'] = 'User registered successfully';   
            $response['user'] = $user;   
        }  
    }  
   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break;   

case 'login':  
  if(isTheseParametersAvailable(array('password', 'email'))){  
    $password = $_POST['password'];   
    $email = $_POST['email']; 
    $stmt = $conn->prepare("SELECT pid, fname, lname, contact, email, gender FROM patientregistration WHERE password = ? AND email = ?");  
    $stmt->bind_param("ss",$password, $email);  
    $stmt->execute();  
    $stmt->store_result();  
    if($stmt->num_rows > 0){  
    $stmt->bind_result($pid, $fname, $lname, $contact, $email, $gender);  
    $stmt->fetch();  
    $user = array(  
    'pid'=>$pid,   
    'fname'=>$fname, 
    'lname'=>$lname, 
    'contact'=>$contact,
    'email'=>$email,  
    'gender'=>$gender  
    );  
   
    $response['error'] = false;   
    $response['message'] = 'Login successfull';   
    $response['user'] = $user;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid username or password';  
 }  
}  
break;   

case 'doctorlogin':  
  if(isTheseParametersAvailable(array('password', 'email'))){  
    $password = $_POST['password'];   
    $email = $_POST['email']; 
    $stmt = $conn->prepare("SELECT username, password, spec, docFees FROM doctortable WHERE password = ? AND email = ?");  
    $stmt->bind_param("ss",$password, $email);  
    $stmt->execute();  
    $stmt->store_result();  
    if($stmt->num_rows > 0){  
    $stmt->bind_result($username, $password, $spec, $docFees);  
    $stmt->fetch();  
    $doctor = array(  
    'username'=>$username,   
    'email'=>$email, 
    'docFees'=>$docFees,
    'spec'=>$spec
     
    );  
   
    $response['error'] = false;   
    $response['message'] = 'Login successfull';   
    $response['doctor'] = $doctor;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid username or password';  
 }  
}  
break;   

case 'adminlogin':  
  if(isTheseParametersAvailable(array('password', 'email'))){  
    $password = $_POST['password'];   
    $email = $_POST['email']; 
    $stmt = $conn->prepare("SELECT password, email FROM admintable WHERE password = ? AND email = ?");  
    $stmt->bind_param("ss",$password, $email);  
    $stmt->execute();  
    $stmt->store_result();  
    if($stmt->num_rows > 0){  
    $stmt->bind_result($password, $email);  
    $stmt->fetch();  
    $admin = array(  
    'username'=>$username,   
    'email'=>$email
     
    );  
   
    $response['error'] = false;   
    $response['message'] = 'Login successfull';   
    $response['admin'] = $admin;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid username or password';  
 }  
}  

break;   

default:   
 $response['error'] = true;   
 $response['message'] = 'Invalid Operation Called';  
}  
}  
else{  
 $response['error'] = true;   
 $response['message'] = 'Invalid API Call';  
}  
echo json_encode($response);  
function isTheseParametersAvailable($params){  
foreach($params as $param){  
 if(!isset($_POST[$param])){  
     return false;   
  }  
}  
return true;   
}  
?>  