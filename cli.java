import java.util.Scanner;

public class cli {

	DBS_Helper myHelper = new DBS_Helper();
	public static void main(String[] args) {
		cli cli = new cli();
		
		
		// This is Command Line Interface
		
		// Uncomment each method separately!
		
		//cli.addRentalOffice();
		//cli.addCustomer();
		//cli.addFriends();
		//cli.addCar();
		cli.addReservation();
		//cli.addEmployee();
		//cli.addDelivery();
	}
	
	void addRentalOffice() {
		try (Scanner enter = new Scanner(System.in)) {
			System.out.println("Add new Rental Office" + "\n" + "Enter Rental Office's address: " );
			String address = enter.nextLine();

			System.out.println("Enter Office's Name: ");
			String officeName = enter.nextLine();
			myHelper.insertIntoRentalOffice(address, officeName);
		} catch (Exception e) {
			System.err.println("Error at: insertIntoRentalOffice\nmessage: " + e.getMessage());
		}
	}

	void addCustomer() {
		try (Scanner enter = new Scanner(System.in)) {
			System.out.println("Add new Customer");
			System.out.println("Enter customer's name: ");
			
			String name = enter.nextLine();

			System.out.println("Enter driving license number: ");
			
			int drivingLicense = enter.nextInt();
			enter.nextLine();
			System.out.println("Enter contact info (telephone number or email): ");
			String contactInfo = enter.nextLine();

			System.out.println("Enter customer's rental office ID: ");
			int rentalID = enter.nextInt();
			enter.nextLine();
			myHelper.insertIntoCustomer(drivingLicense, name, contactInfo, rentalID);
		} catch (Exception e) {
			System.err.println("Error at: insertIntoCustomer\nmessage: " + e.getMessage());
		}
	}

	void addFriends() {
		try (Scanner enter = new Scanner(System.in)) {
			System.out.println("Add friends to Customer");
			System.out.println("To which customer do you want to add friends? (driving license number): ");
			int drivingLicense1 = enter.nextInt();
			enter.nextLine();
			System.out.println("Enter friend's driving license number: ");
			int drivingLicense2 = enter.nextInt();
			enter.nextLine();
			myHelper.insertIntoFriends(drivingLicense1, drivingLicense2);
		} catch (Exception e) {
			System.err.println("Error at: insertIntoFriends\nmessage: " + e.getMessage());
		}
	}

	void addCar() {
		try (Scanner enter = new Scanner(System.in)) {
			System.out.println("Add new car");
			System.out.println("Enter car's plate number: ");
			// Scanner enter = new Scanner(System.in);
			String plateNumber = enter.nextLine();

			System.out.println("Enter car's color: ");
			String color = enter.nextLine();

			System.out.println("Enter rental price: ");
			int price = enter.nextInt();
			enter.nextLine();
			System.out.println("In which office is this car? (reantal office ID): ");
			int rentalID = enter.nextInt();
			enter.nextLine();
			myHelper.insertIntoCar(plateNumber, color, price, rentalID);

			System.out.println("Is this car a truck or a passenger car? (t/p)");
			String choice = enter.nextLine().toLowerCase();

			if (choice.equals("t")) {
				System.out.println("You chose Truck");
				System.out.println("Enter load capacity: ");
				int loadCapacity = enter.nextInt();
				enter.nextLine();
				System.out.println("Enter lengh: ");
				int length = enter.nextInt();
				enter.nextLine();
				System.out.println("Enter number of pallet spaces: ");
				int number_of_pallet_spaces = enter.nextInt();
				enter.nextLine();
				myHelper.insertIntoTruck(plateNumber, loadCapacity, length, number_of_pallet_spaces);
			} else if (choice.equals("p")) {
				System.out.println("You chose Passenger Car");
				System.out.println("Enter max speed: ");
				int maxSpeed = enter.nextInt();
				System.out.println("Enter number of seats: ");
				int number_of_seats = enter.nextInt();
				enter.nextLine();
				System.out.println("Enter fuel type: ");
				String fuel_type = enter.nextLine();
				myHelper.insertIntoPassengerCar(plateNumber, maxSpeed, number_of_seats, fuel_type);
			} else {
				System.out.println("Invalid choice. Please enter 't' for truck or 'p' for passenger car.");
			}
		} catch (Exception e) {
			System.err.println("Error at: insertIntoCar\nmessage: " + e.getMessage());
		}
	}

	void addEmployee() {
		try (Scanner enter = new Scanner(System.in)) {
			System.out.println("Add new employee");
			System.out.println("Enter employee's name");
			// Scanner enter = new Scanner(System.in);
			String name = enter.nextLine();
			System.out.println("Enter insurance number");
			int insuranceNumber = enter.nextInt();
			enter.nextLine();
			System.out.println("Enter in which rental office this emploee works? (rental ID)");
			int rentalID = enter.nextInt();
			enter.nextLine();
			myHelper.insertIntoEmployee(name, insuranceNumber, rentalID);
			// enter.close();
		} catch (Exception e) {
			System.err.println("Error at: insertIntoCar\nmessage: " + e.getMessage());
		}
	}
	
	void addReservation() {
		try (Scanner enter = new Scanner(System.in)) {
			System.out.println("Add new reservation");
			System.out.println("Begin Date (DD-MM-YYYY)");
		
			String b_date = enter.next();
			System.out.println("End Date (DD-MM-YYYY)");
			String e_date = enter.next();
			
			System.out.println("Enter Customer's driving license number: ");
			int driving_license_number = enter.nextInt();
			enter.nextLine();
			System.out.println("Enter Car's Plate number: ");
			String plateNumber = enter.nextLine();
			myHelper.insertIntoReservation(b_date, e_date, driving_license_number, plateNumber);
			// enter.close();
		} catch (Exception e) {
			System.err.println("Error at: insertIntoReservation\nmessage: " + e.getMessage());
		}
	}
	
	void addDelivery(){
		try (Scanner enter = new Scanner(System.in)){
			System.out.println("Add new delivery (Attention! Car, Employee and Customer should have same RentalID)");
			System.out.println("Which car? (plate number)");
			// Scanner enter = new Scanner(System.in);
			String plateNumber1 = enter.nextLine();
			System.out.println("Which customer? (driving license number)");
			int drivingLecense = enter.nextInt();
			enter.nextLine();
			System.out.println("Which employee? (employee id)");
			int employeeID = enter.nextInt();
			enter.nextLine();
			myHelper.insertIntoDelivery(employeeID, plateNumber1, drivingLecense);
			// enter.close();
		} catch (Exception e) {
			System.err.println("Error at: insertIntoCar\nmessage: " + e.getMessage());
		}
	}
}