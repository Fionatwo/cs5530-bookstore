package cs5530;

import java.sql.*;

public class Connector {
	public Connection con;
	public Statement stmt;
	public Connector() throws Exception {
		try{
		 	String userName = "cs5530u29";
	   		String password = "lvedurgt";
	        	String url = "jdbc:mysql://georgia.eng.utah.edu/cs5530db29";
//			String userName = "root";
//			String password = "root";
//			String url = "jdbc:mysql://localhost:8889/cs5530db29";
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        		con = DriverManager.getConnection (url, userName, password);

			//DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
        	//stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt = con.createStatement();
//			prep = con.prepareStatement(sql);
			//stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch(Exception e) {
			System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
            		System.err.println(e.getMessage());
			throw(e);
		}
	}
	
	public void closeConnection() throws Exception{
		con.close();
	}
	
}
