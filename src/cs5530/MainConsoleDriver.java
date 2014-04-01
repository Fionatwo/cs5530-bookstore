package cs5530;


import java.sql.*;
import java.io.*;
import cs5530.db.UsersDB;


/**
 * 
 * @author Alex Stout - u0583813
 * @for CS5530 - University of Utah 
 * @prof Feifei Li
 *
 *
 * This is the console application interface for my bookstore database.
 * 
 */
public class MainConsoleDriver {
	
	static User user = new User();

	/**
	 * @param args
	 */
	public static void displayMenu()
	{
		System.out.println("    -=-=-  Stout Book Store Management System  -=-=-   ");
		System.out.println("Options:");
		System.out.println("1. Register New User:");
		System.out.println("2. login:");
		System.out.println("0. exit:");
		System.out.println("Select an option:");
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		frontMenu();
	}

	/**
	 * 
	 */
	@SuppressWarnings("null")
	private static void frontMenu() {
		String choice;
        int c=0;
        
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while(true)
			{
			 displayMenu();
			 while ((choice = in.readLine()) == null && choice.length() == 0);
			 try{
				 c = Integer.parseInt(choice);
			 }
			 catch (Exception e)
			 {
				 System.out.println("Invalid selection. Try again.");
				 continue;
			 }
			 boolean b = true;
			 switch(c)
			 {
			 case 0:
				 break;
			 case 1:
				 /* REGISTER NEW USER */
				 RegisterNewUser();
				 break;
			 case 2:
				 /* LOGIN */
				 if(Login())
				 {
					 if(user.Get("isManager"))
					 {
						 LoggedInRun_Manager();
					 }
					 LoggedInRun();
				 }
				 break;
			 default:
				 System.out.println("Invalid selection. Try again.");
				 b = false;
				 continue;
			 }
			 if(b)
			 {
				 break;
			 }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * The menu for the customer user once logged in.
	 */
	private static void LoggedInMenu() {
		System.out.println("  -- What's next? --  ");
		System.out.println("1: Browse Books. ");
		System.out.println("2: Place an order. ");
		System.out.println("3: Browse Reviews. ");
		System.out.println("4: Check Author Relations. ");
		System.out.println("5: Custom Query. ");
		System.out.println("6: Show My User Record.");
		System.out.println("7: Leave feedback for a book.");
		System.out.println("9: Log Out and go to Welcome Menu.");
		System.out.println("0: Log Out and Exit.");
	}
	
	/**
	 * This is the loop function that displays the LoggedInMenu
	 * and reads the users input.
	 */
	@SuppressWarnings("null")
	private static void LoggedInRun() {

		String choice;
        int c=0;
        
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while(true)
			{
			 LoggedInMenu();
			 while ((choice = in.readLine()) == null && choice.length() == 0);
			 try{
				 c = Integer.parseInt(choice);
			 }
			 catch (Exception e)
			 {
				 System.out.println("Invalid selection. Try again.");
				 continue;
			 }
			 switch(c)
			 {
			 case 0:
			 	 properExit();
				 break;
			 case 1:
				 /* BROWSE BOOKS */
				 BrowseBooksRun();
				 break;
			 case 2:
				 /* PLACE AN ORDER */
//				 PlaceOrder();
				 break;
			 case 3:
				 /* BROWSE REVIEWS */
				 break;
			 case 4:
			 	 /* CHECK AUTHOR RELATIONS */
			 	 break;
		 	 case 5:
			 	 /* CUSTOM QUERY */
		 		 CustomQuery();
			 	 break;
		 	 case 6:
			 	 /* SHOW USER RECORD */
			 	 break;
		 	 case 7:
			 	 /* LEAVE FEEDBACK */
			 	 break;
		 	 case 9:
			 	 /* LOG OUT */
			 	 break;
		 	 case 99:
		 	 	 /* RETURN TO MANAGER MENU */
		 	 	 if(user.isManager())
		 	 	 {
		 	 	 	LoggedInRun_Manager();
		 	 	 	break;
		 	 	 }
		 	 	 continue;
			 default:
				 System.out.println("Invalid selection. Try again.");
				 continue;
			 }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Prints the menu for a logged in manager.
	 */
	private static void LoggedInMenu_Manager() {
		System.out.println("  -- Manager Menu --  ");
		System.out.println("1: Record A New Book.");
		System.out.println("2: Inventory Arrival.");
		System.out.println("3: Check Statistics.");
		System.out.println("4: User Awards.");
		System.out.println("5: Custom Query.");
		System.out.println("6: Show My User Record.");
		System.out.println("7: Register another Manager User.");
		System.out.println("8: Go to Customer User Menu.");
		System.out.println("9: Log Out and go to Welcome Menu.");
		System.out.println("0: Log Out and Exit.");
	}
	
	/**
	* The running program that gets input from the user at the 
	* menu for a logged in manager.
	*/
	@SuppressWarnings("null")
	private static void LoggedInRun_Manager() {
		String choice;
        int c=0;
        
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while(true)
			{
			 LoggedInMenu_Manager();
			 while ((choice = in.readLine()) == null && choice.length() == 0);
			 try{
				 c = Integer.parseInt(choice);
			 }
			 catch (Exception e)
			 {
				 System.out.println("Invalid selection. Try again.");
				 continue;
			 }
			 switch(c)
			 {
			 case 0:
			 	 properExit();
				 break;
			 case 1:
				 /* RECORD A NEW BOOK */
				 BooksConsole.RegisterNewBookRun();
				 break;
			 case 2:
				 /* INVENTORY ARRIVAL */
				 break;
			 case 3:
			 	 /* CHECK STATISTICS */
			 	 break;
		 	 case 4:
			 	 /* USER AWARDS */
			 	 break;
		 	 case 5:
			 	 /* CUSTOM QUERY */
		 		 CustomQuery();
			 	 break;
		 	 case 6:
			 	 /* SHOW MY USER RECORD */
			 	 break;
		 	 case 7:
			 	 /* REGISTER ANOTHER MANAGER USER */
			 	 break;
		 	 case 8:
			 	 /* GO TO CUSTOMER MENU */
			 	 System.out.println(" #####  YOU MAY ENTER '99' AT ANY MENU PROMPT TO RETURN TO THE MANAGER MENU  ##### ");
			 	 System.out.println("  --  Press Enter to continue --  "); 
			 	 while ((choice = in.readLine()) == null && choice.length() == 0);
			 	 LoggedInRun();
			 	 break;
		 	 case 9:
			 	 frontMenu();
			 	 break;
			 default:
				 System.out.println("Invalid selection. Try again.");
				 continue;
			 }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	*	This version will call the BooksConsole interface
	*	Future versions will call the jsp/web interface.
	*/
	private static void BrowseBooksRun() {
		
		int c = BooksConsole.browseMain(user.Get("Login"));
		
		switch(c) 
		{
		case 0:
			properExit();
			break;
		case 10:
			CustomQuery();
			break;
		case 11:
			LoggedInRun();
			break;
		case 12:
			frontMenu();
			 break;
		case 99:
			LoggedInRun_Manager();
			break;
		default:
			BrowseBooksRun();
		}
		
		
	}
	
	
	/**
	 * 
	 * @param con
	 */
	@SuppressWarnings("null")
	public static void CustomQuery()
	{
		String sql;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			Connector con = new Connector();
			System.out.println("  -- Custom Query --  ");
   		 	while ((sql = in.readLine()) == null && sql.length() == 0)
				 System.out.println(sql);
			 ResultSet rs=con.stmt.executeQuery(sql);
			 ResultSetMetaData rsmd = rs.getMetaData();
			 int numCols = rsmd.getColumnCount();
			 int count = 0;
			 while (rs.next())
			 {
				 System.out.print("#"+(count)+"\t");
				 for (int i=1; i<=numCols;i++)
					 System.out.print(rsmd.getColumnLabel(i)+":\t"+rs.getString(i)+"\t");
				 String line = "= - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - ";
				 System.out.println("\n"+line+line);
				 count++;
			 }
			 System.out.println(" ");
			 rs.close();
			 con.closeConnection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
       	 	System.err.println ("Cannot connect to database server");
		}
	}
	
	/**
	 * Prompts the user for a user name and password.
	 * 
	 * If the user name already exists, the user is prompted to 
	 * try again or exit.
	 * Otherwise, they must enter matching passwords.
	 * If the passwords don't match, prompt to try again or exit.
	 * 
	 * On success, return true.
	 * 
	 * @return boolean - New user registered successfully
	 */
	@SuppressWarnings("null")
	public static boolean RegisterNewUser() {
        String uname, passwd, passwd1;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			 System.out.println("  -- Register New User --  ");
			 System.out.println("Please enter a username");
			 while ((uname = in.readLine()) == null && uname.length() == 0);
			 if(user.checkLoginExists())
			 {
				 System.out.println("  ##  Sorry, that user name is taken. Try another?  ##  ");
				 RegisterNewUserFailMenu();
			 }
			 System.out.println("Please enter a password:");
			 while ((passwd = in.readLine()) == null && passwd.length() == 0);
			 System.out.println("Re-enter password:");
			 while ((passwd1 = in.readLine()) == null && passwd1.length() == 0);
			 if(!passwd.equals(passwd1))
			 {
				 System.out.println("  ##  Your password entries didn't match  ##  ");
				 RegisterNewUserFailMenu();
			 }
			 user = new User(uname, passwd);
			 return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
       	 	return false;
		}
	}
		
	@SuppressWarnings("null")
	public static void RegisterNewUserFailMenu() {
		
		System.out.println("1: Yes, Try Again.");
		System.out.println("2: No, Main Menu.");
		System.out.println("0: No, exit completely.");
		
		String choice;
		int c;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			while(true)
			{
				while ((choice = in.readLine()) == null && choice.length() == 0);
				try{
					c = Integer.parseInt(choice);
				}
				catch (Exception e)
				{
					System.out.println("Invalid selection. Try again.");
					continue;
				}
				switch(c)
				{
				case 0:
					properExit();
					break;
				case 1:
					/* REGISTER NEW USER */
					RegisterNewUser();
					break;
				case 2:
					frontMenu();
					break;
				default:
					RegisterNewUser();
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("null")
	public static boolean Login() {
        String uname, passwd;
        int count = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			while(count < 3)
			{
				System.out.println("  -- Login --  ");
				System.out.println("Please enter your user name");
				while ((uname = in.readLine()) == null && uname.length() == 0);
				System.out.println("Please enter your password:");
				while ((passwd = in.readLine()) == null && passwd.length() == 0);
				if(user.Login(uname, passwd))
				{
					System.out.println("Welcome back " + uname + "!");
					return true;
				}
				System.out.println("  ## Incorrect user name or password ##  ");
				count++;
			}
			System.out.println("Limit of attempts reached.");
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
       	 	return false;
		}
	}
	
	public static void properExit() {
		System.out.println("Goodbye.");
		System.exit(0);
	}
}
