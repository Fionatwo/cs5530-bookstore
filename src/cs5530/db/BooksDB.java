package cs5530.db;

import java.sql.*;
import java.util.ArrayList;

import cs5530.Book;
import cs5530.Connector;


/**
 * 
 * @author Alex Stout
 * @for University of Utah - CS5530 - Database Systems
 *
 * This is the BooksDB Class
 * It is the interface to the database with respect to Books in general.
 * 
 */
public class BooksDB {
	
	static String table = "Books";

	String isbn, title, publisher, year, language, format;
	double price;
	int copies;	
	
	public BooksDB(){}

	/**
	 * Lists all books from the Books table by Title.
	 */
	public static ArrayList<Book> listByTitle() {
		ArrayList<Book> results = new ArrayList<Book>(); //to store the results
		PreparedStatement prep;
		//prepare the sql statement
		String sql = "select * from " + table + "order by Title";
		System.out.println("executing " + sql);
		try{
			//prepare connection
	 		Connector con = new Connector();
	 		prep = con.con.prepareStatement(sql);
	 		
	 		//execute and store the result
	 		ResultSet rs = prep.executeQuery();
	 		
	 		//for each match, create a Book and store it in an ArrayList
	 		while(rs.next())
	 		{
	 			String _isbn = rs.getString("ISBN");
	 			String _title = rs.getString("Title");
	 			String _year = rs.getString("Year");
	 			String _publisher = rs.getString("Publisher");
	 			String _language = rs.getString("Language");
	 			String _format = rs.getString("Format");
	 			double _price = rs.getDouble("Price");
	 			int _copies = rs.getInt("Copies");
	 			results.add(new Book(_isbn, _title, _year, _publisher, _language, _format, _price, _copies));
	 		}
	 		
	 		//close the connection
	 		rs.close();
	 		prep.close();
	 		con.closeConnection();
	 		
	 		return results;
	 	}
	 	catch(Exception e)
	 	{
	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
    		System.err.println(e.getMessage());
    		return null;
	 	}
	}

