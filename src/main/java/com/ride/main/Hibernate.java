package com.ride.main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Hibernate {
	private static EntityManagerFactory emf;
	
	public static void hibernateProcessing() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("rideBookingSystem");
		}
	}
	
	public static EntityManager getEntityManager() {
		hibernateProcessing();
		return emf.createEntityManager();
	}
	
}
