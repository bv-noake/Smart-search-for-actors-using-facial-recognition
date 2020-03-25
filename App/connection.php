<?php

  $servername = "localhost";
  $user = "root";
  $pass = "";
  $database = "Project";

  $conn = new mysqli($servername,$user,$pass, $database);

  if ($conn->connect_error)
  {
    echo "no connection";
    die ("Connection failed:  " . $conn->connect_error);
  }
  

 ?>
