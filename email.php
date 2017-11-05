<?php
    //establilsh connection to database with hostname, username, password, schema
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    //testing conection, if it fails will output connection failed
    if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
}
    //varibles taking in corresponding variables from android
    $email = $_POST["email"];

    $statement = mysqli_prepare($con, "SELECT username, password FROM user WHERE email = ?");
    mysqli_stmt_bind_param($statement, "s", $email);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $username, $password);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $u = $username;
        $p = $password;
    }

    //send email here
    $to = $email;
    $subject = "PrePark Username And Password";
    $message = "Here is your username: " . $u . " and password: " . $p;

    mail($to,$subject,$message);

    //sends back to server with response in json
    echo json_encode($response);
?>
