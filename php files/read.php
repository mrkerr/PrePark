<?php
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $username = $_POST["username"];
    $date = $_POST["date"];
    $when = $_POST["when"];

    if ($when == 2) {
      $statement = mysqli_prepare($con, "SELECT seller, payment, date FROM payment_history WHERE month(date) = ?");
      mysqli_stmt_bind_param($statement, "s", $date);
      mysqli_stmt_execute($statement);
      mysqli_stmt_store_result($statement);
      mysqli_stmt_bind_result($statement, $username, $transaction, $date);
    }

    if ($when == 3) {
      $statement = mysqli_prepare($con, "SELECT seller, payment, date FROM payment_history WHERE year(date) = ?");
      mysqli_stmt_bind_param($statement, "s", $date);
      mysqli_stmt_execute($statement);
      mysqli_stmt_store_result($statement);
      mysqli_stmt_bind_result($statement, $username, $transaction, $date);
    }

    $responseObject = array();
    $usernameList = array();
    $transactionList = array();
    $dateList = array();

    $response = array();
    $response["success"] = true;
    array_push($responseObject, $response);

    while(mysqli_stmt_fetch($statement)){
        array_push($usernameList, $username);
        array_push($transactionList, $transaction);
        array_push($dateList, $date);
    }
    //$output = json_encode(array('kitten' => $result));
    $u = json_encode(array('username' => $usernameList));
    $t = json_encode(array('transaction' => $transactionList));
    $d = json_encode(array('date' => $dateList));
    array_push($responseObject, $u);
    array_push($responseObject, $t);
    array_push($responseObject, $d);
    echo json_encode($responseObject);
?>
