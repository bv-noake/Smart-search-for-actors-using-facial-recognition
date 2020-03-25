<?php
  if (isset($_GET["FName"]) && isset($_GET["LName"]) && isset($_GET["Email"]) && isset($_GET["Password"])) {
    include "connection.php";


    //$sql = "INSERT INTO Users (UserID, FName, LName, Email, Password) VALUES ('', '$FName', '$LName' '$Email', '$Password')";

    $sql = "INSERT INTO Users (FName, LName, Email, Password)
    VALUES ( '$_GET[FName]', '$_GET[LName]', '$_GET[Email]', '$_GET[Password]')";

    if (mysqli_query($conn, $sql)) {
        echo "valid";
    } else {
        echo "not valid";
    }

    mysqli_close($conn);

}
 ?>
