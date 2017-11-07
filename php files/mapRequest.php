<?php
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $address = $_POST["address"];
    $city = $_POST["city"];
    $state = $_POST["state"];

    $spots = $_POST["spots"];
    $time = $_POST["time"];
    $rate = $_POST["rate"];

    $statement = mysqli_prepare($con, "SELECT address, city, state, spots, time, rate FROM lots");
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $address, $city, $state, $spots, $time, $rate);

    $responseObject = array();
    $addressList = array();
    $cityList = array();
    $stateList = array();

    $spotsList = array();
    $timeList = array();
    $rateList = array();

    $response = array();
    $response["success"] = true;
    array_push($responseObject, $response);

    while(mysqli_stmt_fetch($statement)){
        array_push($addressList, $address);
        array_push($cityList, $city);
        array_push($stateList, $state);
        array_push($spotsList, $spots);
        array_push($timeList, $time);
        array_push($rateList, $rate);
    }
    //$output = json_encode(array('kitten' => $result));
    $a = json_encode(array('address' => $addressList));
    $c = json_encode(array('city' => $cityList));
    $s = json_encode(array('state' => $stateList));

    $ss = json_encode(array('spots' => $spotsList));
    $t = json_encode(array('time' => $timeList));
    $r = json_encode(array('rate' => $rateList));
    array_push($responseObject, $a);
    array_push($responseObject, $c);
    array_push($responseObject, $s);
    array_push($responseObject, $ss);
    array_push($responseObject, $t);
    array_push($responseObject, $r);
    echo json_encode($responseObject);
?>
