
<?php
require_once ('DBSHelper.php');

$database = new DBSHelper();

$driving_license_number = 0;
if (isset($_POST['driving_license_number'])) {
    $driving_license_number = $_POST['driving_license_number'];
}

$contact_info = '';
if (isset($_POST['contact_info'])) {
    $contact_info = $_POST['contact_info'];
}

$error_code = $database->updateCustomer($driving_license_number, $contact_info);

if ($error_code == 1) {
    echo "Customer with License Number: '{$driving_license_number}' successfully updated!";
} else {
    echo "Error can't update Customer with License Number: '{$driving_license_number}'. Errorcode: {$error_code}";
}
?>

<!-- link back to index page-->
<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>