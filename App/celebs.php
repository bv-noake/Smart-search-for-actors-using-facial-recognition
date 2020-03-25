<?php

include "connection.php";
$sql = "SELECT Picture FROM Pictures";
$result = $conn->query($sql);
if ($result->num_rows == 1) {
  $row = $result->fetch_assoc();
  $Picture = $row['Picture'];

  echo "$Picture";
  $response = 0;

}



?>
