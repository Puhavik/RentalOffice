<?php
require_once ('DBSHelper.php');

$database = new DBSHelper();

$driving_license_number = 0;
if (isset($_POST['driving_license_number'])) {
    $driving_license_number = $_POST['driving_license_number'];
}

$error_code = $database->deleteCustomer($driving_license_number);

// Check result
if ($error_code == 1) {
    echo "Customer with License Number: '{$driving_license_number}' successfully deleted!'";
} else {
    echo "Error can't delete Office with Address: '{$driving_license_number}'. Errorcode: {$error_code}";
}
?>

<!-- link back to index page-->
<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>