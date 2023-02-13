<?php
require_once ('DBSHelper.php');

$database = new DBSHelper();

$address = '';
if (isset($_POST['address'])) {
    $address = $_POST['address'];
}

$office_name = '';
if (isset($_POST['office_name'])) {
    $office_name = $_POST['office_name'];
}

$success = $database->insertIntoRentalOffice($address, $office_name);

// Check result
if ($success) {
    echo "Rental Office '{$address} {$office_name}' successfully added!'";
} else {
    echo "Error can't insert Rental Office '{$address} {$office_name}'!";
}
?>

<!-- link back to index page-->
<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>