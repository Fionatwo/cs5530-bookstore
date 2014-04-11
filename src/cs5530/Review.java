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
public class Review extends DatabaseModel{
	
	public Review(){
		table = "Reviews";
		columns.add("rid");
		columns.add("ISBN");
		columns.add("Login");
		columns.add("Score");
		columns.add("Description");
		columns.add("Date");
		primaryKeyColumns.add("rid");
	}
	
	public Review(String isbn, String login, String score, String desc, String date) {
		table = "Reviews";
		columns.add("rid");
		columns.add("ISBN");
		columns.add("Login");
		columns.add("Score");
		columns.add("Description");
		columns.add("Date");
		primaryKeyColumns.add("rid");
		
		colValPairs.put("ISBN", isbn);
		colValPairs.put("Login", login);
		colValPairs.put("Score", score);
		colValPairs.put("Description", desc);
		colValPairs.put("Date", date);
	}
}