	/**
	 * Registers a new book into the database.  A book in the database is to be thought of as a collection
	 * of books that have the same ISBN.  So all (same ISBN) copies of "The Great Gatsby" are to be one record in the
	 * database.
	 * 
	 * The parameters publisher, year, and language are nullable.
	 * 
	 * @param isbn
	 * @param title
	 * @param publisher
	 * @param year
	 * @param language
	 * @param format
	 * @param price
	 * @param copies
	 * @return true on success, else false
	 */
	public static boolean newBook(String isbn, String title, String publisher, String year, String language, String format, double price, int copies) {
		
		//prepare the sql statement
		String sql = "insert into " + table + "(ISBN, Title, Publisher, Year, Language, Format, Price, Copies) VALUES (?,?,?,?,?,?,?,?)";
		
		//notify user of the statement
		System.out.println("executing " + sql);
		try{
			//prepare connection
	 		Connector con = new Connector();
	 		PreparedStatement prep = con.con.prepareStatement(sql);
	 		
	 		//bind the variables
	 		prep.setString(1, isbn);
	 		prep.setString(2, title);
	 		prep.setString(3, publisher);
	 		prep.setString(4, year);
	 		prep.setString(5, language);
	 		prep.setString(6, format);
	 		prep.setDouble(7, price);
	 		prep.setInt(8, copies);
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
	
	/**
	 * Gets the count of unique book titles in the database.
	 * @query "SELECT COUNT('ISBN') AS count FROM Books"
	 * 
	 * @return int count, on error returns -1
	 */
	public int getCountUniqueTitles() {
		
		//begin the sql statement
		String sql = "SELECT COUNT('ISBN') AS count FROM " + table;
		
		System.out.println("executing " + sql); //notify
		try{
			//prepare connection
	 		Connector con = new Connector();
	 		PreparedStatement prep = con.con.prepareStatement(sql);
	 		
	 		//execute and store the result
	 		ResultSet rs = prep.executeQuery();
	 		
	 		int count = 0;
	 		//This query will result in one column named 'count'
	 		while(rs.next())
	 		{
	 			count = rs.getInt("count");
	 		}
	 		return count;
	 	}
	 	catch(Exception e)
	 	{
	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
    		System.err.println(e.getMessage());
    		return -1;
	 	}
	}
	
	/**
	 * Searches Books for matches of the given string in Title.
	 * The words can be out of order, but each word must be in the title.
	 * 
	 * @param key
	 * @param sort, int: 1 = sort by year, 2 = sort by Avg Rating,
	 * 3 = sort by Avg Trusted User Rating.
	 * @param login, string: The user login for getting their trusted users.
	 * @return ArrayList<Book> - an ArrayList of all the matching Books.  
	 * See the Book class.
	 * @query SELECT * FROM Books WHERE TITLE LIKE ? ... and TITLE LIKE ? ...
	 * 
	 * select * from Books b natural left join
	 * (select * from Books_AvgRating bavg natural join
     * -- Gets avg trusted reviews for user batman
	 * (select r.ISBN, AVG(r.Score) as TrustRating from Reviews r join Trusts t on r.Login=t.Trustee and t.Assessment=true and t.Truster='bush' group by r.ISBN) as t0) as main2
	 * -- where title like '%potter%'
	 */
	public static ArrayList<Book> searchByTitle(String key, int sort, String login) {
		ArrayList<Book> results = new ArrayList<Book>(); //to store the results
		PreparedStatement prep;
		String end = "";
		String sql = ""; //initialize the sql string
		String trustedRatings = userTrustedRatings(login);
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
		}
		try{
			Connector con = new Connector();
	 		String[] words = key.split("\\s+"); //split the input into words
	 		
	 		//begin the statement
	 		sql = "select * from " + table + " b natural left join ";
	 		sql += "(select * from Books_AvgRating bavg natural join ";
	 		sql += "("+trustedRatings+") as t0) as AllRatings ";
	 		sql += "where Title like ?";
	 		
	 		//for each extra word, append to the statement
			for ( int i = 1; i < words.length; i++ )
			{
				sql += " and Title like ?";
			}
			
			sql += end;
			
			//notify and prepare
			System.out.println("executing " + sql); 
			prep = con.con.prepareStatement(sql);
			
			//bind the variables to the statement
			for ( int i = 0; i < words.length; i++)
			{
				prep.setString(i+1, "%"+words[i]+"%");
			}
	 		
	 		//execute and get the result
	 		ResultSet rs = prep.executeQuery();
	 		ResultSetMetaData rsmd = rs.getMetaData();
	 		int numCols = rsmd.getColumnCount();
			int count = 0;
	 		
	 		//for each match, create a Book object and store it in the ArrayList
	 		while(rs.next())
	 		{
	 			String _isbn = rs.getString("ISBN");
	 			String _title = rs.getString("Title");
	 			String _year = rs.getString("Year");
	 			String _publisher = rs.getString("Publisher");
	 			String _language = rs.getString("Language");
	 			String _format = rs.getString("Format");
	 			double _price = rs.getDouble("Price");
	 			int _copies = rs.getInt("Copies");
	 			double _avgRating = 0;
	 			double _trustRating = 0;
	 			try {_avgRating = rs.getDouble("AvgRating"); } catch(Exception e){}
	 			try {_trustRating = rs.getDouble("TrustRating"); } catch(Exception e){}
	 			results.add(new Book(_isbn, _title, _year, _publisher, _language, _format, _price, _copies, _avgRating, _trustRating));
	 		}
	 		
	 		//close the connection
	 		rs.close();
	 		prep.close();
	 		con.closeConnection();
	 		
	 		return results;
	 	}
	 	catch(Exception e)
	 	{
	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
    		System.err.println(e.getMessage());
    		return new ArrayList<Book>();
	 	}
	}
	
	/**
	 * Queries the database for books authored by the author represented in the string input.
	 * It can be any match of the first or last name.
	 * 
	 * @param key
	 * @return ArrayList<Book> collection of the matched Books.
	 * See Book class.
	 * @query Select * from Books where ISBN IN (SELECT ISBN from Authored_By where aid IN (Select aid FROM Authors where FirstName like ? or LastName like ?));
	 */
	public static ArrayList<Book> searchByAuthor(String key, int sort) {
		ArrayList<Book> results = new ArrayList<Book>(); //to store the results
		PreparedStatement prep;
		String sql = ""; //initialize the sql string
		try{
			Connector con = new Connector();
	 		String[] words = key.split("\\s+"); //split the input into an array of words
	 		
	 		//start the sql statement
	 		sql = "select * from " + table + " where ISBN IN (SELECT ISBN FROM Authored_By WHERE aid IN "; 
	 		sql += "(SELECT aid FROM Authors where (FirstName like ? or LastName like ?)";
	 		
	 		//for each word after the first
			for ( int i = 1; i < words.length; i++ )
			{
				sql += " and (FirstName like ? or LastName like ?)";
			}
			sql += "))"; //end the sql statement
			
			System.out.println("executing " + sql); //notify of sql statement
			prep = con.con.prepareStatement(sql); //prepare the statement
			
			//bind the input variables to the statement
			int j = 1;
			for ( int i = 0; i < words.length; i++ )
			{
				prep.setString(j++, "%"+words[i]+"%");
				prep.setString(j++, "%"+words[i]+"%");
			}
	 		
	 		//execute
	 		ResultSet rs = prep.executeQuery();
	 		
	 		//for each matched result, create a book and put it in the arraylist.
	 		while(rs.next())
	 		{
	 			String _isbn = rs.getString("ISBN");
	 			String _title = rs.getString("Title");
	 			String _year = rs.getString("Year");
	 			String _publisher = rs.getString("Publisher");
	 			String _language = rs.getString("Language");
	 			String _format = rs.getString("Format");
	 			double _price = rs.getDouble("Price");
	 			int _copies = rs.getInt("Copies");
	 			results.add(new Book(_isbn, _title, _year, _publisher, _language, _format, _price, _copies));
	 		}
	 		
	 		//close the connection
	 		rs.close();
	 		prep.close();
	 		con.closeConnection();
	 		
	 		return results;
	 	}
	 	catch(Exception e)
	 	{
	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
    		System.err.println(e.getMessage());
    		return new ArrayList<Book>();
	 	}
	}
	
	/**
	 * Search by genre searches for matches of books by genre.
	 * It takes a string of one or more words that may match any
	 * genre for a given book.  It will only return books that
	 * which have genres that match every word given in the input.
	 * 
	 * @param key: input String to match the desired genres
	 * @return ArrayList<Book> containing collection of Books from query.
	 * @query Select * from Books where ISBN in 
	 * (Select i1 from 
	 *		(Select ISBN as i1 from Is_Genre where gid in 
	 *			(Select gid from Genres where Genre like '%fantasy%')
	 *		) as t1
	 *		JOIN 
	 *		(Select ISBN as i2 from Is_Genre where gid in 
	 *			(Select gid from Genres where Genre like '%fiction%')
	 *		) as t2
	 *		JOIN 
	 *		(Select ISBN as i3 from Is_Genre where gid in 
	 *			(Select gid from Genres where Genre like '%teen%')
	 *		) as t3
	 *		ON (i1=i2) and (i2 = i3)
	 *	);
	 */
	public static ArrayList<Book> searchByGenre(String key, String sort) {
		ArrayList<Book> results = new ArrayList<Book>(); //to store the results
		PreparedStatement prep;
		String sql = ""; //initialize the sql string
		try{
			Connector con = new Connector();
	 		String[] words = key.split("\\s+");
	 		sql = "SELECT * FROM " + table + " WHERE ISBN IN (SELECT i0 FROM "; //begin sql statement
	 		
	 		//For each word add the following to the sql statement
			for ( int i = 0; i < words.length; i++ )
			{
				if( i != 0 ) //only add this if not first word
				{
					sql += " JOIN ";
				}
				sql += "(SELECT ISBN as i"+i+" FROM Is_Genre WHERE gid IN ";
				sql += "(Select gid from Genres where Genre like ?) ";
				sql += ") as t"+i;
				
			}
			
			//Add the the join ending if longer than one word
			if(words.length > 1)
			{
				sql += " ON ";
				for(int j = 0; j < words.length - 1; j++)
				{
					sql += j != 0 ? " and " : "";
					sql += "(i"+j+"=i"+(j+1)+")";
				}
			}
			sql += ")"; //end sql statement
			
			//notify sql statement
			System.out.println("executing " + sql);
			prep = con.con.prepareStatement(sql); //prepare statement
			
			//bind input
			for ( int i = 0; i < words.length; i++)
			{
				prep.setString(i+1, "%"+words[i]+"%");
			}
	 		
	 		ResultSet rs = prep.executeQuery(); //execute
	 		
	 		//for each input, create a Book and add it to result ArrayList
	 		while(rs.next())
	 		{
	 			String _isbn = rs.getString("ISBN");
	 			String _title = rs.getString("Title");
	 			String _year = rs.getString("Year");
	 			String _publisher = rs.getString("Publisher");
	 			String _language = rs.getString("Language");
	 			String _format = rs.getString("Format");
	 			double _price = rs.getDouble("Price");
	 			int _copies = rs.getInt("Copies");
	 			results.add(new Book(_isbn, _title, _year, _publisher, _language, _format, _price, _copies));
	 		}
	 		
	 		//close connection
	 		rs.close();
	 		prep.close();
	 		con.closeConnection();
	 		
	 		return results;
	 	}
	 	catch(Exception e)
	 	{
	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
    		System.err.println(e.getMessage());
    		return new ArrayList<Book>();
	 	}
	}

	/**
	 * Searches for Books from the database that match the input string.
	 * 
	 * @param key string input to match books with the desired publisher
	 * @return ArrayList<Book> collection of Books that resulted from the query.
	 * @query SELECT * FROM Books WHERE Publisher LIKE ? ... and Publisher LIKE ? ...
	 */
	public static ArrayList<Book> searchByPublisher(String key) {
		ArrayList<Book> results = new ArrayList<Book>(); //to store the results
		PreparedStatement prep;
		String sql = ""; //initialize the sql string
		try{
			Connector con = new Connector();
	 		String[] words = key.split("\\s+"); //split the input into words
	 		
	 		//begin the statement
	 		sql = "select * from " + table + " where Publisher like ?";
	 		
	 		//for each extra word, append to the statement
			for ( int i = 1; i < words.length; i++ )
			{
				sql += " and Publisher like ?";
			}
			
			//notify and prepare
			System.out.println("executing " + sql); 
			prep = con.con.prepareStatement(sql);
			
			//bind the variables to the statement
			for ( int i = 0; i < words.length; i++)
			{
				prep.setString(i+1, "%"+words[i]+"%");
			}
	 		
	 		//execute and get the result
	 		ResultSet rs = prep.executeQuery();
	 		
	 		//for each match, create a Book object and store it in the ArrayList
	 		while(rs.next())
	 		{
	 			String _isbn = rs.getString("ISBN");
	 			String _title = rs.getString("Title");
	 			String _year = rs.getString("Year");
	 			String _publisher = rs.getString("Publisher");
	 			String _language = rs.getString("Language");
	 			String _format = rs.getString("Format");
	 			double _price = rs.getDouble("Price");
	 			int _copies = rs.getInt("Copies");
	 			results.add(new Book(_isbn, _title, _year, _publisher, _language, _format, _price, _copies));
	 		}
	 		
	 		//close the connection
	 		rs.close();
	 		prep.close();
	 		con.closeConnection();
	 		
	 		return results;
	 	}
	 	catch(Exception e)
	 	{
	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
    		System.err.println(e.getMessage());
    		return new ArrayList<Book>();
	 	}
	}
	
	private static String userTrustedRatings(String login) {
		//select r.ISBN, AVG(r.Score) as TrustRating from Reviews r join Trusts t on r.Login=t.Trustee and t.Assessment=true and t.Truster=$USER group by r.ISBN
		String trustedRatings = "select r.ISBN, AVG(r.Score) as TrustRating from Reviews r join Trusts t ";
		trustedRatings += "on r.Login=t.Trustee and t.Assessment=true and t.Truster=";
		trustedRatings += "'"+login+"' group by r.ISBN";
		return trustedRatings;
	}

}
