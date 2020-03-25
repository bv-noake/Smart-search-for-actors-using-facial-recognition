<?php

$response = 1;

if (isset($_GET["Email"])) {
  include "connection.php";

  $sql = "SELECT Celebrities.FName, Celebrities.LName FROM Celebrities INNER JOIN Previous_Searches ON Celebrities.CelebID = Previous_Searches.CelebID INNER JOIN Users ON Previous_Searches.UserID = Users.UserID WHERE Users.Email = '$_GET[Email]'";
  $result = $conn->query($sql);

  $count = ($result->num_rows);
  while ($count >= 1){
    $row = $result->fetch_assoc();
    $FName = $row['FName'];
    $LName = $row['LName'];

    $count = $count -1;

    echo "$FName $LName . ";


    $response = 0;

  }
}
if ($response == 1) {
  echo "not valid";
}

?>
