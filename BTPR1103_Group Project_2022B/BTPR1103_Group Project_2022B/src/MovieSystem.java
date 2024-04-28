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

import java.io.Serializable;

public class MovieSystem implements Serializable{
	
        //instance variable
	private int id;
	private String name;
	private String[][] seatNo = {
                                    {"A1", "A2", "A3", "A4", "A5"}, 
                                    {"B1", "B2", "B3", "B4", "B5"}, 
                                    {"C1", "C2", "C3", "C4", "C5"}, 
                                    {"D1", "D2", "D3", "D4", "D5"}, 
                                    {"E1", "E2", "E3", "E4", "E5"}
                                    };  
        
	//default constructor
	public MovieSystem() {
		id = 0;
		name = null;
	}
	
	//constructor with parameter
	public MovieSystem(int id, String name) {
		this.id = id;
		this.name = name;
	}        
        
	//get method
	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String[][] getSeatNo(){
		return seatNo;
	}        
        
	//set method
	public void setID(int theID) {
		id = theID;
	}
	public void setName(String theName) {
		name = theName;
	}
	public void setSeatNo(String[][] theSeatNo) {
		seatNo = theSeatNo;
	} 
        
	//print method
	public String toString() {
		String[][] theSeatNo = getSeatNo();
		
		String display = "\n\t\t|\t\t\tMovie Screen\t\t\t\t|\n\n";
		
		for (int i = 0; i < 5; i++) {
                    display += "\t\t";
                for (int j = 0; j < 5; j++) {
                    display += " | " + theSeatNo[i][j] + " | \t";
            }
                display += "\n";
        }
		return display;
	}           
}
