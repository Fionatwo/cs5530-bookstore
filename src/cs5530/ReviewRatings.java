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
public class ReviewRatings extends DatabaseModel{
	
	public ReviewRatings(){
		table = "Review_Ratings";
		columns.add("Login");
		columns.add("rid");
		columns.add("Opinion");
		primaryKeyColumns.add("Login");
		primaryKeyColumns.add("rid");
	}
	
	public ReviewRatings(String login, String rid, String opinion) {
		table = "Review_Ratings";
		columns.add("Login");
		columns.add("rid");
		columns.add("Opinion");
		primaryKeyColumns.add("Login");
		primaryKeyColumns.add("rid");
		colValPairs.put("Login", login);
		colValPairs.put("rid", rid);
		colValPairs.put("Opinion", opinion);
	}
}
