<?php
    //establilsh connection to database with hostname, username, password, schema
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    //testing conection, if it fails will output connection failed
    if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
  }
    //varibles taking in corresponding variables from android
    $name = $_POST["seller"];
  

    $existing_statement = mysqli_prepare($con, "INSERT INTO payment_history (seller) username = ? ");
    mysqli_stmt_bind_param($existing_statement, "ss", $name);
    mysqli_stmt_execute($existing_statement);
	
	 $response = array();
     $response["success"] = true;


    //sends back to server with response in json
    echo json_encode($response);
?>
