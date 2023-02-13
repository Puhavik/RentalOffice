<?php

require_once ('DBSHelper.php');

$database = new DBSHelper();




$driving_license_number = 0;
if (isset($_POST['driving_license_number'])) {
    $driving_license_number = $_POST['driving_license_number'];
}

$c_name = '';
if (isset($_POST['c_name'])) {
    $c_name = $_POST['c_name'];
}

$contact_info = '';
if (isset($_POST['contact_info'])) {
    $contact_info = $_POST['contact_info'];
}

$rental_id = 0;
if (isset($_POST['rental_id'])) {
    $rental_id = $_POST['rental_id'];
}


$success = $database->addCustomer($driving_license_number, $c_name, $contact_info, $rental_id);

// Check result
if ($success) {
    echo "Customer successfully added!'";
} else {
    echo "Error with adding customer '{$driving_license_number}', '{$c_name}', '{$contact_info}', '{$rental_id}'! ";
    $error = oci_error($conn);
    echo "Error Code: " . $error['code'] . "<br>";
    echo "Error Message: " . $error['message']; 
}
?>

<!-- link back to index page-->
<br>
<form>
  <input type="button" value="Go Back" onclick="location.href='index.php'">
</form>