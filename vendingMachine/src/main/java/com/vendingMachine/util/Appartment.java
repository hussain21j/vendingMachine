package com.vendingMachine.util;

public class Appartment {
	private Bathroom bathroom;
	long openArea;
	
	public Appartment(Bathroom bathroom, long openArea) {
		super();
		this.bathroom = bathroom;
		this.openArea = openArea;
	}


	public long getTotalAppartmentArea() {
		return (openArea + bathroom.getArea());
	}
}
