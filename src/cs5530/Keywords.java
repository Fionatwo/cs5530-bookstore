package cs5530;

//import java.sql.*;
//import java.util.HashMap;
//import java.util.Map;

import cs5530.db.DatabaseModel;


/**
 * 
 * @author Alex Stout
 * @for University of Utah - CS5530 - Database Systems
 *
 * This is the Keywords Class
 */
public class Keywords extends DatabaseModel{
	
	public Keywords(){
		table = "Keywords";
		columns.add("kid");
		columns.add("Keyword");
		primaryKeyColumns.add("kid");
	}
	
	public Keywords(String keyword) {
		table = "Keywords";
		columns.add("kid");
		columns.add("Keyword");
		primaryKeyColumns.add("kid");
		
		colValPairs.put("Keyword", keyword);
	}

//	public int newKeyword(String keyword) {
//		PreparedStatement prep;
//
//		String sql = "INSERT INTO Keywords VALUES(?)";
//		System.out.println("executing " + sql);
//		try{
//	 		Connector con = new Connector();
//	 		prep = con.con.prepareStatement(sql);
//	 		prep.executeUpdate();
//	 		sql = "SELECT kid FROM ? WHERE keyword=?";
//	 		prep = con.con.prepareStatement(sql);
//	 		prep.setString(1, keyword);
//	 		ResultSet rs = prep.executeQuery();
//	 		while(rs.next())
//	 		{
//	 			this.kid = rs.getInt("kid");
//	 		}
//	 		rs.close();
//	 		prep.close();
//	 		return this.kid;
//	 	}
//	 	catch(Exception e)
//	 	{
//	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
//    		System.err.println(e.getMessage());
//    		return -1;
//	 	}
//
//	}
}
