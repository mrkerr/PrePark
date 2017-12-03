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

    $existing_statement = mysqli_prepare($con, "SELECT username, email FROM user WHERE username = ? and email = ?");
    mysqli_stmt_bind_param($existing_statement, "ss", $username, $email);
    mysqli_stmt_execute($existing_statement);

    mysqli_stmt_store_result($existing_statement);
    mysqli_stmt_bind_result($existing_statement, $existing_username, $existing_email);

    while(mysqli_stmt_fetch($existing_statement)){
        $u = $existing_username;
        $e = $existing_email;
    }

    //checking if taken
    if (strcmp($username, $u) == 0 || strcmp($email, $e) == 0){
      $fail = array();
      $fail["success"] = false;
      echo json_encode($fail);
      die();
    }

    //checking if null
    if ($name == null || $username == null || $password == null || $email == null) {
      $fail = array();
      $fail["success"] = false;
      echo json_encode($fail);
      die();
    }

    //checking for spaces
    // if (strlen(trim($name)) == 0 || strlen(trim($username)) == 0  || strlen(trim($password)) == 0  || $strlen(trim($email)) == 0 ) {
    //   $fail = array();
    //   $fail["success"] = false;
    //   echo json_encode($fail);
    //   die();
    // }

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
