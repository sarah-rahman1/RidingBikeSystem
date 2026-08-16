package com.ride.dao;

import java.util.List;
import java.util.Scanner;

import com.ride.entity.Users;
import com.ride.main.Hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UserDao {
	
	public void addUser() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			Users user = new Users();
			System.out.print("Enter User Name: ");
			user.setName(sc.nextLine());
			System.out.print("Enter User Email: ");
			user.setEmail(sc.nextLine());
			System.out.print("Enter Phone Number: ");
			user.setPhoneNumber(sc.nextLong());
			System.out.print("Enter Wallet Balance: ");
			user.setWallet(sc.nextDouble());

			et.begin();
			em.persist(user);
			et.commit();

			System.out.println("User Added Successfully! Assigned ID: " + user.getId());
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error adding user (check inputs, e.g., email format): " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void viewUsers() {
		EntityManager em = Hibernate.getEntityManager();
		try {
			List<Users> usersList = em.createQuery("from Users", Users.class).getResultList();
			if (usersList.isEmpty()) {
				System.out.println("No users registered.");
				return;
			}
			for (Users u : usersList) {
				System.out.println("ID: " + u.getId() + ", Name: " + u.getName() + ", Email: " + u.getEmail() + ", Phone: " + u.getPhoneNumber() + ", Wallet: " + u.getWallet());
			}
		} catch (Exception e) {
			System.err.println("Error viewing users: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void updateUser() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Enter User ID to update: ");
			int userId = sc.nextInt();
			sc.nextLine();
			
			Users user = em.find(Users.class, userId);
			if (user == null) {
				System.err.println("User not found with ID: " + userId);
				return;
			}
			
			System.out.println("What would you like to update?");
			System.out.println("1. Name");
			System.out.println("2. Email");
			System.out.println("3. Phone Number");
			System.out.println("4. Wallet Balance");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			sc.nextLine();
			
			et.begin();
			if (choice == 1) {
				System.out.print("Enter New Name: ");
				user.setName(sc.nextLine());
			} else if (choice == 2) {
				System.out.print("Enter New Email: ");
				user.setEmail(sc.nextLine());
			} else if (choice == 3) {
				System.out.print("Enter New Phone Number: ");
				user.setPhoneNumber(sc.nextLong());
			} else if (choice == 4) {
				System.out.print("Enter New Wallet Balance: ");
				user.setWallet(sc.nextDouble());
			}
			et.commit();
			System.out.println("User Updated Successfully!");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error updating user (check input formatting/constraints): " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void deleteUser() {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction et = em.getTransaction();
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Enter User ID to delete: ");
			int userId = sc.nextInt();
			
			Users user = em.find(Users.class, userId);
			if (user == null) {
				System.err.println("User not found with ID: " + userId);
				return;
			}
			
			et.begin();
			em.remove(user);
			et.commit();
			System.out.println("User deleted successfully!");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			System.err.println("Error deleting user: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void viewUserById() {
		EntityManager em = Hibernate.getEntityManager();
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("Enter User ID to view: ");
			int userId = sc.nextInt();
			Users user = em.find(Users.class, userId);
			if (user != null) {
				System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhoneNumber() + ", Wallet: " + user.getWallet());
			} else {
				System.err.println("No User Found");
			}
		} catch (Exception e) {
			System.err.println("Error retrieving user: " + e.getMessage());
		} finally {
			em.close();
		}
	}
}