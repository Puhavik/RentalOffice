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

$error_code = $database->updateRentalOffice($address, $office_name);

if ($error_code == 1) {
    echo "Office with Address: '{$address}' successfully updated! New Name is {$office_name}";
} else {
    echo "Error can't update Office with Address: '{$address}'. Errorcode: {$error_code}";
}
?>

<!-- link back to index page-->
<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>