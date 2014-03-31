package cs5530;

import java.sql.*;
import java.util.ArrayList;

import cs5530.db.*;

/**
 * Represents an Author object.  Authors are separate entities from the Books table.
 * 
 * @author Alex Stout - u0583813
 *
 */
public class Author extends DatabaseModel{
	
	public String first;
	public String last;
	private int aid;
	
	/**
	 * empty constructor
	 */
	public Author(){
		table = "Authors";
		columns.add("FirstName");
		columns.add("LastName");
	}
	
	/**
	 * The constructor to set the first and last name
	 * of the Author.
	 * 
	 * @param first
	 * @param last
	 */
	public Author(String first, String last) {
		this.first = first;
		this.last = last;
		table = "Authors";
		columns.add("FirstName");
		columns.add("LastName");
		colValPairs.put("FirstName", first);
		colValPairs.put("LastName", last);
		primaryKeyColumns.add("aid");
	}
	
	/**
	 * gets the aid property. The aid is the Author ID from the
	 * Authors table of the database.
	 * 
	 * @return int aid
	 */
	public int getAid()
	{
		return this.aid;
	}
	
	/**
	 * Inserts this author to the database in the Authors table.
	 * Does so by getting the object property of first and last
	 * for the FirstName and LastName fields.
	 * 
	 * It then updates the aid property by getting the aid after
	 * the insert.
	 * 
	 * @return true on success, false otherwise
	 */
//	public boolean insertAuthor() {
//		
//		//null check
//		if(this.first == null || this.last == null)
//		{
//			return false;
//		}
//		
//		//empty check
//		if(this.first == "" || this.last == "")
//		{
//			return false;
//		}
//		
//		//prepare the sql statement
//		String sql = "insert into " + table + " (FirstName, LastName) VALUES (?,?)";
//		
//		//notify of the statement
//		System.out.println("executing " + sql);
//		try{
//			//prepare connection
//	 		Connector con = new Connector();
//	 		PreparedStatement prep = con.con.prepareStatement(sql);
//	 		
//	 		//bind the variables
////	 		prep.setString(1, this.first);
////	 		prep.setString(2, this.last);
////	 		prep.executeUpdate();
//	 		ArrayList<String> cols = new ArrayList<String>();
//	 		cols.add("FirstName");
//	 		cols.add("LastName");
//	 		ArrayList<String> vals = new ArrayList<String>();
//	 		vals.add(this.first);
//	 		vals.add(this.last);
//	 		db.Insert(table, cols, vals);
//	 		
//	 		sql = "select aid from " + table + " where FirstName=? and LastName=?";
//	 		prep = con.con.prepareStatement(sql);
//	 		prep.setString(1, this.first);
//	 		prep.setString(2, this.last);
//	 		ResultSet rs = prep.executeQuery();
//	 		
//	 		while(rs.next())
//	 		{
//	 			this.aid = rs.getInt("aid");
//	 		}
//	 		
//	 		//close connection
//	 		prep.close();
//	 		con.closeConnection();
//	 		
//	 		//return true on success
//	 		return true;
//	 	}
//	 	catch(Exception e)
//	 	{
//	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
//    		System.err.println(e.getMessage());
//    		return false;
//	 	}
//	}

	/**
	 * Enters the relationship of the author to the book to the Authored_By table
	 * in the database.
	 * @param isbn
	 * @return true on success, false otherwise
	 */
	public boolean insertAuthorBookRelation(String isbn) {
		//null check
		if(this.aid < 0)
		{
			return false;
		}
		
		if(isbn == null || isbn.isEmpty())
		{
			return false;
		}
		
		if(isbn.length() != 9 || isbn.length() != 10 || isbn.length() != 13)
		{
			System.out.println("the given ISBN length isn't correct. Must be 9,10, or 13 digits.");
			return false;
		}
		
		//prepare the sql statement
		String _table = "Authored_By";
		String sql = "insert into " + _table + "(ISBN, aid) VALUES (?,?)";
		
		//notify of the statement
		System.out.println("executing " + sql);
		try{
			//prepare connection
	 		Connector con = new Connector();
	 		PreparedStatement prep = con.con.prepareStatement(sql);
	 		
	 		//bind the variables
	 		prep.setString(1, isbn);
	 		prep.setInt(2, this.aid);
	 		prep.executeUpdate();
	 		
	 		//close connection
	 		prep.close();
	 		con.closeConnection();
	 		
	 		//return true on success
	 		return true;
	 	}
	 	catch(Exception e)
	 	{
	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
    		System.err.println(e.getMessage());
    		return false;
	 	}
	}
}
