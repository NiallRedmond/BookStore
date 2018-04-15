package com.example.bookStore.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String date;
    private double amountPaid;
    
	public Purchase(String date, double amountPaid) {
		super();
		this.date = date;
		this.amountPaid = amountPaid;
	}

	public Purchase() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	
	
}
