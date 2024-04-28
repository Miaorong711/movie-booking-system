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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MovieManagementSystem {
	
        //arraylist
	private ArrayList<MovieSystem> entry;
	
        //file name
	private String fileName = "movieSystemData.ser";
		
	//default constructor
	public MovieManagementSystem() {
		entry = new ArrayList<MovieSystem>();  
        }
                
	//add method
	public void add(MovieSystem newMovie) {
		boolean found = false;
		for(int i = 0; i < entry.size(); i++) {
			MovieSystem movie = entry.get(i);
			
			if(movie.getID() == newMovie.getID()) {
				found = true;
				break;
			}
		}
		if(found == true) {
			//empty 
		}else{
			entry.add(newMovie);
		}
	}                
                
	//update method
	public void update(String theName,String[] theSeatNo) {
		int movie = findID(theName);
		String[][] seatNo = findSeatNo(theName);
		
		for (int i = 0; i < theSeatNo.length; i++) {
                    for (int j = 0; j <= 4; j++) {
                        for (int k = 0; k <= 4; k++) {
                            if (theSeatNo[i].equalsIgnoreCase(seatNo[j][k])) {
                                seatNo[j][k] = "XX";
                            }
                        }
                    }
                }
		// update seatNo
		entry.get(movie).setSeatNo(seatNo);
	}
 
	//remove method
	public void remove(String theName, String[] theSeatNo) {
		int movie = findID(theName);
		String[][] seatNo = findSeatNo(theName);
		
	char p;
        char q;
        int r = 0;
        int s = 0;
		
        for (int i = 0; i < theSeatNo.length; i++) {
             p = theSeatNo[i].charAt(0);
             
             if (p == 'A' || p == 'a') {
                 r = 0;
             } else if (p == 'B' || p == 'b') {
                 r = 1;
             } else if (p == 'C' || p == 'c') {
                 r = 2;
             } else if (p == 'D' || p == 'd') {
                 r = 3;
             } else if (p == 'E' || p == 'e') {
                 r = 4;
             }
             
             q = theSeatNo[i].charAt(1);
             s = Integer.parseInt(String.valueOf(q)) - 1;
             seatNo[r][s] = theSeatNo[i];
		 }
        
            entry.get(movie).setSeatNo(seatNo);
	}        

	//find method
	public int find(String theName, String[] theSeatNo) {
		String[][] seatNo;
		if(!(findSeatNo(theName) == null)) {
			seatNo = findSeatNo(theName);
		}else {
			return -1;
		}
		
	//check seat duplication
        for(int i = 0; i < theSeatNo.length; i++) {
        	for (int j = i+1; j < theSeatNo.length; j++) {
                    if (theSeatNo[i].equalsIgnoreCase(theSeatNo[j])) {
                        return -1;
                    }
            }
        }
  
        //check seat availability 
        int found = 0;
        for (int i = 0; i < theSeatNo.length; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 4; k++) {
                    if (theSeatNo[i].equalsIgnoreCase(seatNo[j][k])) {
                        found++;
                    }
                }
            }
        }
        return found;
	}        
        
	//find seat method
	public String[][] findSeatNo(String theName) {
		int movie = findID(theName);
		if(movie >= entry.size()) {
			return null;
		}else {
			// find seat
			return entry.get(movie).getSeatNo(); 
		}
	}        
  
	//find index method
	public int findID(String theName) {
		int i = 0;
		int temp = 0;
		for(i = 0; i < entry.size(); i++) {
			MovieSystem movie = entry.get(i);
			
			if(movie.getName().equalsIgnoreCase(theName)) {
				temp = i;
				break;
			}
		}
		return temp; // index number
	}        
       
	//print method
	public String print(String theName) {
		int movie = findID(theName);
		return entry.get(movie).toString();
	}
	
	//toString method
	public String toString(String theName) {
		int movie = findID(theName);
		return "Please Insert SeatNo into SeatNo text field with comma between seatNos," + 
                        "\nFor example: A1,A2,A3 or a1,a2,a3 for three seats.\n" + 
                        entry.get(movie).toString();
	}        
        
	//----------------------Serializable method---------------------------
		
            // write method
	    public void write() {
	    	FileOutputStream fileOut;
	    	
	    	try {
	    		fileOut = new FileOutputStream(fileName);
	    		ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    		
	    		out.writeObject(entry);
	    		out.close();
	    		fileOut.close();
	    		JOptionPane.showMessageDialog(null, "System Exit and File Updated!");
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }        
   
		// read method
		public String read() {
			String info = "";
	    	try {
	    		File f = new File(fileName);
	    		
	    		if(f.exists()) {
	    			FileInputStream fileIn = new FileInputStream(f);
	    			ObjectInputStream in = new ObjectInputStream(fileIn);
	    			entry = (ArrayList<MovieSystem>) in.readObject();
	    			in.close();
	    			fileIn.close();
	    			info = "--> Connect to movieSystemData.ser file.";
	    		}else {
	    			info = "--> Connect to movieSystemData.ser file for the first time.";
	    		}
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}  	
	    	return info;
	    }                 
}
