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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;

public class MovieTicketSystemFX extends Application{
    
    //database
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet result;
    Connection con;
    Customer c1;

    //instance of MovieManagementSystem class
    MovieManagementSystem movieArrayList = new MovieManagementSystem();     
    
    //-------------------UI control------------------------
    Button btnPurchase = new Button("Purchase"),
           btnSearch = new Button("Search"),
           btnDelete = new Button("Delete"),
           btnExit = new Button("Exit"),
           btnUpdate = new Button("Update");   
    
    TextArea taTable = new TextArea(),
             taTable2 = new TextArea(),
             taTable3 = new TextArea();
    
    TextField tfCustomerID = new TextField(),
              tfCustomerName = new TextField(),
              tfAdultQty = new TextField(),
              tfChildQty = new TextField(),
              tfSeatNo = new TextField(),
              tfTotalAmount = new TextField(),
              tfDiscount = new TextField(),
              tfDeleteCustomerID = new TextField(),
              tfSearchID = new TextField(),
              tfFileName = new TextField();    
    
    Label   lbTitle = new Label("Movie Ticket System"),
            lbID = new Label("Customer ID: "),
            lbName = new Label("Customer Name: "),
            lbCT = new Label("Customer Type: "),
            lbM = new Label("Select Movie: "),
            lbQ = new Label("Quantity:"),
            lbAdult = new Label("Adult:"), 
            lbChild = new Label("Child:"),
            lbSeatNo = new Label("Seat No.:"),
            lbDiscount = new Label("Discount:"),
            lbTotalAmount = new Label("Total Amount:"),
            
            lbPT = new Label("Purchase Ticket"),
            lbDS = new Label("Display Seat"),
            lbRT = new Label("Remove Ticket"),
            lbDPD = new Label("Display Purchased Details"),
            lbExit = new Label("Do You Want to Exit the System?");    
    
    RadioButton tfPCustType = new RadioButton("Member"),
                tfRCustType = new RadioButton("Non-Member");
    
    TabPane tabPane = new TabPane();    
    
    private TableView tableView;
    
    ScrollPane 	sp = new ScrollPane(taTable),
             	sp2 = new ScrollPane(taTable2),
             	sp3 = new ScrollPane(taTable3),
    		spTV;    
    
    ComboBox comM = new ComboBox();
    ComboBox<String> tfMovie = new ComboBox<String>(),
                     tfSearchMovie = new ComboBox<String>();
    
    GridPane gp3 = new GridPane();
    
    VBox vb3 = new VBox(10);    
   
    BorderPane bp3;
    
    HBox add, delete, search, exit;    
    
    //constant variable
    static final int seatNumber = 25;
    static final int numID = 100;
    
    //login
    GridPane grid = new GridPane();
    TextField userTextField;
    PasswordField pwBox;
    Boolean verified = false;    
    
    //scene
    Scene loginScene = new Scene(grid);
    Scene scene = new Scene(tabPane);
    
    //stage
    Stage stage = new Stage();    
    
