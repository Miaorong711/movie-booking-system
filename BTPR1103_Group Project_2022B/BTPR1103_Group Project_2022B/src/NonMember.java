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

public class NonMember extends Customer{
  
	//default constructor
	public NonMember() {
		super();
	}
			
	//constructor with parameter
	public NonMember(int id, String name, String movie, String[] seatNo, int adultQty, int childQty) {
		
		super(id, name, movie, seatNo, adultQty, childQty);
	}    
    
	//calculate total discount method
	public double calculateTotalDiscount() {
		double discount = 0.00;
		int totalQty = super.getAdultQty() + super.getChildQty();
		
		if(totalQty > 8) {
			discount = 0.1;
		}else if(totalQty > 4) {
			discount = 0.05;
		}else {
			discount = 0.00;
		}
		
		return Math.round((discount*100.0) * (super.calculateTotalAmount()*100.0))/10000.0;
	}
	
        //calculate total price method
	public double calculateTotalPrice() {
		return Math.round(( super.calculateTotalAmount() - calculateTotalDiscount())*100.0)/100.0;
	}
	
	//print price method
	public String printPrice() {
		return String.format("%.2f", calculateTotalDiscount()) + 
                        "," + 
                       String.format("%.2f", calculateTotalPrice());
	}
		
	//print method
        @Override
	public String toString() {
		return super.toString()+
				"\nNon Member Discount: " + String.format("%.2f", calculateTotalDiscount()) +
				"\nTotal Price: " + String.format("%.2f", calculateTotalPrice());
	}    
}
