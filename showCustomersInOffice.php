<link rel="stylesheet" href="style.css">

<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>
<?php
require_once ('DBSHelper.php');

$database = new DBSHelper();

$rental_id = 0;
if (isset($_POST['rental_id'])) {
    $rental_id = $_POST['rental_id'];
}

$data = $database->showCustomersInOffice($rental_id);

?>

<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>