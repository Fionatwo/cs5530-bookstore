package cs5530;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import cs5530.db.DatabaseModel;


/**
 * 
 * @author Alex Stout
 * @for University of Utah - CS5530 - Database Systems
 *
 * This is the Customer Class
 */
public class Book extends DatabaseModel{
	
	String isbn, title, publisher, year, language, format, price, avgRating, trustRating, copies;
//	double price, avgRating, trustRating;
//	int copies;	
	
	public Book() {
		table = "Books";
		columns.add("ISBN");
		columns.add("Title");
		columns.add("Publisher");
		columns.add("Year");
		columns.add("Language");
		columns.add("Format");
		columns.add("Price");
		columns.add("Copies");
		primaryKeyColumns.add("ISBN");
	}
	
//	/**
//	 * Book constructor with average score property.
//	 * @param isbn
//	 * @param title
//	 * @param publisher
//	 * @param year
//	 * @param language
//	 * @param format
//	 * @param price
//	 * @param copies
//	 * @param avgRating
//	 * @param trustRating
//	 */
//	public Book(String isbn, String title, String publisher, String year, String language, String format, 
//			String price, String copies, String avgRating, String trustRating) {
//		colValPairs.put("ISBN", isbn);
//		colValPairs.put("Title", title);
//		colValPairs.put("Year", year);
//		colValPairs.put("Language", language);
//		colValPairs.put("Format", format);
//		colValPairs.put("price", price);
//		colValPairs.put("copies", copies);
//		this.isbn = isbn;
//		this.title = title;
//		this.year = year;
//		this.publisher = publisher;
//		this.language = language;
//		this.format = format;
//		this.price = price;
//		this.copies = copies;
//		this.avgRating = avgRating;
//		this.trustRating = trustRating;
//	}
	
	/**
	 * Book constructor without average score property.
	 * @param isbn
	 * @param title
	 * @param publisher
	 * @param year
	 * @param language
	 * @param format
	 * @param price
	 * @param copies
	 */
	public Book(String isbn, String title, String publisher, String year, String language, String format, 
			String price, String copies) {
		table = "Books";
		columns.add("ISBN");
		columns.add("Title");
		columns.add("Publisher");
		columns.add("Year");
		columns.add("Language");
		columns.add("Format");
		columns.add("Price");
		columns.add("Copies");
		primaryKeyColumns.add("ISBN");
		colValPairs.put("ISBN", isbn);
		colValPairs.put("Title", title);
		colValPairs.put("Publisher", publisher);
		colValPairs.put("Year", year);
		colValPairs.put("Language", language);
		colValPairs.put("Format", format);
		colValPairs.put("Price", price);
		colValPairs.put("Copies", copies);
		this.isbn = isbn;
		this.title = title;
		this.year = year;
		this.publisher = publisher;
		this.language = language;
		this.format = format;
		this.price = price;
		this.copies = copies;
	}
	
	public String getISBN()
	{
		if(primaryKey.get("ISBN") == null)
		{
			this.queryKey();
		}
		return primaryKey.get("ISBN");
	}
	
	/**
	 * select * from Books b natural left join
	 * (select * from Books_AvgRating bavg natural join
	 * -- Gets avg trusted reviews for user batman
	 * (select r.ISBN, AVG(r.Score) as TrustRating from Reviews r join Trusts t
	 * on r.Login=t.Trustee and t.Assessment=true and t.Truster='bush' group by r.ISBN) 
	 * as t0) as main2
	 * where
	 * ISBN IN (SELECT ISBN FROM Authored_By WHERE aid IN
	 * (SELECT aid FROM Authors where FirstName like '%%' and LastName like '%%'))
	 * 
	 * @param key
	 * @param sort
	 * @param trustedUsers
	 * @return
	 */
	public ArrayList<HashMap<String, String>> searchByAuthorTrusted(String key, int sort, String trustedUsers) {
		lastQueryResult = new ArrayList<HashMap<String, String>>(); //to store the results
		String sql = ""; //initialize the sql string
		String end = "";
 		String[] words = key.split("\\s+"); //split the input into an array of words
 		
 		//start the sql statement
 		
 		//begin the statement
 		sql = "select * from " + table + " b natural left join ";
 		sql += "(select * from Books_AvgRating bavg natural join ";
 		sql += "("+trustedUsers+") as TrustedUsers) as AllRatings ";
 		if(words.length > 0)
 		{
 			sql += "where ";
 			sql += "ISBN IN (SELECT ISBN FROM Authored_By WHERE aid IN "; 
 			sql += "(SELECT aid FROM Authors where ";
 			
 			//for each word after the first
 			for ( String s : words )
 			{
 				sql += "(FirstName like ";
 				sql += "'%"+s+"%'";
 				sql += " or LastName like ";
 				sql += "'%"+s+"%') and ";
 			}
 			int i = sql.lastIndexOf(" and ");
 			sql=sql.substring(0, i);
 			sql += "))"; //end the sql statement
 		}
		
		switch(sort)
		{
		case 1:
			end = " ORDER BY Year";
			break;
		case 2:
			end = " ORDER BY AvgRating desc ";
			break;
		case 3:
			end = " ORDER BY TrustRating desc ";
			break;
		default:
			end = "";
			break;
		}
		return CustomQuery(sql+end);
	}
	
