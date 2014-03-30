package cs5530;

import java.sql.*;


/**
 * 
 * @author Alex Stout
 * @for University of Utah - CS5530 - Database Systems
 *
 * This is the Customer Class
 */
public class Book {
	
	String table = "Books";

	String isbn, title, publisher, year, language, format;
	double price, avgRating, trustRating;
	int copies;	
	
	/**
	 * Book constructor with average score property.
	 * @param isbn
	 * @param title
	 * @param publisher
	 * @param year
	 * @param language
	 * @param format
	 * @param price
	 * @param copies
	 * @param avgRating
	 * @param trustRating
	 */
	public Book(String isbn, String title, String publisher, String year, String language, String format, double price, int copies, double avgRating, double trustRating) {
		this.isbn = isbn;
		this.title = title;
		this.year = year;
		this.publisher = publisher;
		this.language = language;
		this.format = format;
		this.price = price;
		this.copies = copies;
		this.avgRating = avgRating;
		this.trustRating = trustRating;
	}
	
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
	public Book(String isbn, String title, String publisher, String year, String language, String format, double price, int copies) {
		this.isbn = isbn;
		this.title = title;
		this.year = year;
		this.publisher = publisher;
		this.language = language;
		this.format = format;
		this.price = price;
		this.copies = copies;
	}
	
	/**
	 * Book to string function.  Takes boolean parameter that if
	 * true, returns the string of all the Data tabified.
	 * Else, it returns only the Title, language, format, price, and copies tabified.
	 * @param more
	 * @return
	 */
	public String toString(boolean more) {
		String line = "";
		if(more)
		{
			line += this.isbn != null ? this.isbn + "\t" : "";
			line += this.title != null ? this.title + "\t" : "";
			line += this.publisher != null ? this.publisher + "\t" : "";
			line += this.year != null ? this.year + "\t" : "";
			line += this.language != null ? this.language + "\t" : "";
			line += this.format != null ? this.format + "\t" : "";
			line += this.price != 0 ? "$"+this.price + "\t" : "0.00";
			line += this.copies != 0 ? this.copies + "\t" : "0";
			line += this.avgRating != 0 ? this.avgRating + "\t" : "0";
			line += this.trustRating != 0 ? this.trustRating + "\t" : "0";
			return line;
		}
		line += this.title != null ? this.title + "\t" : "";
		line += this.language != null ? this.language + "\t" : "";
		line += this.format != null ? this.format + "\t" : "";
		line += this.price != 0 ? "$"+this.price + "\t" : "0.00";
		line += this.copies != 0 ? this.copies + "\t" : "0";
		line += this.avgRating != 0 ? this.avgRating + "\t" : "0";
		line += this.trustRating != 0 ? this.trustRating + "\t" : "0";
		return line;
		
	}

	/**
	 * TODO: GIVE AN ACTUAL RETURN TYPE
	 * THIS SHOULD RETURN ALL DATA
	 */
	public void getAllBooks() {
		String sql = "select * from " + table;
		System.out.println("executing " + sql);
		try{
	 		Connector con = new Connector();
	 		PreparedStatement prep = con.con.prepareStatement(sql);
	 		ResultSet rs = prep.executeQuery();
	 		while(rs.next())
	 		{
	 			
	 		}
	 	}
	 	catch(Exception e)
	 	{
	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
    		System.err.println(e.getMessage());
	 	}
	}

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
