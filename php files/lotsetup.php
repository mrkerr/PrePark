<?php
    //establilsh connection to database with hostname, username, password, schema
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    //testing conection, if it fails will output connection failed
    if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
}
    //varibles taking in corresponding variables from android
    $username = $_POST["username"];
    $address = $_POST["address"];
    $city = $_POST["city"];
    $state = $_POST["state"];
    $zip = $_POST["zip"];
    $spots = $_POST["spots"];
    $time = $_POST["time"];
    $rate = $_POST["rate"];

    //checking if null
    if ($address == null || $city== null || $state == null || $zip == null || $spots == null || $time == null || $rate == null) {
      $fail = array();
      $fail["success"] = false;
      echo json_encode($fail);
      die();
    }

    //passing in an insert statement
    $statement = mysqli_prepare($con, "INSERT INTO lots (username, address, city, state, zip, spots, time, rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    //assigning the values with the ones given in android
    mysqli_stmt_bind_param($statement, "ssssssss", $username, $address, $city, $state, $zip, $spots, $time, $rate);
    //executing statement
    mysqli_stmt_execute($statement);

    //
    $response = array();
    $response["success"] = true;

    //sends back to server with response in json
    echo json_encode($response);
?>
