<?php
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

    $name = $_POST["name"];
    $username = $_POST["username"];
    $password = $_POST["password"];
    $email = $_POST["email"];

    $statement = mysqli_prepare($con, "INSERT INTO user (name, username, password, email) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssss", $name, $username, $password, $email);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
