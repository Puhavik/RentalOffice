<?php
require_once ('DBSHelper.php');

$database = new DBSHelper();

$address = '';
if (isset($_POST['address'])) {
    $address = $_POST['address'];
}

$error_code = $database->deleteRentalOffice($address);

// Check result
if ($error_code == 1) {
    echo "Office with Address: '{$address}' successfully deleted!'";
} else {
    echo "Error can't delete Office with Address: '{$address}'. Errorcode: {$error_code}";
}
?>

<!-- link back to index page-->
<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>
