<?php
    //establilsh connection to database with hostname, username, password, schema
    $con = mysqli_connect("mysql.cs.iastate.edu", "dbu309sbb2", "5RVqfsTS", "db309sbb2");

    //testing conection, if it fails will output connection failed
    if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
}
    //varibles taking in corresponding variables from android
    $name = $_POST["name"];
    $username = $_POST["username"];
    $password = $_POST["password"];
    $email = $_POST["email"];

    $emailstatement = mysqli_prepare($con, "SELECT count(*) FROM 'users' WHERE email = ?");
    mysqli_stmt_bind_param($emailstatement, "s", $email);
    $emailIsUsed = mysqli_stmt_execute($emailstatement);

    if (mysql_num_rows($emailIsUsed) != 0) {
        echo 'An account with this e-mail address already exists!';
    }






    //passing in an insert statement
    $statement = mysqli_prepare($con, "INSERT INTO user (name, username, password, email) VALUES (?, ?, ?, ?)");
    //assigning the values with the ones given in android
    mysqli_stmt_bind_param($statement, "ssss", $name, $username, $password, $email);
    //executing statement
    mysqli_stmt_execute($statement);

    //
    $response = array();
    $response["success"] = true;

    //sends back to server with response in json
    echo json_encode($response);
?>
