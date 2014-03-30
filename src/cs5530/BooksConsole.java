package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs5530.db.BooksDB;


/**
 * @author Alex Stout - u0583813
 * @for CS5530 - Databases
 * @organization University of Utah
 * @since Spring 2014
 *
 * The console interface with the BooksDB module.
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
					ArrayList<Book> result = BooksDB.listByTitle();
					if(result.isEmpty())
					{
						System.out.println("Sorry, no results");
					}
					Iterator<Book> iBook = result.iterator();
					while(iBook.hasNext())
					{
						Book b = iBook.next();
						System.out.println(b.toString(true));
					}
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
			ArrayList<Book> result = new ArrayList<Book>(); //arraylist to store the results
			switch(column.toLowerCase()) //switch on field
			{
			case "author":
//				result = BooksDB.searchByAuthor(keywords, sort); //search by author
				break;
			case "title":
				result = BooksDB.searchByTitle(keywords, sort, login); //search by title
				break;
			case "genre":
//				result = BooksDB.searchByGenre(keywords, sort); //search by genre
				break;
			case "publisher":
//				result = BooksDB.searchByPublisher(keywords, sort); //search by publisher
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
			//Print a tabified header for output
			String header = "-#-\t-ISBN-\t\t-Title-\t\t-Year-\t-Publisher-\t-Language-\t-Format-\t-Price-\t-Copies-\t-Avg Rating-";
			System.out.println(header);
			
			//Create iterator for book results
			Iterator<Book> iBooks = result.iterator();
			
			//Print all content of each book.
			int count = 0;
			while(iBooks.hasNext())
			{
				Book b = iBooks.next();
				System.out.println(count + "\t" + b.toString(true));
			}
			
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
				PreparedStatement prep = con.con.prepareStatement("SELECT ISBN FROM Books WHERE ISBN=?");
				prep.setString(1, isbn.trim());
				ResultSet rs = prep.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int numCols = rsmd.getColumnCount();
				boolean alreadyExists = false;
				while(rs.next())
				{
					alreadyExists = true;
					System.out.println("  ##  That book is already in the database.  ##  ");
					 for (int i=1; i<=numCols;i++)
						 System.out.print(rsmd.getColumnLabel(i)+":\t"+rs.getString(i)+"\t");
					 System.out.println();
				}
				
				if(alreadyExists)
				{
					rs.close();
					prep.close();
					con.closeConnection();
					return false;
				}
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
				double price = 0;
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
						price = Double.parseDouble(_price);
					} catch(Exception e)
					{
						System.out.println("Please enter a valid dollar value, without the '$' symbol.");
						continue;
					}
					if(!bad)
					{
						break;
					}
				}
				
				//COPIES
				int copies = 0;
				while(true)
				{
					System.out.println("Enter the number of copies:");
					String _copies = "";
					while ((_copies = in.readLine().trim()) == null && _copies.length() == 0);
					try {
						copies = Integer.parseInt(_copies);
					} catch(Exception e)
					{
						System.out.println("Please enter a valid integer value.");
						continue;
					}
					if(copies < 0)
					{
						System.out.println("Please enter a positive (or 0) integer value.");
						continue;
					}
					break;
				}
				
				boolean success = BooksDB.newBook(isbn, title, publisher, year, language, format, price, copies);
				
				if(success)
				{
					for(Author a : authors)
					{
						if(a.insertAuthor())
						{
							a.insertAuthorBookRelation(isbn);
						}
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
