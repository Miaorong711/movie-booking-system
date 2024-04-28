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

public class Member extends Customer{
	
        //default constructor
	public Member() {
		super();
	}
		
	//constructor with parameter
	public Member(int id, String name, String movie, String[] seatNo, int adultQty, int childQty) {
		
		super(id, name, movie, seatNo, adultQty, childQty);
	}    
        
	//calculate total discount method
	public double calculateTotalDiscount() {
		double discount = 0.00;
		int totalQty = super.getAdultQty() + super.getChildQty();
		
		if(totalQty > 8) {
			discount = 0.15;
		}else if(totalQty > 4) {
			discount = 0.1;
		}else {
			discount = 0.05;
		}
		return Math.round((discount*100.0) * (super.calculateTotalAmount()*100.0))/10000.0;
	}
	
        //calculate total price method
	public double calculateTotalPrice() {
		double totalPrice = super.calculateTotalAmount()*100.0/100.0;
		return Math.round((totalPrice - calculateTotalDiscount())*100.0)/100.0;
	}
	
	//print method
	public String toString() {
		return super.toString()+
				"\nMember Discount: " + String.format("%.2f",calculateTotalDiscount()) +
				"\nTotal Price : " + String.format("%.2f",calculateTotalPrice());
	}                    
}
