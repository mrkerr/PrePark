<?php
    //establilsh connection to database with hostname, username, password, schema
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    //testing conection, if it fails will output connection failed
    if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
}
    //varibles taking in corresponding variables from android
	$buyer = $_POST["buyer"];
	$seller = $_POST["seller"];
	$transaction = $_POST["transaction"];
  $date = $_POST["date"];

    //passing in an insert statement
    $statement = mysqli_prepare($con, "INSERT INTO payment_history VALUES (?, ?, ?, ?)");
    //assigning the values with the ones given in android
    mysqli_stmt_bind_param($statement, "ssss", $buyer, $seller, $transaction, $date);
    //executing statement
    mysqli_stmt_execute($statement);

    //
    $response = array();
    $response["success"] = true;

    //sends back to server with response in json
    echo json_encode($response);
?>
