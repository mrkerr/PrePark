<?php
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $address = $_POST["address"];
    $city = $_POST["city"];
    $state = $_POST["state"];

    $statement = mysqli_prepare($con, "SELECT address, city, state FROM lots");
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $address, $city, $state);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["address"] = $address;
        $response["city"] = $city;
        $response["state"] = $state;
        echo json_encode($response);
    }

    //echo json_encode($response);
?>
