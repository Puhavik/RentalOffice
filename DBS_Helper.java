//Database Systems (Module IDS) 

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.github.javafaker.Faker;

// The DatabaseHelper class encapsulates the communication with our database
class DBS_Helper {
	// Database connection info
	private static final String DB_CONNECTION_URL = "jdbc:oracle:thin:@oracle19.cs.univie.ac.at:1521:orclcdb";
	private static final String USER = "";
	private static final String PASS = "";

	// The name of the class loaded from the ojdbc14.jar driver file
	private static final String CLASSNAME = "oracle.jdbc.driver.OracleDriver";

	// We need only one Connection and one Statement during the execution => class
	// variable
	private static Statement stmt;
	private static Connection con;

	// CREATE CONNECTION
	DBS_Helper() {
		try {
			// Loads the class into the memory
			Class.forName(CLASSNAME);

			// establish connection to database
			con = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASS);
			stmt = con.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Rental Office
	void insertIntoRentalOffice(String address, String officeName) {
		try (PreparedStatement stmt = con.prepareStatement("INSERT INTO rental_office (address, office_name) VALUES (?, ?)")){
			stmt.setString(1, address);
			stmt.setString(2, officeName + " Office");
			System.out.println("Done");
	        stmt.executeUpdate();
		} catch (Exception e) {
			System.err.println("Error at: insertIntoRentalOffice\nmessage: " + e.getMessage());
		}
		System.out.println("Done");
	}


	void autoInsertRenatlOffice(int n) {
		Faker faker = new Faker();
		for (int i = 0; i < n; i++) {
			this.insertIntoRentalOffice(faker.address().fullAddress(), faker.address().streetName());
		}
		System.out.println("AutoInsert Done");
	}

	// Customer
	void insertIntoCustomer(int drivingLicense, String name, String contactInfo, int rentalID) {
	    try (PreparedStatement stmt = con.prepareStatement("INSERT INTO customer (driving_license_number, c_name, contact_info, rental_id) VALUES (?, ?, ?, ?)")) {
	        stmt.setInt(1, drivingLicense);
	        stmt.setString(2, name);
	        stmt.setString(3, contactInfo);
	        stmt.setInt(4, rentalID);
	        stmt.executeUpdate();
	    } catch (Exception e) {
	        System.err.println("Error at: insertIntoCustomer\nmessage: " + e.getMessage());
	    }
	    System.out.println("Done");
	}

	void autoInsertCustomer(int n) { // n = customer pro office
		Faker faker = new Faker();
		for (int ID : this.selectRentalIDs()) {
			for (int i = 0; i < n; i++) {
				this.insertIntoCustomer(Integer.parseInt(faker.number().digits(6)), faker.name().fullName(),
						faker.phoneNumber().cellPhone(), ID);
			}
		}
		System.out.println("AutoInsert Done");
	}

	// Friends
	void insertIntoFriends(int drivingLecense1, int drivingLecense2) {
		try {
			String friends = "INSERT INTO friends VALUES ('" + drivingLecense1 + "','" + drivingLecense2 + "')";
			stmt.execute(friends);
		} catch (Exception e) {
			System.err.println("Error at: insertIntoFriends\nmessage: " + e.getMessage());
		}
		System.out.println("Done");
	}

	void autoInsertFriends(int n) {
	    ArrayList<Integer> licenses = this.selectDrivingLicenses();
	    //Random rand = new Random();
	    for (int license : licenses) {
	    	ArrayList<Integer> otherLicenses = new ArrayList<>(licenses);
	        Collections.shuffle(otherLicenses);
	        for (int i = 0; i < n; i++) {
	            if(i < otherLicenses.size() && license != otherLicenses.get(i)) {
	                this.insertIntoFriends(license, otherLicenses.get(i));
	            } else {
	                break;
	            }
	        }
	    }
	    System.out.println("AutoInsert Done");
	}


	// Car
	void insertIntoCar(String plateNumber, String color, int price, int rentalID) {
		try {
			String car = "INSERT INTO car VALUES ('" + plateNumber + "','" + color + "'," + price + "," + rentalID
					+ ")";
			stmt.execute(car);
		} catch (Exception e) {
			System.err.println("Error at: insertIntoCar\nmessage: " + e.getMessage());
		}
		System.out.println("Done");
	}

	void autoInsertCar(int n) { // n = car pro office
		Faker faker = new Faker();
		for (int ID : this.selectRentalIDs()) {
			for (int i = 0; i < n; i++) {
				char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
				Random rand = new Random();
				int randomIndex = rand.nextInt(alphabet.length);
				char randomLetter = alphabet[randomIndex];
				this.insertIntoCar(randomLetter + faker.number().digits(6), faker.color().name(),
						faker.number().numberBetween(50, 1000), ID);
			}
		}
		System.out.println("AutoInsert Done");
	}

	// Truck
	void insertIntoTruck(String plateNumber, int loadCapacity, int length, int number_of_pallet_spaces) {
		try {
			String truck = "INSERT INTO truck VALUES ('" + plateNumber + "'," + loadCapacity + "," + length + ","
					+ number_of_pallet_spaces + ")";
			stmt.execute(truck);
		} catch (Exception e) {
			System.err.println("Error at: insertIntoTruck\nmessage: " + e.getMessage());
		}
		System.out.println("Done");
	}

	// Passenger_Car
	void insertIntoPassengerCar(String plateNumber, int maxSpeed, int number_of_seats, String fuel_type) {
		try {
			String passengerCar = "INSERT INTO passenger_car VALUES ('" + plateNumber + "'," + maxSpeed + ","
					+ number_of_seats + ",'" + fuel_type + "')";
			stmt.execute(passengerCar);
		} catch (Exception e) {
			System.err.println("Error at: insertIntoPassengerCar\nmessage: " + e.getMessage());
		}
		System.out.println("Done");
	}

	void autoInsertTruckAndPassengerCar() {
		Faker faker = new Faker();
		boolean isTruck = true;
		for (String plateNumber : this.selectPlateNumbers()) {
			if (isTruck) {
				int length = faker.number().numberBetween(900, 1300);
				this.insertIntoTruck(plateNumber, faker.number().numberBetween(3500, 20000), length, length / 80);
				isTruck = false;
			} else {
				this.insertIntoPassengerCar(plateNumber, faker.number().numberBetween(100, 400),
						faker.number().numberBetween(2, 8), this.getRandomFueltyp());
				isTruck = true;
			}
		}
		System.out.println("AutoInsert Done");
	}

	// Reservation
	void insertIntoReservation(String b_date, String e_date, int drivingLecense, String plateNumber) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM reservation WHERE plate_number = '" + plateNumber
					+ "' AND ((reservation_begin BETWEEN to_date('" + b_date + "', 'dd-mm-yyyy') AND to_date('" + e_date
					+ "', 'dd-mm-yyyy')) OR (reservation_end BETWEEN to_date('" + b_date
					+ "', 'dd-mm-yyyy') AND to_date('" + e_date + "', 'dd-mm-yyyy')) OR (reservation_begin <= to_date('"
					+ b_date + "', 'dd-mm-yyyy') AND reservation_end >= to_date('" + e_date + "', 'dd-mm-yyyy')))");
			if (!rs.next()) {
				try {
					String reservation = "INSERT INTO reservation (reservation_begin, reservation_end, driving_license_number, plate_number) VALUES "
							+ "(to_date('" + b_date + "', 'dd-mm-yyyy'),to_date('" + e_date + "', 'dd-mm-yyyy'),"
							+ drivingLecense + ",'" + plateNumber + "')";
					stmt.execute(reservation);
				} catch (Exception e) {
					System.err.println("Error at: insertIntoReservation\nmessage: " + e.getMessage());
				}
			} else {
				System.err.println("Error: Car is buisy at these days");
			}
		} catch (SQLException e) {
			System.err.println("Error: Cars" + e.getMessage());
		}
		System.out.println("Done");
	}

	void autoInsertReservations(int n) { // n = quantity of reservations for 1 customer
		Random rand = new Random();
		for (int license : this.selectDrivingLicenses()) {
			for (int i = 0; i < n; i++) {
				String beginDate = this.getRandomBeginDate();
				String endDate = this.getRandomEndDate(beginDate);
				int randomIndex = rand.nextInt(this.selectPlateNumbers().size());
				this.insertIntoReservation(beginDate, endDate, license, this.selectPlateNumbers().get(randomIndex));

			}
		}
		System.out.println("AutoInsert Done");
	}

	// Employee
	void insertIntoEmployee(String name, int insuranceNumber, int rentalID) {
		try(PreparedStatement stmt = con.prepareStatement("INSERT INTO employee (employee_id, name_e, insurance_number, rental_id) VALUES (seq_employee_id.nextval,?,?,?)")) {
			stmt.setString(1, name);
			stmt.setInt(2, insuranceNumber);
			stmt.setInt(3, rentalID);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.err.println("Error at: insertIntoEmployee\nmessage: " + e.getMessage());
		}
		System.out.println("Done");
	}

	void autoInsertEmployee(int n) { // n = quantity of employees pro office
		Faker faker = new Faker();
		for (int ID : this.selectRentalIDs()) {
			for (int i = 0; i < n; i++) {
				this.insertIntoEmployee(faker.name().fullName(), Integer.parseInt(faker.number().digits(6)), ID);
			}
		}
		System.out.println("AutoInsert Done");
	}

	// Check if Same Office
	boolean checkSameOffice(int employeeID, String carPlateNumber, int drivingLicense) {
		int customerRentalOfficeID = 0;
		int employeeRentalOfficeID = 0;
		int carRentalOfficeID = 0;

		// Retrieve the rental office ID for the customer
		try {
			ResultSet customerRS = stmt
					.executeQuery("SELECT rental_id FROM customer WHERE driving_license_number = " + drivingLicense);
			if (customerRS.next()) {
				customerRentalOfficeID = customerRS.getInt("rental_id");
				customerRS.close();
			}
		} catch (Exception e) {
			System.err.println(("Error at: checkSameOfficeCustomer\n message: " + e.getMessage()).trim());
		}

		// Retrieve the rental office ID for the employee
		try {
			ResultSet employeeRS = stmt
					.executeQuery("SELECT rental_id FROM employee WHERE employee_id = " + employeeID);
			if (employeeRS.next()) {
				employeeRentalOfficeID = employeeRS.getInt("rental_id");
			}
			employeeRS.close();
		} catch (Exception e) {
			System.err.println(("Error at: checkSameOfficeEmployee\n message: " + e.getMessage()).trim());
		}

		// Retrieve the rental office ID for the car
		try {
			ResultSet carRS = stmt
					.executeQuery("SELECT rental_id FROM car WHERE plate_number = '" + carPlateNumber + "'");
			if (carRS.next()) {
				carRentalOfficeID = carRS.getInt("rental_id");
			}
			carRS.close();
		} catch (Exception e) {
			System.err.println(("Error at: checkSameOfficeCar\n message: " + e.getMessage()).trim());
		}

		// Check if the rental office IDs match for the customer, employee, and car
		if (customerRentalOfficeID == employeeRentalOfficeID && employeeRentalOfficeID == carRentalOfficeID) {
			return true;
		} else {
			return false;
		}
	}

	// Delivery
	void insertIntoDelivery(int employeeID, String plateNumber, int drivingLecense) {
		if (checkSameOffice(employeeID, plateNumber, drivingLecense)) {
			try {
				String delivery = "INSERT INTO delivery (employee_ID, plate_number, driving_license_number) VALUES ("
						+ employeeID + ",'" + plateNumber + "'," + drivingLecense + ")";
				stmt.execute(delivery);
			} catch (Exception e) {
				System.err.println("Error at: insertIntoDelivery\nmessage: " + e.getMessage());
			}
		} else {
			System.err.println("Error Not Same Office!");
		}
		System.out.println("Done");
	}

	void AutoInsertDelivery(int n) {
		for (int i = 0; i < n; i++) {
			int drivingLicense = 0;
			String plateNumber = "";
			int employeeID = 0;
			int officeID = 0;
			Random rand = new Random();
			int randomOffice = rand.nextInt(this.selectRentalIDs().size());
			officeID = this.selectRentalIDs().get(randomOffice);
			try {
				ResultSet rsPlate = stmt.executeQuery(
						"SELECT car.plate_number FROM car JOIN employee ON car.rental_id = employee.rental_id JOIN customer ON car.rental_id = customer.rental_id WHERE car.rental_id ="
								+ officeID);
				if (rsPlate.next()) {
					plateNumber = rsPlate.getString("plate_number");
					rsPlate.close();
				}
			} catch (Exception e) {
				System.err.println("Error at: AutoInsertDelivery\n message: " + e.getMessage());
			}
			try {
				ResultSet rsEmployee = stmt.executeQuery(
						"SELECT employee.employee_id FROM car JOIN employee ON car.rental_id = employee.rental_id JOIN customer ON car.rental_id = customer.rental_id WHERE car.rental_id ="
								+ officeID);
				if (rsEmployee.next()) {
					employeeID = rsEmployee.getInt("employee_id");
					rsEmployee.close();
				}
			} catch (Exception e) {
				System.err.println("Error at: AutoInsertDelivery\n message: " + e.getMessage());
			}
			try {
				ResultSet rsLicense = stmt.executeQuery(
						"SELECT customer.driving_license_number FROM car JOIN employee ON car.rental_id = employee.rental_id JOIN customer ON car.rental_id = customer.rental_id WHERE car.rental_id ="
								+ officeID);
				if (rsLicense.next()) {
					drivingLicense = rsLicense.getInt("driving_license_number");
					rsLicense.close();
				}
			} catch (Exception e) {
				System.err.println("Error at: AutoInsertDelivery\n message: " + e.getMessage());
			}

			this.insertIntoDelivery(employeeID, plateNumber, drivingLicense);
		}
		System.out.println("AutoInsert Done");

	}

	// SELECT
	ArrayList<Integer> selectRentalIDs() {
		ArrayList<Integer> IDs = new ArrayList<>();

		try {
			ResultSet rs = stmt.executeQuery("SELECT rental_ID FROM rental_office ORDER BY rental_id");
			while (rs.next()) {
				IDs.add(rs.getInt("rental_id"));
			}
			rs.close();
		} catch (Exception e) {
			System.err.println(("Error at: selectRentalIDs\n message: " + e.getMessage()).trim());
		}
		return IDs;
	}

	ArrayList<Integer> selectDrivingLicenses() {
		ArrayList<Integer> licenses = new ArrayList<>();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT driving_license_number FROM customer ORDER BY driving_license_number");
			while (rs.next()) {
				licenses.add(rs.getInt("driving_license_number"));
			}
			rs.close();
		} catch (Exception e) {
			System.err.println(("Error at: selectDrivingLicenses\n message: " + e.getMessage()).trim());
		}
		return licenses;
	}

	ArrayList<String> selectPlateNumbers() {
		ArrayList<String> plateNumbers = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT plate_number FROM car ORDER BY plate_number");
			while (rs.next()) {
				plateNumbers.add(rs.getString("plate_number"));
			}
			rs.close();
		} catch (Exception e) {
			System.err.println(("Error at: selectPlateNumbers\n message: " + e.getMessage()).trim());
		}
		return plateNumbers;
	}

	ArrayList<Integer> selectEmployeeID() {
		ArrayList<Integer> employeeID = new ArrayList<>();

		try {
			ResultSet rs = stmt.executeQuery("SELECT employee_ID FROM employee ORDER BY employee_ID");
			while (rs.next()) {
				employeeID.add(rs.getInt("employee_ID"));
			}
			rs.close();
		} catch (Exception e) {
			System.err.println(("Error at: selectEmployeeID\n message: " + e.getMessage()).trim());
		}
		return employeeID;
	}

	String getRandomFueltyp() {
		ArrayList<String> fuelTypes = new ArrayList<String>(Arrays.asList("Diesel", "Electric", "Hybrid", "Gasoline"));
		Random rand = new Random();
		int randomIndex = rand.nextInt(fuelTypes.size());
		String randomFuelType = fuelTypes.get(randomIndex);
		return randomFuelType;
	}

	String getRandomBeginDate() {
		LocalDate begin = LocalDate.of(2020, 1, 1);
		LocalDate end = LocalDate.of(2022, 12, 31);
		long days = ChronoUnit.DAYS.between(begin, end);
		LocalDate randomDate = begin.plusDays((long) (Math.random() * days));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return randomDate.format(formatter);
	}

	String getRandomEndDate(String beginDate) {
		Faker faker = new Faker();
		String ret;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date = LocalDate.parse(beginDate, formatter);

		int days = faker.number().numberBetween(1, 30);

		LocalDate date2 = date.plusDays(days);
		DateTimeFormatter formatterRet = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		ret = date2.format(formatterRet);
		return ret;
	}

	public void close() {
		try {
			stmt.close(); // clean up
			con.close();
		} catch (Exception ignored) {
		}
	}
}