	public ArrayList<HashMap<String, String>> searchByAuthor(String key, int sort) {
		lastQueryResult = new ArrayList<HashMap<String, String>>(); //to store the results
		String sql = ""; //initialize the sql string
		String end = "";
 		String[] words = key.split("\\s+"); //split the input into an array of words
 		
 		//start the sql statement
 		
 		//begin the statement
 		sql = "Select * from " + table + " natural join ";
 		sql += "(select * from " + "Books_AvgRating bavg" + " where ISBN IN (SELECT ISBN FROM Authored_By WHERE aid IN "; 
 		sql += "(SELECT aid FROM Authors where ";
 		
 		//for each word after the first
		for ( String s : words )
		{
			sql += "(FirstName like ";
			sql += "'%"+s+"%'";
			sql += " or LastName like ";
			sql += "'%"+s+"%') and ";
		}
		int i = sql.lastIndexOf(" and ");
		sql=sql.substring(0, i);
		sql += "))) as t"; //end the sql statement
		
		switch(sort)
		{
		case 1:
			end = " ORDER BY Year";
			break;
		case 2:
			end = " ORDER BY AvgRating desc ";
			break;
		default:
			end = "";
			break;
		}
		return CustomQuery(sql+end);
	}
	
	public ArrayList<HashMap<String, String>> searchByGenreTrusted(String key, int sort, String trustedUsers) {
		lastQueryResult = new ArrayList<HashMap<String, String>>(); //to store the results
		String sql = ""; //initialize the sql string
		String end = "";
 		String[] words = key.split("\\s+"); //split the input into an array of words
 		
 		//start the sql statement
 		
 		//begin the statement
 		sql = "select * from " + table + " b natural left join ";
 		sql += "(select * from Books_AvgRating bavg natural join ";
 		sql += "("+trustedUsers+") as TrustedUsers) as AllRatings ";
 		if(words.length > 0)
 		{
 			sql += "where ";
 			sql += "ISBN IN (SELECT ISBN FROM Is_Genre WHERE gid IN "; 
 			sql += "(SELECT gid FROM Genres where ";
 			
 			//for each word after the first
 			for ( String s : words )
 			{
 				sql += "Genre like ";
 				sql += "'%"+s+"%' and ";
 			}
 			int i = sql.lastIndexOf(" and ");
 			sql=sql.substring(0, i);
 			sql += "))"; //end the sql statement
 		}
		
		switch(sort)
		{
		case 1:
			end = " ORDER BY Year";
			break;
		case 2:
			end = " ORDER BY AvgRating desc ";
			break;
		case 3:
			end = " ORDER BY TrustRating desc ";
			break;
		default:
			end = "";
			break;
		}
		return CustomQuery(sql+end);
	}
	
	public ArrayList<HashMap<String, String>> searchByGenre(String key, int sort) {
		lastQueryResult = new ArrayList<HashMap<String, String>>(); //to store the results
		String sql = ""; //initialize the sql string
		String end = "";
 		String[] words = key.split("\\s+"); //split the input into an array of words
 		
 		//start the sql statement
 		
 		//begin the statement
 		sql = "Select * from " + table + " natural join ";
 		sql += "(select * from " + "Books_AvgRating bavg" + " where ISBN IN (SELECT ISBN FROM Is_Genre WHERE gid IN "; 
 		sql += "(SELECT gid FROM Genres where ";
 		
 		//for each word after the first
		for ( String s : words )
		{
			sql += "Genre like ";
			sql += "'%"+s+"%' and ";
		}
		int i = sql.lastIndexOf(" and ");
		sql=sql.substring(0, i);
		sql += "))) as t"; //end the sql statement
		
		switch(sort)
		{
		case 1:
			end = " ORDER BY Year";
			break;
		case 2:
			end = " ORDER BY AvgRating desc ";
			break;
		default:
			end = "";
			break;
		}
		return CustomQuery(sql+end);
	}
	