    @Override
    public void start(Stage primaryStage) throws Exception{

        //-----Start------
	initializeDB();
        login(); 
        
        //--------------------Insert new movie--------------------------
        movieArrayList.add(new MovieSystem(1, "Dragon Ball Super: Super Hero"));
        movieArrayList.add(new MovieSystem(2, "Crayon Shinchan The Movie: The Tornado Legend Of Ninja Mononoke"));
        movieArrayList.add(new MovieSystem(3, "Emergency Declaration"));  
        movieArrayList.add(new MovieSystem(4, "Moon Man"));    
        
        //create menu bar
        MenuBar menuBar = new MenuBar();

        //Create menus
        Menu operationMenu = new Menu("Operation");
        Menu helpMenu = new Menu("Help");               

        MenuItem addItem = new MenuItem("Purchase Ticket");
        MenuItem deleteItem = new MenuItem("Display/Remove Ticket");
        MenuItem searchItem = new MenuItem("Search Ticket");
        MenuItem updateItem = new MenuItem("Update Ticket");
         
        MenuItem exitItem = new MenuItem("Exit");        
        
        //Add menuItems to the operationMenus
        operationMenu.getItems().addAll(addItem, deleteItem, searchItem, updateItem);

        //Add menuItems to the helpMenus
        helpMenu.getItems().addAll(exitItem);        
        
        //Add Menus to the MenuBar
        menuBar.getMenus().addAll(operationMenu, helpMenu);            
        
        BorderPane p = new BorderPane();
        GridPane x = new GridPane();
        
        //1st row
        x.add(lbID,0,0);
        x.add(tfCustomerID,1,0);        
        
        //2nd row
        x.add(lbName,0,1);
        x.add(tfCustomerName,1,1);       
        
        //3rd row
        x.add(lbCT,0,2);
        x.add(tfPCustType,1,2);
        x.add(tfRCustType,2,2);
        
        //group radio button
        ToggleGroup group = new ToggleGroup();
        tfPCustType.setToggleGroup(group);
        tfRCustType.setToggleGroup(group);
        tfRCustType.setSelected(true);     
        
        //4th row
        x.add(lbM,0,3);  //select movie
        x.add(comM,1,3); 
        tfMovie.getItems().add("Dragon Ball Super: Super Hero");
        tfMovie.getItems().add("Crayon Shinchan The Movie: The Tornado Legend Of Ninja Mononoke");
        tfMovie.getItems().add("Emergency Declaration");
        tfMovie.getItems().add("Moon Man");
        comM.getItems().addAll("Dragon Ball Super: Super Hero",
                               "Crayon Shinchan The Movie: The Tornado Legend Of Ninja Mononoke",
                               "Emergency Declaration",
                               "Moon Man");
        comM.setValue("- Select Type -");  
       
        //5th row
        x.add(lbQ,0,4);
        x.add(lbAdult,1,4);  
        x.add(tfAdultQty,2,4);  
        x.add(lbChild,3,4);   
        x.add(tfChildQty,4,4); 
        
        //6th row
        x.add(lbSeatNo,0,5);
        x.add(tfSeatNo,1,5);       
        
        //7th row
        x.add(lbDiscount,0,6);
        x.add(tfDiscount,1,6);        
        
        //8th row
        x.add(lbTotalAmount,0,7);
        x.add(tfTotalAmount,1,7);        
        
        x.setHgap(5);
    	x.setVgap(5);
        x.setAlignment(Pos.CENTER);
        p.setTop(x);        
        
        HBox z = new HBox();
        z.getChildren().addAll(btnPurchase, btnUpdate);
        z.setSpacing(10);
        z.setAlignment(Pos.CENTER);

       VBox vBox = new VBox(10);
       vBox.getChildren().addAll(p, x, sp, z);  
              
       tfDiscount.setEditable(false);  
       tfTotalAmount.setEditable(false);         
        
       //putting icon into button
       //add button icon
       Image add = new Image("image/add.png");
       ImageView viewAdd = new ImageView(add);
       viewAdd.setFitHeight(30);
       viewAdd.setFitWidth(30);
       viewAdd.setPreserveRatio(true);
       addItem.setGraphic(viewAdd);
 
       //add button icon
       Image add1 = new Image("image/p1.png");
       ImageView viewAdd1 = new ImageView(add1);
       viewAdd1.setFitHeight(30);
       viewAdd1.setFitWidth(30);
       viewAdd1.setPreserveRatio(true);
       btnPurchase.setContentDisplay(ContentDisplay.RIGHT);
       btnPurchase.setGraphic(viewAdd1);       
       
        //delete button icon
        Image clear = new Image("image/clear.png");
        ImageView viewClear = new ImageView(clear);
        viewClear.setFitHeight(30);
        viewClear.setFitWidth(30);
        viewClear.setPreserveRatio(true);
        deleteItem.setGraphic(viewClear);
        btnUpdate.setContentDisplay(ContentDisplay.RIGHT);
        
        //search button icon
        Image search = new Image("image/search.png");
        ImageView viewSearch = new ImageView(search);
        viewSearch.setFitHeight(30);
        viewSearch.setFitWidth(30);
        viewSearch.setPreserveRatio(true);
        searchItem.setGraphic(viewSearch);
        btnSearch.setContentDisplay(ContentDisplay.RIGHT);        

        //update button icon
        Image update = new Image("image/u1.png");
        ImageView viewUpdate = new ImageView(update);
        viewUpdate.setFitHeight(30);
        viewUpdate.setFitWidth(30);
        viewUpdate.setPreserveRatio(true);
        updateItem.setGraphic(viewUpdate);
        
        //update button icon
        Image update1 = new Image("image/save.png");
        ImageView viewUpdate1 = new ImageView(update1);
        viewUpdate1.setFitHeight(30);
        viewUpdate1.setFitWidth(30);
        viewUpdate1.setPreserveRatio(true);
        btnUpdate.setGraphic(viewUpdate1);
        btnUpdate.setContentDisplay(ContentDisplay.RIGHT);        
        
       //exit button icon
        Image exit = new Image("image/exit.png");
        ImageView viewExit = new ImageView(exit);
        viewExit.setFitHeight(30);
        viewExit.setFitWidth(30);
        viewExit.setPreserveRatio(true);
        exitItem.setGraphic(viewExit);
        btnExit.setContentDisplay(ContentDisplay.RIGHT);              
       
        //styling nodes
        lbID.setStyle("-fx-font: normal bold 19px 'serif';");
        lbName.setStyle("-fx-font: normal bold 19px 'serif';");
        lbCT.setStyle("-fx-font: normal bold 19px 'serif';");
        lbAdult.setStyle("-fx-font: normal bold 19px 'serif';");
        lbChild.setStyle("-fx-font: normal bold 19px 'serif';");
        lbM.setStyle("-fx-font: normal bold 19px 'serif';");
        lbQ.setStyle("-fx-font: normal bold 19px 'serif';");
        lbSeatNo.setStyle("-fx-font: normal bold 19px 'serif';");
        lbDiscount.setStyle("-fx-font: normal bold 19px 'serif';");
        lbTotalAmount.setStyle("-fx-font: normal bold 19px 'serif';");
        
        btnPurchase.setStyle("-fx-background-color:#515CF5; -fx-font: normal bold 20px 'serif'; -fx-text-fill: white;");
        btnDelete.setStyle("-fx-background-color:#515CF5; -fx-font: normal bold 20px 'serif'; -fx-text-fill: white;");
        btnSearch.setStyle("-fx-background-color:#515CF5; -fx-font: normal bold 20px 'serif'; -fx-text-fill: white;");
        btnUpdate.setStyle("-fx-background-color:#515CF5; -fx-font: normal bold 20px 'serif'; -fx-text-fill: white;");
        btnExit.setStyle("-fx-background-color:#515CF5; -fx-font: normal bold 20px 'serif'; -fx-text-fill: white;");

        menuBar.setStyle("-fx-background-color: #DBD8D7;");
        taTable.setStyle(" -fx-background-color: steelblue; -fx-font-size: 16");       
        
        //process events
        addItem.setOnAction(e->add());
        deleteItem.setOnAction(e->delete());
        searchItem.setOnAction(e->search());
        updateItem.setOnAction(e->update());   
        
        exitItem.setOnAction(e->exit());       
       
        btnPurchase.setOnAction(e->add());
        btnDelete.setOnAction(e -> delete());
        btnUpdate.setOnAction(e -> update());
        btnSearch.setOnAction(e -> search());
        
        btnExit.setOnAction(e->exit());
        
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(vBox);        
        
        //------------------------------------------Key Press event----------------------------------------
        
        //Purchase Ticket
        tfCustomerID.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT || 
                e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.TAB || e.getCode().isDigitKey()) {
            } else {
                Error("Invalid data", "Enter digits only!");
            }
        });
        
        tfCustomerName.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT || 
            	e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.SHIFT || 
            	e.getCode() == KeyCode.SPACE || e.getCode().isLetterKey()) {
            } else {
                Error("Error in Customer Name", "Name can only be Letter.");
            }
        });
        
        tfAdultQty.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT || 
                e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.TAB || e.getCode().isDigitKey()) {
            } else {
                Error("Invalid data", "Enter digits only!");
            }
        });
        
        tfChildQty.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT || 
            	e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.TAB || e.getCode().isDigitKey()) {
            } else {
                Error("Invalid data", "Enter digits only!");
            }
        });
        
        tfSeatNo.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT || 
            	e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.SHIFT || 
            	e.getCode() == KeyCode.COMMA || e.getCode().isLetterKey() || e.getCode().isDigitKey()) {
            } else {
                Error("Invalid data", "Please Insert SeatNo into SeatNo text field with comma between seatNos.");
            }
        });
        
        //Display/Remove Ticket
        tfDeleteCustomerID.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT || 
                e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.TAB || e.getCode().isDigitKey()) {
            } else {
                Error("Invalid data", "Enter digits only!");
            }
        });
        
        //Search Ticket
        tfSearchID.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT || 
                e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.SHIFT || 
                e.getCode().isDigitKey()) {
            } else {
                Error("Invalid data", "Enter digits only!");
            }
        });        
        
        //----------------Scene Setting-------------------
        
        //style
        scene.getStylesheets().add("style.css");
        loginScene.getStylesheets().add("style.css");
        //style();
        Scene scene = new Scene(root,1200,600);
        stage.setTitle("Movie Ticket System");
        stage.setScene(scene);
        stage.show();
    }        
    
    //----------------------------------------Task Method----------------------------------------
    
        //initialize database method
        public void initializeDB() {
       
        //initialize database
        String url = "jdbc:mysql://localhost:3306/movieticketsystemdb"; // change the database's name
        String username = "root";
        String password = "Gmr2002711-";

        String info = "";
        try {
            con = DriverManager.getConnection(url, username, password);
            if (con != null) {
                info = "--> Connected to database.\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        info += movieArrayList.read();
        taTable.setText(info);
    }        
    
        //login Method
        public void login() {
    	
        //--------------GridPane-------------------------
    	grid.setAlignment(Pos.CENTER);
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));

    	Text scenetitle = new Text("Welcome to Movie Ticket System 0v0");
    	scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    	grid.add(scenetitle, 0, 0, 2, 1);

    	//1st row
    	Label userName = new Label("Username:");
    	grid.add(userName, 0, 1);

    	//2nd row
    	userTextField = new TextField();
    	grid.add(userTextField, 1, 1);

    	//3rd row
    	Label pw = new Label("Password:");
    	grid.add(pw, 0, 2);
    	pwBox = new PasswordField();
    	pwBox.setStyle("-fx-background-color: rgba(53,89,319,0.2);");
    	grid.add(pwBox, 1, 2);
    	
    	//4th row
    	Button btn = new Button("Login");
    	HBox hbBtn = new HBox(10);
    	hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    	hbBtn.getChildren().add(btn);
    	grid.add(hbBtn, 1, 4);
    	
    	//5th row
    	final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

      //----------Event Handling--------------
        btn.setOnAction(e -> {
        	String name = userTextField.getText();
        	String password = pwBox.getText();
        	Boolean gotIt = false;
        	char ch = '"';
                try {
                    String searchSQL = "SELECT * FROM admin WHERE admin_name = ? AND admin_password = ?";
                    preparedStatement = con.prepareStatement(searchSQL);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, password);
                    result = preparedStatement.executeQuery();
                    gotIt = result.next();
                    
                } catch (SQLException E) {
                    E.printStackTrace();
                }
                
                if(gotIt == true) {
                	stage.setScene(scene);
                }else {
                	Error("Error in Login","Incorrect name or password.");
                	Clear(8);
                	login();
                }
        });
    }
  
        //change root method 
 	public void changeRoot(int option) {
 		switch(option){
 			case 1:
 				bp3.setBottom(spTV);
 				break;
 			case 2:
 				bp3.setBottom(sp3);
 				break;
 			default:
 				break;
 		}
 	}        
    
        //exit method
        public void exit() {
            movieArrayList.write();
            System.exit(0);
            try {
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }    
    
    //-----File I/O Function-------
   
    //load method
    public void load() {
    	if(tfFileName.getLength()==0){
    		Error("Error in Load","Please insert file Name");
    		return;
    	}
    	String fileName = tfFileName.getText();
		File file = new File(fileName+ ".txt");
        
        try{
            Scanner inputFile = new Scanner(file);
            while(inputFile.hasNext()){
                String line = inputFile.nextLine();
                taTable3.appendText(line + "\n");
            }
            Clear(9);
        }catch(IOException io){
            Error("Error in Load","File input error.");
            Clear(9);
        }
    }

        //save method
	public void save() {
		if(tfFileName.getLength()==0){
    		Error("Error in Load","Please insert file Name");
    		return;
    	}
		String fileName = tfFileName.getText();
		File file = new File(fileName+".txt");
		
		try {
			PrintWriter output = new PrintWriter(new FileWriter (file));
			Scanner s = new Scanner(taTable3.getText());
			
			while(s.hasNext()) {
				String out = s.nextLine(); // read from textarea
				output.println(out); // write into text file
			}
			
			output.close();
			s.close();
			JOptionPane.showMessageDialog(null, "Record Saved!");
			Clear(9);
			taTable3.setText(null);
			
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null,"I/O error.");
			Clear(9);
		}
	}
        
	//-------Database SQL statement for customer's detail-----------
	
        //add method
        public void add() {
        try {
            int customerId = Integer.parseInt(tfCustomerID.getText());
            String customerName = tfCustomerName.getText();
            int adultQty = Integer.parseInt(tfAdultQty.getText());
            int childQty = Integer.parseInt(tfChildQty.getText());
            String customerMovie = tfMovie.getValue();
            String[] customerSeatNo = tfSeatNo.getText().toUpperCase().split(",", 0);
            double customerPrice = 0.00;
            double customerDiscount = 0.00;
            double customerTotalAmount = 0.00;

            if (searchID(customerId)) {
                Error("Error in Customer ID", "Duplicate ID found.");
                Clear(1);
                return;
            }

            if (purchaseTicketException(customerId, customerName, adultQty, childQty, customerMovie, customerSeatNo) == false) {
                return;
            }

            if (tfPCustType.isSelected()) {
                c1 = new Member(customerId, customerName, customerMovie, customerSeatNo, adultQty, childQty);
                customerPrice = c1.calculateTotalAmount();
                customerDiscount = ((Member) c1).calculateTotalDiscount();
                customerTotalAmount = ((Member) c1).calculateTotalPrice();
            } else if (tfRCustType.isSelected()) {
                c1 = new NonMember(customerId, customerName, customerMovie, customerSeatNo, adultQty, childQty);
                customerPrice = c1.calculateTotalAmount();
                customerDiscount = ((NonMember) c1).calculateTotalDiscount();
                customerTotalAmount = ((NonMember) c1).calculateTotalPrice();
            }

            String sql = "INSERT INTO customer (id, name, movie, seatno, adultqty, childqty, price, discount, totalprice) " + 
                         "VALUES (?,?,?,?,?,?,?,?,?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setString(2, customerName);
            preparedStatement.setString(3, customerMovie);
            preparedStatement.setString(4, tfSeatNo.getText().toUpperCase());
            preparedStatement.setInt(5, adultQty);
            preparedStatement.setInt(6, childQty);
            preparedStatement.setDouble(7, customerPrice);
            preparedStatement.setDouble(8, customerDiscount);
            preparedStatement.setDouble(9, customerTotalAmount);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                Info("Added Successfully");
            } else {
                Info("Added Failed");
                return;
            }

            tfDiscount.setText("RM" + customerDiscount);
            tfTotalAmount.setText("RM" + customerTotalAmount);
            movieArrayList.update(customerMovie, customerSeatNo);
            Clear(7);

        } catch (NumberFormatException ex) {
            Error("Invalid Data", "Please enter integer number in Customer ID, Quantity of Adult, and Quantity of Child!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }        
        
        //update method
        public void update() {
        try {
            int customerId = Integer.parseInt(tfCustomerID.getText());
            String customerName = tfCustomerName.getText();
            int adultQty = Integer.parseInt(tfAdultQty.getText());
            int childQty = Integer.parseInt(tfChildQty.getText());
            String customerMovie = tfMovie.getValue();
            String[] customerSeat = tfSeatNo.getText().toUpperCase().split(",", adultQty + childQty);
            double customerPrice = 0.00;
            double customerDiscount = 0.00;
            double customerTotalAmount = 0.00;

            String oldMovie = "";
            String oldCustomerSeat = "";
            int oldAdultQty = 0;
            int oldChildQty = 0;

            if (searchID(customerId)) {
                // empty
            } else {
                Error("Error in Customer ID", "ID not found.");
                Clear(1);
                return;
            }

            String selectSql = "SELECT movie, seatid, adultqty, childqty FROM customer WHERE id = " + customerId;
            statement = con.createStatement();
            result = statement.executeQuery(selectSql);

            while (result.next()) {
                oldMovie = result.getString("movie");
                oldCustomerSeat = result.getString("seatno");
                oldAdultQty = result.getInt("adultqty");
                oldChildQty = result.getInt("childqty");
            }
            String oldCustSeatNo[] = oldCustomerSeat.split(",", oldAdultQty + oldChildQty);
            movieArrayList.remove(oldMovie, oldCustSeatNo);

            int found = movieArrayList.find(customerMovie, customerSeat);
            if (purchaseTicketException(customerId, customerName, adultQty, childQty, customerMovie, customerSeat) == false) {
                return;
            }

            if (found == customerSeat.length) {

                if (tfPCustType.isSelected()) {
                    c1 = new Member(customerId, customerName, customerMovie, customerSeat, adultQty, childQty);
                    customerPrice = c1.calculateTotalAmount();
                    customerDiscount = ((Member) c1).calculateTotalDiscount();
                    customerTotalAmount = ((Member) c1).calculateTotalPrice();
                } else if (tfRCustType.isSelected()) {
                    c1 = new NonMember(customerId, customerName, customerMovie, customerSeat, adultQty, childQty);
                    customerPrice = c1.calculateTotalAmount();
                    customerDiscount = ((NonMember) c1).calculateTotalDiscount();
                    customerTotalAmount = ((NonMember) c1).calculateTotalPrice();
                }

                String sql = "UPDATE customer SET name = ?, movie = ?, seatno = ?, adultqty = ?, childqty = ?, price = ?, discount = ?, totalamount = ? WHERE id = ?";
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, customerName);
                preparedStatement.setString(2, customerMovie);
                preparedStatement.setString(3, tfSeatNo.getText().toUpperCase());
                preparedStatement.setInt(4, adultQty);
                preparedStatement.setInt(5, childQty);
                preparedStatement.setDouble(6, customerPrice);
                preparedStatement.setDouble(7, customerDiscount);
                preparedStatement.setDouble(8, customerTotalAmount);
                preparedStatement.setInt(9, customerId);

                int rows = preparedStatement.executeUpdate();
                if (rows > 0) {
                    Info("Updated Successfully");

                } else {
                    Info("Update failed");
                    return;
                }

                Clear(7);
                movieArrayList.update(customerMovie, customerSeat);
                tfDiscount.setText("RM" + customerDiscount);
                tfTotalAmount.setText("RM" + customerTotalAmount);

            } else if (!(found == customerSeat.length)) {
                Error("Error in Seat ID", "The selected seat(s) is or are not found");
                tfSeatNo.setText("");
            }

        } catch (NumberFormatException e) {
            Error("Invalid Data", "Please enter integer number in Customer ID, Quantity of Adult , and Quantity of Child!");
            Clear(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        //search method
        public void search() {
            changeRoot(2);
            String info = "";

        try {
            int customerId = Integer.parseInt(tfSearchID.getText());
            
            // Customer ID not found
            if (customerId < 100) { //at least three integer.
                Error("Invalid data in Customer ID", "Enter at least three integers!");
                Clear(5);
                return;
            }
            if (searchID(customerId)) {
                // empty
            } else {
                Error("Error in Customer ID", "ID not found.");
                Clear(5);
                return;
            }

            String sql = "SELECT * FROM customer WHERE id = " + customerId;
            statement = con.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                String id = result.getString("id");
                String customerName = result.getString("name");
                String customerMovie = result.getString("movie");
                String seatNo = result.getString("seatid");
                int adultQty = result.getInt("adultqty");
                int childQty = result.getInt("childqty");
                double price = result.getDouble("price");
                double discount = result.getDouble("discount");
                double totalAmount = result.getDouble("totalamount");
                info = " ID: " + id
                        + "\n Name: " + customerName
                        + "\n Movie: " + customerMovie
                        + "\n Seat ID: " + seatNo
                        + "\n Adult Quantity: " + adultQty
                        + "\n Kid Quantity: " + childQty
                        + "\n Price: " + price
                        + "\n Discount: " + discount
                        + "\n Total Price: " + totalAmount + "\n";
            }
            taTable3.setText(info);
            Clear(5);
        } catch (NumberFormatException e) {
            Error("Error in Customer ID", "Please enter integer.");
            Clear(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        //print method
        public void print() {
        String print = "";
        String info = "";
        
        changeRoot(1);
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        
        try {
        	tableView.getColumns().clear();
        	tableView.getItems().clear();
        	String sql = "SELECT * FROM customer ORDER BY id";
            
            //resultSet
            statement = con.createStatement();
            
            //sql receive form sort method
            result = statement.executeQuery(sql);
 
            //table column added dynamically
            for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
                //Non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(result.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                }); 
                tableView.getColumns().addAll(col);
            }
 
            //data added to obervableList
            while (result.next()) {
                //Iterate row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    //Iterate column
                    row.add(result.getString(i));
                }
                data.add(row);
            }
 
            //added to tableview
            tableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

        //delete method
        public void delete() {
        try {
            int customerId = Integer.parseInt(tfDeleteCustomerID.getText());
            if (customerId < 100) {
                Error("Invalid data in Customer ID", "Enter at least three integers!");
                Clear(6);
                return;
            }

            // Customer ID not found
            if (searchID(customerId)) {
                // empty
            } else {
                Error("Error in Customer ID", "ID not found.");
                Clear(6);
                return;
            }

            String oldMovie = "";
            String oldCustomerSeat = "";
            int oldAdultQty = 0;
            int oldChildQty = 0;

            String selectSql = "SELECT movie, seatno, adultqty, childqty FROM customer WHERE id = " + customerId;
            statement = con.createStatement();
            result = statement.executeQuery(selectSql);

            while (result.next()) {
                oldMovie = result.getString("movie");
                oldCustomerSeat = result.getString("seatno");
                oldAdultQty = result.getInt("adultqty");
                oldChildQty = result.getInt("kidqty");
            }
            String oldCustSeatNo[] = oldCustomerSeat.split(",", oldAdultQty + oldChildQty);
            movieArrayList.remove(oldMovie, oldCustSeatNo);

            String sql = "DELETE FROM customer WHERE id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                Info("Remove Successfully");
                Clear(6);
            }
        } catch (NumberFormatException e) {
            Error("Error in Customer ID", "Please enter integer.");
            Clear(6);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        //search id method
        public boolean searchID(int id) {
        boolean gotIt = false;

        try {
            String searchSQL = "SELECT * FROM customer WHERE id = ?";
            preparedStatement = con.prepareStatement(searchSQL);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();
            gotIt = result.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gotIt;
    }    
 
        //display method
        public void display(TextArea ta, int option) {
    	String customerMovie;
    	switch(option) {
    		case 1:
    			customerMovie = tfSearchMovie.getValue();
    	        String output = movieArrayList.print(customerMovie);
    	        ta.setText(output);
    	        break;
    		case 2:
    			customerMovie = tfMovie.getValue();
    	        ta.setText(movieArrayList.toString(customerMovie));
    	        break;
    	}
    }

        //exception for buy ticket tab method
        public boolean purchaseTicketException(int customerId, String customerName, int adultQty, int childQty, String customerMovie, String[] customerSeat) {
        if (customerId < numID) {
            Error("Error in Customer ID", "Enter at least three integers!");
            Clear(1);
            return false;
        }
        if (customerName.length() < 1) {
            Error("Error in Customer Name", "Please enter your name!");
            Clear(2);
            return false;
        }
        if (!(tfPCustType.isSelected() || tfRCustType.isSelected())) {
            Error("Error in Customer Type", "Please select one customer type.");
            return false;
        }
        if (adultQty + childQty > seatNumber) {
            Error("Error in Adult and Child Quantity", "Please select adult and child quantity not more than number of seat available (maximum 25 seats).");
            Clear(3);
            return false;
        }
        if (customerMovie != null) {
            // empty
        } else {
            Error("Error in Movie", "Please select one Movie.");
            return false;
        }
        if (customerSeat.length != (adultQty + childQty)) {
            Error("Error in Seat No", "The number of selected seat is unmatch with number of adult plus child.");
            Clear(4);
            return false;
        }
        int found = movieArrayList.find(customerMovie, customerSeat);
        if (!(found == customerSeat.length)) {
            Error("Error in Seat No", "The selected seat(s) is or are not found");
            Clear(4);
            return false;
        }
        if (found == -1) {
            Error("Error in Seat No", "Duplicate the seat No.");
            Clear(4);
            return false;
        }
        return true;
    }        
    
    //error method
    public void Error(String e1, String e2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(e1);
        alert.setHeaderText(null);
        alert.setContentText(e2);
        alert.show();
    }

    //inform method
    public void Info(String e) {
        JOptionPane.showMessageDialog(null, e);
    }

    // clear method
    public void Clear(int option) {
        switch (option) {
            case 1:
                tfCustomerID.clear();
                tfCustomerID.requestFocus();
                break;
            case 2:
                tfCustomerName.clear();
                tfCustomerName.requestFocus();
                break;
            case 3:
                tfAdultQty.clear();
                tfAdultQty.requestFocus();
                tfChildQty.clear();
                break;
            case 4:
                tfSeatNo.clear();
                tfSeatNo.requestFocus();
                break;
            case 5:
                tfSearchID.clear();
                tfSearchID.requestFocus();
                break;
            case 6:
                tfDeleteCustomerID.clear();
                tfDeleteCustomerID.requestFocus();
                break;
            case 7:
                tfCustomerID.clear();
                tfCustomerName.clear();
                tfAdultQty.clear();
                tfChildQty.clear();
                tfSeatNo.clear();
                tfMovie.setValue("");
                //group.selectToggle(null);
                taTable.setText("");
                break;
            case 8:
            	userTextField.setText("");
            	pwBox.setText("");
            	break;
            case 9:
            	tfFileName.setText("");
            	break;
            default:
                break;
        }
    }    
  
    //spinner coverter
    class MyConverter extends StringConverter<Integer> {

        @Override
        public String toString(Integer object) {
            return object + "";
        }

        @Override
        public Integer fromString(String string) {
            return Integer.parseInt(string);
        }
    }     
    public static void main(String[] args) {
        launch(args);
    }        
}


