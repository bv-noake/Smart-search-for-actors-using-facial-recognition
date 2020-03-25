<?php

$response = 1;

if (isset($_GET["Email"])) {
  include "connection.php";
  $sql = "SELECT FName, LName FROM Users WHERE Email='$_GET[Email]' ";
  $result = $conn->query($sql);

  if ($result->num_rows == 1) {
    $row = $result->fetch_assoc();
    $FName = $row['FName'];
    $LName = $row['LName'];

    echo "$FName $LName";
    $response = 0;

  }
}
if ($response == 1) {
  echo "not valid";
}

?>