	public ArrayList<HashMap<String, String>> searchByTitleTrusted(String key, int sort, String trustedUsers) {
		lastQueryResult = new ArrayList<HashMap<String, String>>(); //to store the results
		String sql = ""; //initialize the sql string
		String end = "";
 		String[] words = key.split("\\s+"); //split the input into an array of words
 		
 		//start the sql statement
 		sql = "select * from ";
 		sql += table + " b natural left join ";
 		sql += "(select * from Books_AvgRating bavg natural join ";
 		sql += "("+trustedUsers+") as TrustedUsers ";
 		sql += ") as main ";
 		if(words.length > 0)
 		{
 			sql += "where ";
 			
 			//for each word after the first
 			for ( String s : words )
 			{
 				sql += "Title like ";
 				sql += "'%"+s+"%' and ";
 			}
 			int i = sql.lastIndexOf(" and ");
 			sql=sql.substring(0, i);
 		}
		
		switch(sort)
		{
		case 1:
			end = " ORDER BY Year";
			break;
		case 2:
			end = " ORDER BY AvgRating desc ";
			break;
		case 3:
			end = " ORDER BY TrustRating desc ";
			break;
		default:
			end = " ORDER BY Title";
			break;
		}
		return this.CustomQuery(sql+end);
	}
	
	/**
	 * select * from Books b natural left join
	 * (select * from Books_AvgRating bavg natural join
     * -- Gets avg trusted reviews for user batman
	 * (select r.ISBN, AVG(r.Score) as TrustRating from Reviews r join Trusts t 
	 * on r.Login=t.Trustee and t.Assessment=true and t.Truster='bush' group by r.ISBN) as t0) as main2
	 * -- where title like '%potter%'
	 * select * from Books b natural left join Books_AvgRating bavg where title like '%great%' and title like '%%'
	 * @param key
	 * @param sort
	 * @return
	 */
	public ArrayList<HashMap<String, String>> searchByTitle(String key, int sort) {
		lastQueryResult = new ArrayList<HashMap<String, String>>(); //to store the results
		String sql = ""; //initialize the sql string
		String end = "";
 		String[] words = key.split("\\s+"); //split the input into an array of words
 		
 		//start the sql statement
 		sql = "select * from ";
// 		sql += table + " b natural left join ";
 		sql += "Books_AvgRating bavg ";
 		if(words.length > 0)
 		{
 			sql += "where ";
 			
 			//for each word after the first
 			for ( String s : words )
 			{
 				sql += "Title like ";
 				sql += "'%"+s+"%') and ";
 			}
 			int i = sql.lastIndexOf(" and ");
 			sql=sql.substring(0, i);
 		}
		
		switch(sort)
		{
		case 1:
			end = " ORDER BY Year";
			break;
		case 2:
			end = " ORDER BY AvgRating desc ";
			break;
		default:
			end = "";
			break;
		}
		return CustomQuery(sql+end);
	}
	
	public ArrayList<HashMap<String, String>> searchByPublisherTrusted(String key, int sort, String trustedUsers) {
		lastQueryResult = new ArrayList<HashMap<String, String>>(); //to store the results
		String sql = ""; //initialize the sql string
		String end = "";
 		String[] words = key.split("\\s+"); //split the input into an array of words
 		
 		//start the sql statement
 		sql = "select * from ";
 		sql += table + " b natural left join ";
 		sql += "(select * from Books_AvgRating bavg natural join ";
 		sql += "("+trustedUsers+") as TrustedUsers ";
 		sql += ") as main ";
 		if(words.length > 0)
 		{
 			sql += "where ";
 			
 			//for each word after the first
 			for ( String s : words )
 			{
 				sql += "Publisher like ";
 				sql += "'%"+s+"%' and ";
 			}
 			int i = sql.lastIndexOf(" and ");
 			sql=sql.substring(0, i);
 		}
		
		switch(sort)
		{
		case 1:
			end = " ORDER BY Year";
			break;
		case 2:
			end = " ORDER BY AvgRating desc ";
			break;
		case 3:
			end = " ORDER BY TrustRating desc ";
			break;
		default:
			end = "";
			break;
		}
		return this.CustomQuery(sql+end);
	}
	
