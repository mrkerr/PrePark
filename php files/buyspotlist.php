<?php
    //establilsh connection to database with hostname, username, password, schema
    $connect = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    //testing conection, if it fails will output connection failed
    if ($connect->connect_error) {
    die("Connection failed: " . $connect->connect_error);
}
    //varibles taking in corresponding variables from android
    $zip = $_POST["zip"];

    //passing in an insert statement
    $statement = mysqli_prepare($con, "SELECT address FROM lots WHERE zip = ?");
    //assigning the values with the ones given in android
    mysqli_stmt_bind_param($statement, "s", $zip);
    //executing statement
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);

    mysqli_stmt_bind_result($statement, $address);
    //


    $addressList = array();
    $responseObject = array();

    $response = array();
    $response["success"] = true;
    array_push($responseObject, $response);

    while(mysqli_stmt_fetch($statement)){
	     array_push($addressList, $address);
	}
  echo json_encode($addressList);
  //    $a = json_encode(array('address' => $addressList));
  //    array_push($responseObject, $a);
  //
  //   //sends back to server with response in json
  //   echo json_encode($responseObject);
?>
