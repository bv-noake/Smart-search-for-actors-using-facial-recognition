<?php

$response = 0;



if (isset($_POST['Image']) & (isset($_POST['names']))) {


  "include connection.php";
  $names = ($_POST["Image"]);

  $content = base64_decode($_POST["Image"]);



  $file = fopen("unknown.jpg", "wb");
  fwrite($file, $content);
  fclose($file);

  $message = exec("/Applications/XAMPP/xamppfiles/htdocs/App/database.py 2>&1" .$names);

  echo($message);

  $response = 1;

}

if ($response == 0)
{
  echo "not valid";
}






?>
