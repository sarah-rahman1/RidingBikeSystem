package com.ride.main;

import java.util.Scanner;
import com.ride.service.UserService;

public class RideDriver {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Hibernate.hibernateProcessing();
		UserService userService = new UserService();
		
		while(true) {
			try {
				System.out.println("\n============= RIDE BOOKING APPLICATION ===================");
				System.out.println("1. Manage User.");
				System.out.println("2. Manage Driver.");
				System.out.println("3. Manage Vehicle.");
				System.out.println("4. Manage Rides.");
				System.out.println("5. Manage Transaction.");
				System.out.println("6. Reports. ");
				System.out.println("7. Advance Features. ");
				System.out.println("8. Exit.");
				System.out.print("Enter Your Choice: ");
				
				int choice;
				try {
					choice = sc.nextInt();
					sc.nextLine();
				} catch (Exception e) {
					System.err.println("Invalid choice input! Please enter a number.");
					sc.nextLine(); // clear scan buffer
					continue;
				}
				
				switch (choice) {
					case 1:
						userService.manageUser();
						break;
					case 2:
						userService.manageDriver();
						break;
					case 3:
						userService.manageVehicle();
						break;
					case 4:
						userService.manageRides();
						break;
					case 5:
						userService.manageTransaction();
						break;
					case 6:
						userService.manageReports();
						break;
					case 7:
						userService.advanceFeatures();
						break;
					case 8:
						System.out.println("Thank you for using the Ride Booking Application!");
						sc.close();
						System.exit(0);
					default:
						System.out.println("Enter valid choice!");
						break;
				}
			} catch (Exception e) {
				System.err.println("An unexpected error occurred in menu selection: " + e.getMessage());
			}
		}
	}
}
