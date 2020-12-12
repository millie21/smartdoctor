<?php
        require 'init.php';

        $email = $_POST['email'];

        $sql_query = "SELECT doctor, docFees, appdate FROM appointmenttable WHERE email ='$email'";

        $response = [];

        $result = mysqli_query($conn,$sql_query);// or exit(mysql_error());

if(mysqli_num_rows($result) > 0){

        $counter = 0;

        while ($row = mysqli_fetch_assoc($result)) {
                $doctor = $row["doctor"];
                $docFees = $row["docFees"];
                $appdate = $row["appdate"];
               // $apptime = $row["apptime"];



                $response['type'] = true;
                $response['appointments'][$counter] = [
                        'doctor'          =>      $doctor,
                        'docFees'        =>      $docFees,
                        'appdate'          =>      $appdate
                      //  'apptime'        =>      $apptime
                ];

                $counter++;
        }
        
}else{
        $response['type'] = false;
        $response['message'] = 'No appointments found';
}

echo json_encode($response, JSON_NUMERIC_CHECK);