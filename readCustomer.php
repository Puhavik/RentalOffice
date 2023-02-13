
<link rel="stylesheet" href="style.css">

<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>
<?php
require_once ('DBSHelper.php');

$database = new DBSHelper();

$data = $database->readCustomer();

if (count($data) > 0) {
    echo '<table>';
    echo '<tr><th>Driving License</th><th>Name</th><th>Contact Info</th><th>Rental ID</th></tr>';
    foreach ($data as $row) {
        echo '<tr>';
        echo '<td class="table-data">' . $row['DRIVING_LICENSE_NUMBER'] . '</td>';
        echo '<td class="table-data">' . $row['C_NAME'] . '</td>';
        echo '<td class="table-data">' . $row['CONTACT_INFO'] . '</td>';
        echo '<td class="table-data">' . $row['RENTAL_ID'] . '</td>';
        echo '</td>';
    }
    echo '</table>';
} else {
    echo "Error with reading.";
}

$database->__destruct();
?>

<!-- link back to index page-->
<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>
