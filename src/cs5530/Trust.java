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
public class Trust extends DatabaseModel{
	
	public Trust(){
		table = "Trusts";
		columns.add("Truster");
		columns.add("Trustee");
		columns.add("Assessment");
		columns.add("Date");
		primaryKeyColumns.add("Truster");
		primaryKeyColumns.add("Trustee");
	}
	
	public Trust(String truster, String trustee, String assessment, String date) {
		table = "Trusts";
		columns.add("Truster");
		columns.add("Trustee");
		columns.add("Assessment");
		columns.add("Date");
		primaryKeyColumns.add("Truster");
		primaryKeyColumns.add("Trustee");
		
		colValPairs.put("Truster", truster);
		colValPairs.put("Trustee", trustee);
		colValPairs.put("Assessment", assessment);
		colValPairs.put("Date", date);
	}
}
