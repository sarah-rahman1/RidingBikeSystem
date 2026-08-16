package com.ride.dao;

import java.util.List;
import java.util.Scanner;

import com.ride.entity.Driver;
import com.ride.entity.Vehicle;
import com.ride.enums.VehicleType;
import com.ride.main.Hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class VehicleDao {
	
	public void addVehicle() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			Vehicle vehicle = new Vehicle();
			System.out.println("1. BIKE, 2. AUTO, 3. MINI, 4. SEDAN, 5. SUV");
			System.out.print("Enter Choice: ");
			int typeChoice = sc.nextInt();
			sc.nextLine();
			
			VehicleType type = VehicleType.BIKE;
			if (typeChoice == 2)
				type = VehicleType.AUTO;
			else if (typeChoice == 3)
				type = VehicleType.MINI;
			else if (typeChoice == 4)
				type = VehicleType.SEDAN;
			else if (typeChoice == 5)
				type = VehicleType.SUV;
			vehicle.setVehicletype(type);
			
			System.out.print("Enter Vehicle Number (format e.g., MH12AB1234): ");
			vehicle.setVehicleNumber(sc.next().toUpperCase());
			
			System.out.print("Enter Driver ID to assign (or 0 for none): ");
			int driverId = sc.nextInt();
			
			et.begin();
			if (driverId != 0) {
				Driver driver = em.find(Driver.class, driverId);
				if (driver == null) {
					System.err.println("Driver not found with ID: " + driverId + ". Vehicle creation aborted.");
					if (et.isActive()) et.rollback();
					return;
				}
				vehicle.setDriver(driver);
				driver.setVehicle(vehicle);
				em.persist(vehicle);
				em.merge(driver);
			} else {
				em.persist(vehicle);
			}
			et.commit();
			System.out.println("Vehicle Added Successfully!");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error adding vehicle (check input formats/constraints): " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void viewVehicles() {
		EntityManager em = Hibernate.getEntityManager();
		try {
			List<Vehicle> vehicleList = em.createQuery("from Vehicle", Vehicle.class).getResultList();
			if (vehicleList.isEmpty()) {
				System.out.println("No vehicles registered.");
				return;
			}
			for (Vehicle v : vehicleList) {
				System.out.println("ID: " + v.getId() + ", Type: " + v.getVehicletype() + ", Number: " + v.getVehicleNumber());
			}
		} catch (Exception e) {
			System.err.println("Error viewing vehicles: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void updateVehicle() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Enter Vehicle ID to update: ");
			int vehicleId = sc.nextInt();
			sc.nextLine();
			
			Vehicle vehicle = em.find(Vehicle.class, vehicleId);
			if (vehicle == null) {
				System.err.println("Vehicle not found with ID: " + vehicleId);
				return;
			}
			
			System.out.println("1. Vehicle Type");
			System.out.println("2. Vehicle Number");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();
			sc.nextLine();
			
			et.begin();
			if (choice == 1) {
				System.out.println("1. BIKE, 2. AUTO, 3. MINI, 4. SEDAN, 5. SUV");
				int t = sc.nextInt();
				
				VehicleType type = VehicleType.BIKE;
				if (t == 2)
					 type = VehicleType.AUTO;
				else if (t == 3)
					 type = VehicleType.MINI;
				else if (t == 4)
					 type = VehicleType.SEDAN;
				else if (t == 5)
					 type = VehicleType.SUV;
				
				vehicle.setVehicletype(type);
			} else if (choice == 2) {
				System.out.print("Enter New Vehicle Number (format e.g., MH12AB1234): ");
				vehicle.setVehicleNumber(sc.nextLine().toUpperCase());
			}
			et.commit();
			System.out.println("Vehicle Updated Successfully!");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error updating vehicle: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void deleteVehicle() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Enter Vehicle ID to delete: ");
			int vehicleId = sc.nextInt();
			
			Vehicle vehicle = em.find(Vehicle.class, vehicleId);
			if (vehicle == null) {
				System.err.println("Vehicle not found with ID: " + vehicleId);
				return;
			}
			
			et.begin();
			// Dissociate from driver if assigned to avoid cascade/constraint issues
			Driver driver = vehicle.getDriver();
			if (driver != null) {
				driver.setVehicle(null);
				em.merge(driver);
			}
			em.remove(vehicle);
			et.commit();
			System.out.println("Vehicle deleted successfully!");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error deleting vehicle: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void viewVehicleById() {
		EntityManager em = Hibernate.getEntityManager();
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("Enter Vehicle ID to view: ");
			int vehicleId = sc.nextInt();
			Vehicle vehicle = em.find(Vehicle.class, vehicleId);
			if (vehicle != null) {
				System.out.println("ID: " + vehicle.getId() + ", Type: " + vehicle.getVehicletype() + ", Number: " + vehicle.getVehicleNumber());
			} else {
				System.err.println("No Vehicle Found");
			}
		} catch (Exception e) {
			System.err.println("Error retrieving vehicle: " + e.getMessage());
		} finally {
			em.close();
		}
	}
}
