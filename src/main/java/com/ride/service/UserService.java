package com.ride.service;

import java.util.List;
import java.util.Scanner;

import com.ride.dao.DriverDao;
import com.ride.dao.RideDao;
import com.ride.dao.TransactionDao;
import com.ride.dao.UserDao;
import com.ride.dao.VehicleDao;
import com.ride.entity.Driver;
import com.ride.entity.Ride;
import com.ride.entity.Transaction;
import com.ride.entity.Users;
import com.ride.entity.Vehicle;
import com.ride.enums.DriverStatus;
import com.ride.main.Hibernate;

import jakarta.persistence.EntityManager;

public class UserService {
	Scanner sc = new Scanner(System.in);
	UserDao ud = new UserDao();
	DriverDao dd = new DriverDao();
	VehicleDao vd = new VehicleDao();
	RideDao rd = new RideDao();
	TransactionDao td = new TransactionDao();
	
	public void manageUser() {
		while(true) {
			try {
				System.out.println("\n------------------ USER OPERATIONS -----------------");
				System.out.println("1. Add User.");
				System.out.println("2. View All Users.");
				System.out.println("3. Update User.");
				System.out.println("4. Delete User.");
				System.out.println("5. View User By ID.");
				System.out.println("6. Return.");
				System.out.print("Enter Your Choice: ");
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice == 1)
					ud.addUser();
				else if (choice == 2)
					ud.viewUsers();
				else if (choice == 3)
					ud.updateUser();
				else if (choice == 4)
					ud.deleteUser();
				else if (choice == 5)
					ud.viewUserById();
				else if (choice == 6)
					return;
			} catch (Exception e) {
				System.err.println("Invalid operation: " + e.getMessage());
				sc.nextLine(); // Clear buffer
			}
		}
	}
	
	public void manageDriver() {
		while(true) {
			try {
				System.out.println("\n------------------ DRIVER OPERATIONS -----------------");
				System.out.println("1. Add Driver.");
				System.out.println("2. View All Drivers.");
				System.out.println("3. Update Driver.");
				System.out.println("4. Delete Driver.");
				System.out.println("5. View Driver By ID.");
				System.out.println("6. Return.");
				System.out.print("Enter Your Choice: ");
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice == 1) dd.addDriver();
				else if (choice == 2)
					dd.viewDrivers();
				else if (choice == 3)
					dd.updateDriver();
				else if (choice == 4)
					dd.deleteDriver();
				else if (choice == 5)
					dd.viewDriverById();
				else if (choice == 6)
					return;
			} catch (Exception e) {
				System.err.println("Invalid operation: " + e.getMessage());
				sc.nextLine();
			}
		}
	}
	
	public void manageVehicle() {
		while(true) {
			try {
				System.out.println("\n------------------ VEHICLE OPERATIONS -----------------");
				System.out.println("1. Add Vehicle.");
				System.out.println("2. View All Vehicles.");
				System.out.println("3. Update Vehicle.");
				System.out.println("4. Delete Vehicle.");
				System.out.println("5. View Vehicle By ID.");
				System.out.println("6. Return.");
				System.out.print("Enter Your Choice: ");
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice == 1)
					 vd.addVehicle();
				else if (choice == 2)
					 vd.viewVehicles();
				else if (choice == 3)
					 vd.updateVehicle();
				else if (choice == 4)
					 vd.deleteVehicle();
				else if (choice == 5)
					 vd.viewVehicleById();
				else if (choice == 6)
					 return;
			} catch (Exception e) {
				System.err.println("Invalid operation: " + e.getMessage());
				sc.nextLine();
			}
		}
	}
	
	public void manageRides() {
		while(true) {
			try {
				System.out.println("\n------------------ RIDE OPERATIONS -----------------");
				System.out.println("1. Book a Ride.");
				System.out.println("2. Complete a Ride.");
				System.out.println("3. Cancel a Ride.");
				System.out.println("4. View All Rides.");
				System.out.println("5. View Ride By ID.");
				System.out.println("6. Return.");
				System.out.print("Enter Your Choice: ");
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice == 1)
					rd.bookRide();
				else if (choice == 2)
					 rd.completeRide();
				else if (choice == 3)
					 rd.cancelRide();
				else if (choice == 4)
					 rd.viewRides();
				else if (choice == 5)
					 rd.viewRideById();
				else if (choice == 6)
					 return;
			} catch (Exception e) {
				System.err.println("Invalid operation: " + e.getMessage());
				sc.nextLine();
			}
		}
	}
	
	public void manageTransaction() {
		while(true) {
			try {
				System.out.println("\n------------------ TRANSACTION OPERATIONS -----------------");
				System.out.println("1. View All Transactions.");
				System.out.println("2. View Transaction By ID.");
				System.out.println("3. Return.");
				System.out.print("Enter Your Choice: ");
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice == 1)
					td.viewTransactions();
				else if (choice == 2)
					td.viewTransactionById();
				else if (choice == 3) 
					return;
			} catch (Exception e) {
				System.err.println("Invalid operation: " + e.getMessage());
				sc.nextLine();
			}
		}
	}
	
	public void manageReports() {
		while(true) {
			System.out.println("\n------------------ REPORTS PANEL -----------------");
			System.out.println("1. View Ride History of a User");
			System.out.println("2. View Rides Completed by a Driver");
			System.out.println("3. View Transactions of a User");
			System.out.println("4. View Drivers by Status");
			System.out.println("5. Return");
			System.out.print("Enter Your Choice: ");
			
			int choice;
			try {
				choice = sc.nextInt();
				sc.nextLine();
			} catch (Exception e) {
				System.err.println("Invalid input!");
				sc.nextLine();
				continue;
			}
			
			if (choice == 5) {
				return;
			}
			
			EntityManager em = Hibernate.getEntityManager();
			try {
				if (choice == 1) {
					System.out.print("Enter User ID: ");
					int userId = sc.nextInt();
					List<Ride> userRides = em.createQuery("select r from Ride r where r.users.id = :uid", Ride.class)
							.setParameter("uid", userId)
							.getResultList();
					if (userRides.isEmpty()) {
						System.out.println("No rides found for user ID: " + userId);
					} else {
						for (Ride r : userRides) {
							System.out.println("ID: " + r.getId() + ", Path: " + r.getSource() + " -> " + r.getDestination() + ", Price: " + r.getFare() + ", Status: " + r.getRideStatus());
						}
					}
				} else if (choice == 2) {
					System.out.print("Enter Driver ID: ");
					int driverId = sc.nextInt();
					List<Ride> driverRides = em.createQuery("select r from Ride r where r.driver.id = :did", Ride.class)
							.setParameter("did", driverId)
							.getResultList();
					if (driverRides.isEmpty()) {
						System.out.println("No rides completed by driver ID: " + driverId);
					} else {
						for (Ride r : driverRides) {
							System.out.println("ID: " + r.getId() + ", Path: " + r.getSource() + " -> " + r.getDestination() + ", Price: " + r.getFare() + ", Status: " + r.getRideStatus());
						}
					}
				} else if (choice == 3) {
					System.out.print("Enter User ID: ");
					int uId = sc.nextInt();
					List<Transaction> userTx = em.createQuery("select t from Transaction t where t.users.id = :uid", Transaction.class)
							.setParameter("uid", uId)
							.getResultList();
					if (userTx.isEmpty()) {
						System.out.println("No transactions found for user ID: " + uId);
					} else {
						for (Transaction t : userTx) {
							System.out.println("ID: " + t.getId() + ", Mode: " + t.getTransactionMode() + ", Amount: " + t.getAmount());
						}
					}
				} else if (choice == 4) {
					System.out.println("1. AVAILABLE, 2. BUSY, 3. OFFLINE");
					int stat = sc.nextInt();
					DriverStatus dStatus = DriverStatus.AVAILABLE;
					if (stat == 2)
						dStatus = DriverStatus.BUSY;
					else if (stat == 3)
						dStatus = DriverStatus.OFFLINE;
					
					List<Driver> filteredDrivers = em.createQuery("select d from Driver d where d.status = :status", Driver.class)
							.setParameter("status", dStatus)
							.getResultList();
					if (filteredDrivers.isEmpty()) {
						System.out.println("No drivers with status: " + dStatus);
					} else {
						for (Driver d : filteredDrivers) {
							System.out.println("ID: " + d.getId() + ", Name: " + d.getName());
						}
					}
				} else {
					System.out.println("Enter valid choice!");
				}
			} catch (Exception e) {
				System.err.println("Error generating report: " + e.getMessage());
			} finally {
				em.close(); // Prevent connection leakage!
			}
		}
	}
	
	public void advanceFeatures() {
		while(true) {
			try {
				System.out.println("\n------------------ ADVANCED FEATURES -----------------");
				System.out.println("2. Add Money to User Wallet");
				System.out.println("3. Reassign Vehicle to Driver");
				System.out.println("4. Return");
				System.out.print("Enter Your Choice: ");
				int choice = sc.nextInt();
				sc.nextLine();
				
				if (choice == 2)
					addMoneyToWallet();
				else if (choice == 3)
					reassignVehicle();
				else if (choice == 4)
					return;
			} catch (Exception e) {
				System.err.println("Invalid operation: " + e.getMessage());
				sc.nextLine();
			}
		}
	}
	
	private void addMoneyToWallet() {
		EntityManager em = Hibernate.getEntityManager();
		jakarta.persistence.EntityTransaction et = em.getTransaction();
		try {
			System.out.print("Enter User ID: ");
			int uid = sc.nextInt();
			System.out.print("Enter Money to Add (INR): ");
			double amount = sc.nextDouble();
			
			Users user = em.find(Users.class, uid);
			if (user == null) {
				System.err.println("User not found with ID: " + uid);
				return;
			}
			
			et.begin();
			user.setWallet(user.getWallet() + amount);
			em.merge(user);
			et.commit();
			System.out.println("Money added successfully! New Wallet Balance: INR " + user.getWallet());
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error adding money: " + e.getMessage());
		} finally {
			em.close();
		}
	}
	
	private void reassignVehicle() {
		EntityManager em = Hibernate.getEntityManager();
		jakarta.persistence.EntityTransaction et = em.getTransaction();
		try {
			System.out.print("Enter Driver ID: ");
			int did = sc.nextInt();
			System.out.print("Enter Vehicle ID to Link: ");
			int vid = sc.nextInt();
			
			Driver driver = em.find(Driver.class, did);
			if (driver == null) {
				System.err.println("Driver not found with ID: " + did);
				return;
			}
			
			Vehicle vehicle = em.find(Vehicle.class, vid);
			if (vehicle == null) {
				System.err.println("Vehicle not found with ID: " + vid);
				return;
			}
			
			et.begin();
			// Dissociate old driver/vehicle associations to keep data consistency
			Driver oldDriver = vehicle.getDriver();
			if (oldDriver != null) {
				oldDriver.setVehicle(null);
				em.merge(oldDriver);
			}
			Vehicle oldVehicle = driver.getVehicle();
			if (oldVehicle != null) {
				oldVehicle.setDriver(null);
				em.merge(oldVehicle);
			}
			
			driver.setVehicle(vehicle);
			vehicle.setDriver(driver);
			em.merge(driver);
			em.merge(vehicle);
			et.commit();
			System.out.println("Vehicle assigned successfully!");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error reassigning vehicle: " + e.getMessage());
		} finally {
			em.close();
		}
	}
}
