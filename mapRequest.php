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

    $responseObject = array();
    $addressList = array();
    $cityList = array();
    $stateList = array();

    $response = array();
    $response["success"] = true;
    array_push($responseObject, $response);

    // while(mysqli_stmt_fetch($statement)){
    //     $response = array();
    //     $response["success"] = true;
    //     $response["address"] = $address;
    //     $response["city"] = $city;
    //     $response["state"] = $state;
    //     array_push($responseObject, $response);
    // }

    while(mysqli_stmt_fetch($statement)){
        array_push($addressList, $address);
        array_push($cityList, $city);
        array_push($stateList, $state);
    }
    //$output = json_encode(array('kitten' => $result));
    $a = json_encode(array('address' => $addressList));
    $c = json_encode(array('address' => $cityList));
    $s = json_encode(array('address' => $stateList));
    array_push($responseObject, $a);
    array_push($responseObject, $c);
    array_push($responseObject, $s);
    echo json_encode($responseObject);
?>
