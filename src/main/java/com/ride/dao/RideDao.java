package com.ride.dao;

import java.util.List;
import java.util.Scanner;

import com.ride.entity.Driver;
import com.ride.entity.Ride;
import com.ride.entity.Transaction;
import com.ride.entity.Users;
import com.ride.enums.DriverStatus;
import com.ride.enums.RideStatus;
import com.ride.enums.TransactionMode;
import com.ride.enums.VehicleType;
import com.ride.main.Hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class RideDao {
	
	public void bookRide() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Enter User ID booking the ride: ");
			int userId = sc.nextInt();
			sc.nextLine();
			
			Users user = em.find(Users.class, userId);
			if (user == null) {
				System.err.println("User not found with ID: " + userId);
				return;
			}
			
			System.out.print("Enter Source Location: ");
			String source = sc.next();
			System.out.print("Enter Destination Location: ");
			String destination = sc.next();
			System.out.print("Enter Distance to Destination (in km): ");
			double distance = sc.nextDouble();
			
			System.out.println("1. BIKE, 2. AUTO, 3. MINI, 4. SEDAN, 5. SUV");
			System.out.print("Choice: ");
			int pref = sc.nextInt();
			sc.nextLine();
			
			VehicleType selectedType = VehicleType.BIKE;
			double ratePerKm = 10.0;
			if (pref == 2) { 
				selectedType = VehicleType.AUTO; ratePerKm = 15.0;
			}
			else if (pref == 3){ 
				selectedType = VehicleType.MINI; ratePerKm = 20.0; 
			}
			else if (pref == 4) {
				selectedType = VehicleType.SEDAN; ratePerKm = 25.0; 
			}
			else if (pref == 5) { 
				selectedType = VehicleType.SUV; ratePerKm = 35.0; 
			}
			
			List<Driver> availableDrivers = em.createQuery(
					"select d from Driver d join d.vehicle v where d.status = :dstatus and v.Vehicletype = :vtype", Driver.class)
					.setParameter("dstatus", DriverStatus.AVAILABLE)
					.setParameter("vtype", selectedType)
					.getResultList();
			
			if (availableDrivers.isEmpty()) {
				System.err.println("No drivers available for the selected vehicle type!");
				return;
			}
			
			Driver closestDriver = availableDrivers.get(0);
			double fare = distance * ratePerKm;
			
			if (user.getWallet() < fare) {
				System.err.println("Insufficient wallet balance! Fare: INR " + fare + ", Wallet: INR " + user.getWallet());
				System.err.println("Please add money to your wallet first.");
				return;
			}
			
			et.begin();
			user.setWallet(user.getWallet() - fare);
			closestDriver.setStatus(DriverStatus.BUSY);
			
			Ride ride = new Ride();
			ride.setSource(source);
			ride.setDestination(destination);
			ride.setFare(fare);
			ride.setRideStatus(RideStatus.ACCEPTED);
			ride.setUser(user);
			ride.setDriver(closestDriver);
			
			Transaction tx = new Transaction();
			tx.setTransactionMode(TransactionMode.UPI);
			tx.setAmount(fare);
			tx.setUser(user);
			tx.setRide(ride);
			
			em.persist(ride);
			em.persist(tx);
			em.merge(user);
			em.merge(closestDriver);
			et.commit();
			
			System.out.println("Ride Booked successfully. Ride ID: " + ride.getId() + ", Fare: INR " + fare);
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error booking ride: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void completeRide() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Enter Ride ID to mark as completed: ");
			int rideId = sc.nextInt();
			
			Ride ride = em.find(Ride.class, rideId);
			if (ride == null) {
				System.err.println("Ride not found with ID: " + rideId);
				return;
			}
			
			if (ride.getRideStatus() != RideStatus.ACCEPTED) {
				System.err.println("Ride cannot be completed! Current Status is: " + ride.getRideStatus());
				return;
			}
			
			Driver driver = ride.getDriver();
			
			et.begin();
			ride.setRideStatus(RideStatus.CONFIRMED);
			if (driver != null) {
				driver.setStatus(DriverStatus.AVAILABLE);
				em.merge(driver);
			}
			em.merge(ride);
			et.commit();
			
			System.out.println("Ride Completed successfully!");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error completing ride: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void cancelRide() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Enter Ride ID to cancel: ");
			int rideId = sc.nextInt();
			
			Ride ride = em.find(Ride.class, rideId);
			if (ride == null) {
				System.err.println("Ride not found with ID: " + rideId);
				return;
			}
			
			if (ride.getRideStatus() != RideStatus.ACCEPTED) {
				System.err.println("Ride cannot be cancelled! Current Status is: " + ride.getRideStatus());
				return;
			}
			
			Driver driver = ride.getDriver();
			Users user = ride.getUser();
			
			et.begin();
			ride.setRideStatus(RideStatus.CANCELLED);
			if (driver != null) {
				driver.setStatus(DriverStatus.AVAILABLE);
				em.merge(driver);
			}
			if (user != null) {
				user.setWallet(user.getWallet() + ride.getFare());
				em.merge(user);
			}
			em.merge(ride);
			et.commit();
			
			System.out.println("Ride Cancelled successfully! Fare refunded to user's wallet.");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error cancelling ride: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void viewRides() {
		EntityManager em = Hibernate.getEntityManager();
		try {
			List<Ride> ridesList = em.createQuery("from Ride", Ride.class).getResultList();
			if (ridesList.isEmpty()) {
				System.out.println("No rides found.");
				return;
			}
			for (Ride r : ridesList) {
				System.out.println("ID: " + r.getId() + ", Source: " + r.getSource() + ", Destination: " + r.getDestination() + ", Fare: " + r.getFare() + ", Status: " + r.getRideStatus());
			}
		} catch (Exception e) {
			System.err.println("Error viewing rides: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void viewRideById() {
		EntityManager em = Hibernate.getEntityManager();
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("Enter Ride ID to view: ");
			int rideId = sc.nextInt();
			
			Ride ride = em.find(Ride.class, rideId);
			if (ride != null) {
				System.out.println("ID: " + ride.getId() + ", Source: " + ride.getSource() + ", Destination: " + ride.getDestination() + ", Fare: " + ride.getFare() + ", Status: " + ride.getRideStatus());
			} else {
				System.err.println("No Ride Found");
			}
		} catch (Exception e) {
			System.err.println("Error retrieving ride: " + e.getMessage());
		} finally {
			em.close();
		}
	}
}
