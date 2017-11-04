<?php
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $username = $_POST["username"];
    
    $statement = mysqli_prepare($con, "SELECT payment FROM payement_history WHERE username = ?");
    mysqli_stmt_bind_param($statement, "s", $username,);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$transaction);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["payment"] = $transaction;
    }

    echo json_encode($response);
?>
