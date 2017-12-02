<?php
    //establilsh connection to database with hostname, username, password, schema
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    //testing conection, if it fails will output connection failed
    if ($connect->connect_error) {
    die("Connection failed: " . $connect->connect_error);
}
    //varibles taking in corresponding variables from android
    $address = $_POST["address"];
    $spots = $_POST["spots"];
    $time = $_POST["time"];
    $username = $_POST["username"];

    //passing in an insert statement
    $statement = mysqli_prepare($con, "UPDATE lots SET spots = ?, time = ? WHERE address = ? and username = ?");
    //assigning the values with the ones given in android
    mysqli_stmt_bind_param($statement, "ssss", $spots, $time, $address, $username);
    //executing statement
    mysqli_stmt_execute($statement);

    //
    $response = array();
    $response["success"] = true;

    //sends back to server with response in json
    echo json_encode($response);
?>
