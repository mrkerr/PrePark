<?php
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    // Check connection
    if ($con->connect_error) {
        die("Connection failed: " . $con->connect_error);
    }

    $username = $_POST["username"];
	  $address = $_POST["address"];

    $statement = mysqli_prepare($con, "SELECT username FROM lots WHERE address = ?");
    mysqli_stmt_bind_param($statement, "s", $address);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $username);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["username"] = $username;
    }

    echo json_encode($response);
?>
