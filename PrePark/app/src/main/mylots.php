<?php
    //establilsh connection to database with hostname, username, password, schema
    $connect = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    //testing conection, if it fails will output connection failed
    if ($connect->connect_error) {
    die("Connection failed: " . $connect->connect_error);
}
    //varibles taking in corresponding variables from android
    $address = $_POST["address"];
    $zip = $_POST["zip"];
    $spots = $_POST["spots"];
    $nexttime = $_POST["time"];

    //passing in an insert statement
    $statement = mysqli_prepare($con, "INSERT INTO lots (address, zip, spots, time) VALUES (?, ?, ?, ?)");
    //assigning the values with the ones given in android
    mysqli_stmt_bind_param($statement, "ssss", $address, $zip, $spots, $nexttime);
    //executing statement
    mysqli_stmt_execute($statement);

    //
    $response = array();
    $response["success"] = true;

    //sends back to server with response in json
    echo json_encode($response);
?>
