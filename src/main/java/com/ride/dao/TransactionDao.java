package com.ride.dao;

import java.util.List;
import java.util.Scanner;

import com.ride.entity.Transaction;
import com.ride.main.Hibernate;

import jakarta.persistence.EntityManager;

public class TransactionDao {
	
	public void viewTransactions() {
		EntityManager em = Hibernate.getEntityManager();
		try {
			List<Transaction> transactionList = em.createQuery("from Transaction", Transaction.class).getResultList();
			if (transactionList.isEmpty()) {
				System.out.println("No transactions found.");
				return;
			}
			for (Transaction t : transactionList) {
				System.out.println("ID: " + t.getId() + ", Mode: " + t.getTransactionMode() + ", Amount: " + t.getAmount());
			}
		} catch (Exception e) {
			System.err.println("Error viewing transactions: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void viewTransactionById() {
		EntityManager em = Hibernate.getEntityManager();
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("Enter Transaction ID to view: ");
			int txId = sc.nextInt();
			
			Transaction tx = em.find(Transaction.class, txId);
			if (tx != null) {
				System.out.println("ID: " + tx.getId() + ", Mode: " + tx.getTransactionMode() + ", Amount: " + tx.getAmount());
			} else {
				System.err.println("No Transaction Found");
			}
		} catch (Exception e) {
			System.err.println("Error retrieving transaction: " + e.getMessage());
		} finally {
			em.close();
		}
	}
}
