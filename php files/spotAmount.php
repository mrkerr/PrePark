<?php
    //establilsh connection to database with hostname, username, password, schema
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    //testing conection, if it fails will output connection failed
    if ($connect->connect_error) {
    die("Connection failed: " . $connect->connect_error);
}
    //varibles taking in corresponding variables from android
    $address = $_POST["address"];

    //if taken is 1, someone is buying a spot
    //else someone is leaving a spot
    $taken = $_POST["taken"];

    if ($taken == 1) {
    //passing in an insert statement
    $statement = mysqli_prepare($con, "UPDATE lots SET spots = spots - 1 WHERE address = ? and spots >= 0");
    //assigning the values with the ones given in android
    mysqli_stmt_bind_param($statement, "s", $address);
    //executing statement
    mysqli_stmt_execute($statement);
  }
  else {
    //passing in an insert statement
    $statement = mysqli_prepare($con, "UPDATE lots SET spots = spots + 1 WHERE address = ?");
    //assigning the values with the ones given in android
    mysqli_stmt_bind_param($statement, "s", $address);
    //executing statement
    mysqli_stmt_execute($statement);
  }

    //
    $response = array();
    $response["success"] = true;

    //sends back to server with response in json
    echo json_encode($response);
?>
