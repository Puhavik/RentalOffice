public class Main {
	public static void main(String args[]) {
		DBS_Helper myHelper = new DBS_Helper();
		
		
//		This are auto insert methods
		
// 		Uncomment each method separately
		
//		For inserting random data(names, addresses, phones) in my tables I use Faker. To use faker you
//		need to import pom.xml. Or insert this in your pom.xml:
//
//		<dependencies>
//			<dependency>
//				<groupId>com.github.javafaker</groupId>
//				<artifactId>javafaker</artifactId>
//				<version>0.15</version>
//			</dependency>
//		</dependencies>
		
		
// 		Insert Rental
		//myHelper.autoInsertRenatlOffice(100);
//
//		 Insert Customer
	//	myHelper.autoInsertCustomer(22); // number = customer pro office
//
//		 Insert Friends
		//myHelper.autoInsertFriends(1); // enter max quantity of friends for one customer
//
//		Insert Car
		//myHelper.autoInsertCar(25) ; // enter car pro office
//
//		Insert Truck and Passenger_Car
	//	myHelper.autoInsertTruckAndPassengerCar(); // Divides cars into Truck andPassenger Car 1:1
//
//		Insert Reservations
		//myHelper.autoInsertReservations(1); // Enter quantity of reservations for 1 customer
//
//		Insert Employee
		//myHelper.autoInsertEmployee(25); //Enter quantity of employees pro Office
//
//		Insert Delivery
		myHelper.AutoInsertDelivery(2100); // enter quantity of deliveries

	}
}