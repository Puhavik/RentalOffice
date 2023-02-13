<link rel="stylesheet" href="style.css">
<br>
<form>
	<input type="button" value="Go Back"
		onclick="location.href='index.php'">
</form>

<?php
require_once ('DBSHelper.php');

$database = new DBSHelper();

$data = $database->readRentalOffice();

if (count($data) > 0) {
    echo '<table>';
    echo '<tr><th>Rental ID</th><th>Address</th><th>Office Name</th></tr>';
    foreach ($data as $row) {
        echo '<tr>';
        echo '<td class="table-data">' . $row['RENTAL_ID'] . '</td>';
        echo '<td class="table-data">' . $row['ADDRESS'] . '</td>';
        echo '<td class="table-data">' . $row['OFFICE_NAME'] . '</td>';
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