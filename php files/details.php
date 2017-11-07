<?php
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    // Check connection
    if ($con->connect_error) {
        die("Connection failed: " . $con->connect_error);
    }

    $address = $_POST["address"];

    $statement = mysqli_prepare($con, "SELECT spots, time, rate FROM lots WHERE address = ?");
    mysqli_stmt_bind_param($statement, "s", $address);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $spots, $time, $rate);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["spots"] = $spots;
        $response["time"] = $time;
        $response["rate"] = $rate;
    }

    echo json_encode($response);
?>
