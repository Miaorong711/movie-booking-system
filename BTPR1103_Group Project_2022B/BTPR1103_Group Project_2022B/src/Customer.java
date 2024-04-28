/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * BTPR1103_Group Project_2022B
 * Movie Ticket System
 * Author: Gwi Miao Rong B210086B
 */

public class Customer {
    
    //instance variable
	private int id;
	private String name;
	private String movie;
	private String[] seatNo;
	private int adultQty;
	private int childQty;
        
	//default constructor
	public Customer() {
		id = 0;
		name = "unknown";
		movie = "unknown";
		seatNo = new String[adultQty + childQty];
		adultQty = 0;
		childQty = 0;
	}  
        
	//constructor with parameter
	public Customer(int id, String name, String movie, String[] seatNo, int adultQty, int childQty) {
		
		this.id = id;
		this.name = name;
		this.movie = movie;
		this.seatNo = seatNo;
		this.adultQty = adultQty;
		this.childQty = childQty;	
	}        
        
	//get method
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMovie() {
		return movie;
	}
	
	public String[] getSeatNo() {
		return seatNo;
	}
	
	public int getAdultQty() {
		return adultQty;
	}
	
	public int getChildQty() {
		return childQty;
	}        
 
	//set method
	public void setID(int theID) {
		id = theID;
	}
	
	public void setName(String theName) {
		name = theName;
	}
	
	public void setMovie(String theMovie) {
		movie = theMovie;
	}
	
	public void setSeatNo(String[] theSeatNo) {
		seatNo = theSeatNo;
	}
	
	public void setAdultQty(int theAdultQty) {
		adultQty = theAdultQty; 
	}
	
	public void setChildQty(int theChildQty) {
		childQty = theChildQty;
	}        
        
        //calculate total amount method
	public double calculateTotalAmount() {
		int adultPrice = 25;
		int childPrice = 15;
		
		return Math.round((getAdultQty() * adultPrice + getChildQty() * childPrice)*100.0)/100.0;
	}        
        
	//print method
        @Override
	public String toString() {
		return  "Customer ID: " + getID() +
			"\nCustomer Name: " + getName() +
			"\nMovie: " + getMovie() +
			"\nCustomer Seat: " + getSeatNo().toString() +
			"\nAdult Quantity: " + getAdultQty() +
			"\nChild Quantity: " + getChildQty() +
			"\nTotal Price: " + String.format("%.2f",calculateTotalAmount());
	}              
}
