package cs5530;

//import java.sql.*;
//import java.util.HashMap;
//import java.util.Map;



/**
 * 
 * @author Alex Stout
 * @for University of Utah - CS5530 - Database Systems
 *
 * This is the Keywords Class
 */
public class ShoppingCart extends DatabaseModel{
	
	public ShoppingCart(){
		table = "Shopping_Carts";
		columns.add("Login");
		columns.add("TotalPrice");
		primaryKeyColumns.add("Login");
	}
	
	public ShoppingCart(String login, String total) {
		table = "Shopping_Carts";
		columns.add("Login");
		columns.add("TotalPrice");
		primaryKeyColumns.add("Login");
		
		colValPairs.put("Login", login);
		colValPairs.put("TotalPrice", total);
	}
}