	public ArrayList<HashMap<String, String>> searchByPublisher(String key, int sort) {
		lastQueryResult = new ArrayList<HashMap<String, String>>(); //to store the results
		String sql = ""; //initialize the sql string
		String end = "";
 		String[] words = key.split("\\s+"); //split the input into an array of words
 		
 		//start the sql statement
 		sql = "select * from ";
// 		sql += table + " b natural left join ";
 		sql += "Books_AvgRating bavg ";
 		if(words.length > 0)
 		{
 			sql += "where ";
 			
 			//for each word after the first
 			for ( String s : words )
 			{
 				sql += "Publisher like ";
 				sql += "'%"+s+"%') and ";
 			}
 			int i = sql.lastIndexOf(" and ");
 			sql=sql.substring(0, i);
 		}
		
		switch(sort)
		{
		case 1:
			end = " ORDER BY Year";
			break;
		case 2:
			end = " ORDER BY AvgRating desc ";
			break;
		default:
			end = "";
			break;
		}
		return CustomQuery(sql+end);
	}
	
//	/**
//	 * Book to string function.  Takes boolean parameter that if
//	 * true, returns the string of all the Data tabified.
//	 * Else, it returns only the Title, language, format, price, and copies tabified.
//	 * @param more
//	 * @return
//	 */
//	public String toString(boolean more) {
//		String line = "";
//		if(more)
//		{
//			line += this.isbn != null ? this.isbn + "\t" : "";
//			line += this.title != null ? this.title + "\t" : "";
//			line += this.publisher != null ? this.publisher + "\t" : "";
//			line += this.year != null ? this.year + "\t" : "";
//			line += this.language != null ? this.language + "\t" : "";
//			line += this.format != null ? this.format + "\t" : "";
//			line += this.price != 0 ? "$"+this.price + "\t" : "0.00";
//			line += this.copies != 0 ? this.copies + "\t" : "0";
//			line += this.avgRating != 0 ? this.avgRating + "\t" : "0";
//			line += this.trustRating != 0 ? this.trustRating + "\t" : "0";
//			return line;
//		}
//		line += this.title != null ? this.title + "\t" : "";
//		line += this.language != null ? this.language + "\t" : "";
//		line += this.format != null ? this.format + "\t" : "";
//		line += this.price != 0 ? "$"+this.price + "\t" : "0.00";
//		line += this.copies != 0 ? this.copies + "\t" : "0";
//		line += this.avgRating != 0 ? this.avgRating + "\t" : "0";
//		line += this.trustRating != 0 ? this.trustRating + "\t" : "0";
//		return line;
//		
//	}
//
//	/**
//	 * TODO: GIVE AN ACTUAL RETURN TYPE
//	 * THIS SHOULD RETURN ALL DATA
//	 */
//	public void getAllBooks() {
//		String sql = "select * from " + table;
//		System.out.println("executing " + sql);
//		try{
//	 		Connector con = new Connector();
//	 		PreparedStatement prep = con.con.prepareStatement(sql);
//	 		ResultSet rs = prep.executeQuery();
//	 		while(rs.next())
//	 		{
//	 			
//	 		}
//	 	}
//	 	catch(Exception e)
//	 	{
//	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
//    		System.err.println(e.getMessage());
//	 	}
//	}

//	public boolean newBook(String isbn, String title, String publisher, String year, String language, String format, float price, int copies) {
//		String sql = "insert into " + table + "(ISBN, Title, Publisher, Year, Language, Format, Price, Copies) VALUES (?,?,?,?,?,?,?,?)";
//		System.out.println("executing " + sql);
//		try{
//	 		Connector con = new Connector();
//	 		PreparedStatement prep = con.con.prepareStatement(sql);
//	 		prep.setString(1, isbn);
//	 		prep.setString(2, title);
//	 		prep.setString(3, publisher);
//	 		prep.setString(4, year);
//	 		prep.setString(5, language);
//	 		prep.setString(6, format);
//	 		prep.setFloat(7, price);
//	 		prep.setInt(8, copies);
//	 		prep.executeUpdate();
//	 		return true;
//	 	}
//	 	catch(Exception e)
//	 	{
//	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
//    		System.err.println(e.getMessage());
//    		return false;
//	 	}
//	}

}
