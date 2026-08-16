package com.ride.dao;

import java.util.List;
import java.util.Scanner;

import com.ride.entity.Driver;
import com.ride.enums.DriverStatus;
import com.ride.main.Hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DriverDao {
	
	public void addDriver() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			Driver driver = new Driver();
			System.out.print("Enter Driver Name: ");
			driver.setName(sc.nextLine());
			System.out.print("Enter Driver Email: ");
			driver.setEmail(sc.nextLine());
			System.out.print("Enter Driver Phone: ");
			driver.setPhone(sc.nextLine());
			System.out.print("Enter Initial Latitude: ");
			driver.setLatitude(sc.nextDouble());
			System.out.print("Enter Initial Longitude: ");
			driver.setLongitude(sc.nextDouble());
			driver.setStatus(DriverStatus.AVAILABLE);

			et.begin();
			em.persist(driver);
			et.commit();
			System.out.println("Driver Added Successfully! Assigned ID: " + driver.getId());
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error adding driver: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void viewDrivers() {
		EntityManager em = Hibernate.getEntityManager();
		try {
			List<Driver> driverList = em.createQuery("from Driver", Driver.class).getResultList();
			if (driverList.isEmpty()) {
				System.out.println("No drivers registered.");
				return;
			}
			for (Driver d : driverList) {
				System.out.println("ID: " + d.getId() + ", Name: " + d.getName() + ", Email: " + d.getEmail() + ", Phone: " + d.getPhone() + ", Status: " + d.getStatus());
			}
		} catch (Exception e) {
			System.err.println("Error viewing drivers: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void updateDriver() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Enter Driver ID to update: ");
			int driverId = sc.nextInt();
			sc.nextLine();
			
			Driver driver = em.find(Driver.class, driverId);
			if (driver == null) {
				System.err.println("Driver not found with ID: " + driverId);
				return;
			}
			
			System.out.println("What would you like to update?");
			System.out.println("1. Name");
			System.out.println("2. Email");
			System.out.println("3. Phone");
			System.out.println("4. Coordinates");
			System.out.println("5. Status");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			sc.nextLine();
			
			et.begin();
			if (choice == 1) {
				System.out.print("Enter New Name: ");
				driver.setName(sc.nextLine());
			} else if (choice == 2) {
				System.out.print("Enter New Email: ");
				driver.setEmail(sc.nextLine());
			} else if (choice == 3) {
				System.out.print("Enter New Phone: ");
				driver.setPhone(sc.nextLine());
			} else if (choice == 4) {
				System.out.print("Enter New Latitude: ");
				driver.setLatitude(sc.nextDouble());
				System.out.print("Enter New Longitude: ");
				driver.setLongitude(sc.nextDouble());
			} else if (choice == 5) {
				System.out.println("1. AVAILABLE, 2. BUSY, 3. OFFLINE");
				int statusChoice = sc.nextInt();
				if (statusChoice == 1) driver.setStatus(DriverStatus.AVAILABLE);
				else if (statusChoice == 2) driver.setStatus(DriverStatus.BUSY);
				else if (statusChoice == 3) driver.setStatus(DriverStatus.OFFLINE);
			}
			et.commit();
			System.out.println("Driver Updated Successfully!");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error updating driver: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void deleteDriver() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Enter Driver ID to delete: ");
			int driverId = sc.nextInt();
			
			Driver driver = em.find(Driver.class, driverId);
			if (driver == null) {
				System.err.println("Driver not found with ID: " + driverId);
				return;
			}
			
			et.begin();
			em.remove(driver);
			et.commit();
			System.out.println("Driver deleted successfully!");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error deleting driver: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void viewDriverById() {
		EntityManager em = Hibernate.getEntityManager();
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("Enter Driver ID to view: ");
			int driverId = sc.nextInt();
			Driver driver = em.find(Driver.class, driverId);
			if (driver == null) {
				System.err.println("Driver not found with ID: " + driverId);
				return;
			}
			System.out.println("ID: " + driver.getId() + ", Name: " + driver.getName() + ", Email: " + driver.getEmail() + ", Phone: " + driver.getPhone() + ", Status: " + driver.getStatus());
		} catch (Exception e) {
			System.err.println("Error retrieving driver: " + e.getMessage());
		} finally {
			em.close();
		}
	}
}
