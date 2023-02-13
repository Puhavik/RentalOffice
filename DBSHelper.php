<?php

class DBSHelper
{

    // Since the connection details are constant, define them as const
    // We can refer to constants like e.g. DatabaseHelper::username
    const username = '';

    // use a + your matriculation number
    const password = '';

    // use your oracle db password
    const con_string = 'oracle19.cs.univie.ac.at:1521/orclcdb';

    // on almighty "lab" is sufficient

    // Since we need only one connection object, it can be stored in a member variable.
    // $conn is set in the constructor.
    protected $conn;

    // Create connection in the constructor
    public function __construct()
    {
        try {
            // Create connection with the command oci_connect(String(username), String(password), String(connection_string))
            // The @ sign avoids the output of warnings
            // It could be helpful to use the function without the @ symbol during developing process
            $this->conn = @oci_connect(DBSHelper::username, DBSHelper::password, DBSHelper::con_string);

            // check if the connection object is != null
            if (! $this->conn) {
                // die(String(message)): stop PHP script and output message:
                die("DB error: Connection can't be established!");
            }
        } catch (Exception $e) {
            die("DB error: {$e->getMessage()}");
        }
    }

    // Used to clean up
    public function __destruct()
    {
        // clean up
        @oci_close($this->conn);
    }

    
    public function insertIntoRentalOffice($address, $office_name)
    {
        // Enable DBMS_OUTPUT
        $enable_output = oci_parse($this->conn, "BEGIN DBMS_OUTPUT.ENABLE(NULL); END;");
        oci_execute($enable_output);
        oci_free_statement($enable_output);
        
        // Execute insert statement
        $sql = "INSERT INTO rental_office (address, office_name) VALUES ('{$address}', '{$office_name}')";
        $statement = oci_parse($this->conn, $sql);
        $success = oci_execute($statement) && oci_commit($this->conn);
        oci_free_statement($statement);
        
        // Retrieve DBMS_OUTPUT
        $output = '';
        $get_output = oci_parse($this->conn, "BEGIN DBMS_OUTPUT.GET_LINE(:output, :status); END;");
        oci_bind_by_name($get_output, ":output", $output, 32767);
        oci_bind_by_name($get_output, ":status", $status);
        while ($status == 0) {
            oci_execute($get_output);
            echo $output . "<br>";
        }
        
        return $success;
    }
    

    public function deleteRentalOffice($address)
    {
        $sql = "SELECT * FROM rental_office WHERE address = '{$address}'";

        $statement = @oci_parse($this->conn, $sql);
        $success = @oci_execute($statement) && @oci_commit($this->conn);
        if (@oci_fetch($statement)) {
            $sql = "DELETE FROM rental_office WHERE address = '{$address}'";
            $statement = @oci_parse($this->conn, $sql);
            $success = @oci_execute($statement) && @oci_commit($this->conn);
            @oci_free_statement($statement);
            return $success;
        } else {
            echo "Not Found ";
        }
    }

    public function updateRentalOffice($address, $office_name)
    {
        $sql = "SELECT * FROM rental_office WHERE address = '{$address}'";
        $statement = @oci_parse($this->conn, $sql);
        $success = @oci_execute($statement) && @oci_commit($this->conn);
        if (@oci_fetch($statement)) {
            $sql = "UPDATE rental_office SET office_name = '{$office_name}' WHERE address = '{$address}'";
            $statement = @oci_parse($this->conn, $sql);
            $success = @oci_execute($statement) && @oci_commit($this->conn);
            @oci_free_statement($statement);
            return $success;
        } else {
            echo "Not Found ";
        }
    }

    public function readRentalOffice()
    {
        $sql = "SELECT * FROM rental_office ORDER BY rental_id";
        $statement = oci_parse($this->conn, $sql);
        $success = oci_execute($statement);
        $data = array();
        while ($row = oci_fetch_assoc($statement)) {
            $data[] = $row;
        }
        oci_free_statement($statement);
        return $data;
    }

    public function addCustomer($driving_license_number, $c_name, $contact_info, $rental_id)
    {
        $sql = "INSERT INTO customer (driving_license_number, c_name, contact_info, rental_id) VALUES (:driving_license_number, :c_name, :contact_info, :rental_id)";
        $statement = oci_parse($this->conn, $sql);
        oci_bind_by_name($statement, ':driving_license_number', $driving_license_number);
        oci_bind_by_name($statement, ':c_name', $c_name);
        oci_bind_by_name($statement, ':contact_info', $contact_info);
        oci_bind_by_name($statement, ':rental_id', $rental_id);
        $success = @oci_execute($statement) && @oci_commit($this->conn);
        @oci_free_statement($statement);
        return $success;
    }

    public function deleteCustomer($driving_license_number)
    {
        $sql = "SELECT * FROM customer WHERE driving_license_number = :driving_license_number";
        $statement = @oci_parse($this->conn, $sql);
        oci_bind_by_name($statement, ':driving_license_number', $driving_license_number);
        $success = @oci_execute($statement) && @oci_commit($this->conn);
        if (@oci_fetch($statement)) {
            $sql = "DELETE FROM customer WHERE driving_license_number = :driving_license_number";
            $statement = @oci_parse($this->conn, $sql);
            oci_bind_by_name($statement, ':driving_license_number', $driving_license_number);
            $success = @oci_execute($statement) && @oci_commit($this->conn);
            @oci_free_statement($statement);
            return $success;
        } else {
            echo "Not Found ";
        }
    }

    public function updateCustomer($driving_license_number, $contact_info)
    {
        $sql = "SELECT * FROM customer WHERE driving_license_number = :driving_license_number";
        $statement = @oci_parse($this->conn, $sql);
        oci_bind_by_name($statement, ':driving_license_number', $driving_license_number);
        $success = @oci_execute($statement) && @oci_commit($this->conn);
        if (@oci_fetch($statement)) {
            $sql = "UPDATE customer SET contact_info = :contact_info WHERE driving_license_number = :driving_license_number";
            $statement = @oci_parse($this->conn, $sql);
            oci_bind_by_name($statement, ':driving_license_number', $driving_license_number);
            oci_bind_by_name($statement, ':contact_info', $contact_info);
            $success = @oci_execute($statement) && @oci_commit($this->conn);
            @oci_free_statement($statement);
            return $success;
        } else {
            echo "Not Found ";
        }
    }

    public function readCustomer()
    {
        $sql = "SELECT * FROM customer ORDER BY driving_license_number";
        $statement = oci_parse($this->conn, $sql);
        $success = oci_execute($statement);
        $data = array();
        while ($row = oci_fetch_assoc($statement)) {
            $data[] = $row;
        }
        oci_free_statement($statement);
        return $data;
    }

    public function showCustomersInOffice($rental_id)
    {
        oci_set_client_info($this->conn, "SET SERVEROUTPUT ON");
        $sql = "BEGIN
                show_customer_in_office(:rental_id, :number_of_customer);
                END;";
        $statement = oci_parse($this->conn, $sql);
        oci_bind_by_name($statement, ':rental_id', $rental_id);
        $count = 0;
        oci_bind_by_name($statement, ':number_of_customer', $count, - 1, SQLT_INT);
        $success = oci_execute($statement);
        if (! $success) {
            $err = oci_error($statement);
            trigger_error(htmlentities($err['message'], ENT_QUOTES), E_USER_ERROR);
        }
        echo 'Number of Customers in Office ' . $rental_id . ' is:   ' . $count;
        oci_free_statement($statement);
    }
}
