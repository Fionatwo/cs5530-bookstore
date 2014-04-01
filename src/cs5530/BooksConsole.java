package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @author Alex Stout - u0583813
 * @for CS5530 - Databases
 * @organization University of Utah
 * @since Spring 2014
 *
 * The console interface to books in the database.	
 */
public class BooksConsole {
	
	static String login = "";
	
	@SuppressWarnings("null")
	public static int browseMain(String user) {
		login = user;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String choice;
		int c=0;
		try
		{
			while(true)
			{
				BrowseBooksMenu();
				while ((choice = in.readLine()) == null && choice.length() == 0);
				try{
					c = Integer.parseInt(choice);
				}catch (Exception e)
				{
					System.out.println("Invalid selection. Try again.");
					continue;
				}
				switch(c)	
				{
				case 0:
					return 0;
				case 1:
					/* SEARCH BY ANY KEYWORD */
					break;
				case 2:
					/* SEARCH BY TITLE */
					searchBy("Title");
					break;
				case 3:
					/* SEARCH BY AUTHOR */
					searchBy("Author");
					break;
				case 4:
					/* SEARCH BY GENRE */
					searchBy("Genre");
					break;
				case 5:
					/* SEARCH BY PUBLISHER */
					searchBy("Publisher");
					break;
				case 6:
					/* LIST BY TITLE */
					Book b = new Book();
					ArrayList<HashMap<String, String>> result = b.searchByTitleTrusted("", 0, User.trustedUsersSQL(login));
					System.out.println(b.lastQueryToString());
					break;
				case 7:
					 /* LIST BY AUTHOR */
					break;
				case 8:
					 /* LIST BY REVIEW SCORES */
					break;
				case 9:
					/* LIST BY TRUSTED USER SCORES */
					break;
				case 10:
					/* CUSTOM QUERY */
					return 10;
				case 11:
					/* BACK TO MAIN MENU */
					return 11;
				case 12:
					/* LOG OUT AND GO TO WELCOME MENU  */
					return 12;
				case 99:
					/* RETURN MANAGER TO MANAGER MENU */
					return 99;
				default:
					System.out.println("Invalid selection. Try again.");
//					browseMain();
					continue;
				}
			}
		 }
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Prints the Browse Books Menu.
	 */
	private static void BrowseBooksMenu() {
		System.out.println("      -- Browse Books --     ");
		System.out.println("Options:");
		System.out.println("1: Search by any keyword.");
		System.out.println("2: Search by Title.");
		System.out.println("3: Search by Author.");
		System.out.println("4: Search by Genre");
		System.out.println("5: Search by Publisher");
		System.out.println("6: List By Title.");
		System.out.println("7: List By Author.");
		System.out.println("8: List By Review Scores. ");
		System.out.println("9: List By Trusted User Scores.");
		System.out.println("10: Custom Query.");
		System.out.println("11: Back to Main Menu.");
		System.out.println("12: Log out and go to Welcome Menu.");
		System.out.println("0: Log out and exit.");
		System.out.println("Select an option:");
	}
	
	
	/**
	 * The modular searchBy method takes a String column that specifies which field
	 * to query in Books.
	 * It will prompt the user for a string to query in the specified field.
	 * The string can be any string of words in the field.
	 * 
	 * If something is found, it simply prints the results. Otherwise,
	 * notifies of no results.
	 * 
	 * @param column
	 */
	@SuppressWarnings("null")
	public static void searchBy(String column) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String keywords, sort_in;
		int sort = 0;
		try
		{
			System.out.println("      -- Search By "+column+" --     "); //print title
			System.out.println("Please Enter words for the book "+column+":"); //prompt for input
			while ((keywords = in.readLine()) == null && keywords.length() == 0); //wait for input
			
			System.out.println("How would you like to sort the data?:");
			System.out.println("1: By Year.");
			System.out.println("2: By Average User Score.");
			System.out.println("3: By Average User Score from your trusted users.");
			while ((sort_in = in.readLine()) == null && sort_in.length() == 0); //wait for input on sort
			try {
				sort = Integer.parseInt(sort_in);
			}
			catch(Exception e){
				System.out.println("Invalid selection.");
			}
			Book book = new Book();
			ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>(); //arraylist to store the results
			switch(column.toLowerCase()) //switch on field
			{
			case "author":
				result = book.searchByAuthorTrusted(keywords, sort, User.trustedUsersSQL(login)); //search by author
				break;
			case "title":
				result = book.searchByTitleTrusted(keywords, sort, User.trustedUsersSQL(login)); //search by title
				break;
			case "genre":
				result = book.searchByGenreTrusted(keywords, sort, User.trustedUsersSQL(login));
				break;
			case "publisher":
				result = book.searchByPublisherTrusted(keywords, sort, User.trustedUsersSQL(login));
				break;
			}
			
			if(result.isEmpty()) //if no results, notify and return
			{
				System.out.println("Sorry, no results.");
			 	System.out.println("  --  Press Enter to continue --  "); 
			 	while(in.readLine() != null)
			 	{
			 		return;
			 	}
			}
			
			System.out.println(book.lastQueryToString());
			
			//Prompt to continue
			System.out.println("  --  Press Enter to continue --  "); 
			while(in.readLine() != null)
		 	{
		 		return;
		 	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean RegisterNewBookRun() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int c=0;
		try
		{
			while(true)
			{
				System.out.println("      -- Register New Book --     ");
				System.out.println("Enter the ISBN:");
				String isbn = "";
				while ((isbn = in.readLine()) == null && isbn.length() == 0);
				Pattern pattern = Pattern.compile("^[0-9]{9}$|^[0-9]{10}$|^[0-9]{13}$");
				Matcher m = pattern.matcher(isbn);
				if(!m.matches())
				{
					System.out.println("The ISBN should only consist of numbers and be of length 9, 10, 13");
					System.out.println("Try Again? (yes or no)");
					String temp = "";
					while ((temp = in.readLine()) == null && temp.length() == 0);
					if(temp.trim().contains("yes"))
					{
						continue;
					}
					return false;
				}
				Connector con = new Connector();
				Book b = new Book();
				if(!b.Query("ISBN", "Where ISBN='"+isbn.trim()+"'", "").isEmpty())
				{
					System.out.println("  ##  That book is already in the database.  ##  ");
					System.out.println(b.lastQueryToString());
					return false;
				}
//				PreparedStatement prep = con.con.prepareStatement("SELECT ISBN FROM Books WHERE ISBN=?");
//				prep.setString(1, isbn.trim());
//				ResultSet rs = prep.executeQuery();
//				ResultSetMetaData rsmd = rs.getMetaData();
//				int numCols = rsmd.getColumnCount();
//				boolean alreadyExists = false;
//				while(rs.next())
//				{
//					alreadyExists = true;
//					System.out.println("  ##  That book is already in the database.  ##  ");
//					 for (int i=1; i<=numCols;i++)
//						 System.out.print(rsmd.getColumnLabel(i)+":\t"+rs.getString(i)+"\t");
//					 System.out.println();
//				}
//				
//				if(alreadyExists)
//				{
//					rs.close();
//					prep.close();
//					con.closeConnection();
//					return false;
//				}
				System.out.println("Enter the Title:");
				String title = "";
				while ((title = in.readLine().trim()) == null && title.length() == 0);
				
				//Get the author(s)
				ArrayList<Author> authors = new ArrayList<Author>();
				while(true)
				{
					System.out.println("Enter the Author's First Name:");
					Author author = new Author();
					String first = "";
					while ((first = in.readLine()) == null && first.length() == 0);
					System.out.println("Enter the Author's Last Name:");
					String last = "";
					while ((last = in.readLine()) == null && last.length() == 0);
					author.first = first.trim();
					author.last = last.trim();
					authors.add(author);
					System.out.println("Enter another author? (yes or no)");
					String temp = "";
					while ((temp = in.readLine()) == null && temp.length() == 0);
					if(temp.trim().contains("yes"))
					{
						continue;
					}
					break;
				}
				
				System.out.println("Enter the Publisher:");
				String publisher = "";
				while ((publisher = in.readLine().trim()) == null && publisher.length() == 0);
				
				System.out.println("Enter the Year:");
				String year = "";
				while ((year = in.readLine().trim()) == null && year.length() == 0);
				
				System.out.println("Enter the Language:");
				String language = "";
				while ((language = in.readLine().trim()) == null && language.length() == 0);
				
				System.out.println("Enter the Format (soft/hard/audio):");
				String format = "";
				while ((format = in.readLine().trim()) == null && format.length() == 0);
				
				//PRICE
				String price = "";
				while(true)
				{
					boolean bad = false;
					System.out.println("Enter the Price:");
					String _price = "";
					while ((_price = in.readLine().trim()) == null && _price.length() == 0);
					pattern = Pattern.compile("^[0-9]*[.][0-9]{2}$");
					m = pattern.matcher(_price);
					if(!m.matches())
					{
						bad = true;
					}
					try {
						Double.parseDouble(_price);
					} catch(Exception e)
					{
						System.out.println("Please enter a valid dollar value, without the '$' symbol.");
						continue;
					}
					if(!bad)
					{
						price = _price;
						break;
					}
				}
				
				//COPIES
				int _copies = 0;
				String copies = "";
				while(true)
				{
					System.out.println("Enter the number of copies:");
					while ((copies = in.readLine().trim()) == null && copies.length() == 0);
					try {
						_copies = Integer.parseInt(copies);
					} catch(Exception e)
					{
						System.out.println("Please enter a valid integer value.");
						continue;
					}
					if(_copies < 0)
					{
						System.out.println("Please enter a positive (or 0) integer value.");
						continue;
					}
					copies = ""+_copies;
					break;
				}
				b = new Book(isbn, title, publisher, year, language, format, price, copies);
				HashMap<String, String> result = b.Insert();
				
				if(result != null)
				{
					for(Author a : authors)
					{
						if(a.Insert() != null)
						{
							Authored_By ab = new Authored_By(b.getISBN(), a.getAid());
							if(ab.Insert() != null)
							{
								return true;
							}
							System.out.println("There was an error adding the Author/ISBN pair to Authored_By");
							return true;
						}
						System.out.println("There was an error adding the Author to Authors");
						return true;
					}
					return true;
				}
				
			}
		 }
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
