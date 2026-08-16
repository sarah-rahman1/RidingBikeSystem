package com.ride.entity;

import com.ride.enums.RideStatus;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Ride {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String current_loc;
	private String destination;
	@PositiveOrZero(message = "Fare Can't be Negative!")
	private double price;
	@Enumerated(EnumType.STRING)
	private RideStatus RideStatus;
	
	@ManyToOne
	@JoinColumn(name = "Users_ID")
	private Users users;
	
	@ManyToOne
	@JoinColumn(name = "Driver_ID")
	private Driver driver;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource() {
		return current_loc;
	}

	public void setSource(String source) {
		this.current_loc = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getFare() {
		return price;
	}

	public void setFare(double fare) {
		this.price = fare;
	}

	

	public RideStatus getRideStatus() {
		return RideStatus;
	}

	public void setRideStatus(RideStatus rideStatus) {
		RideStatus = rideStatus;
	}

	public Users getUser() {
		return users;
	}

	public void setUser(Users users) {
		this.users = users;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	
}
