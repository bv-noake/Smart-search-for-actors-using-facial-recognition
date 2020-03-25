<?php

  $response = 1;

  if (isset($_GET["Email"]) && isset($_GET["Password"])) {
    include "connection.php";
    $sql = "SELECT Email, Password FROM Users WHERE Email='$_GET[Email]'";
    $result = $conn->query($sql);

    if ($result->num_rows == 1) {
      $row = $result->fetch_assoc();
      if ($row['Password'] == $_GET["Password"]) {
        echo "valid";
        $response = 0;
      }
    }
  }
  if ($response == 1) {
    echo "not valid";
  }

 ?>
