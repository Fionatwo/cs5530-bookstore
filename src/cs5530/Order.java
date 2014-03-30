package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Order{
	public Order() {
	}
	
	static String login = "";
	
	public static int orderMain(String user) {
		login = user;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String choice;
		int c=0;
		try
		{
			while(true)
			{
				orderMenu();
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
					/* ORDER BY TITLE */
					BooksConsole.searchBy("title");
					break;
				case 2:
					/* ORDER BY ISBN */
					break;
				case 3:
					/* ORDER BY AUTHOR */
				case 4:
					/* BACK TO MAIN MENU */
					return 4;
				case 5:
					/* LOG OUT AND GO TO WELCOME MENU  */
					return 5;
				case 99:
					/* RETURN MANAGER TO MANAGER MENU */
					return 99;
				default:
					System.out.println("Invalid selection. Try again.");
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
	
	private static void orderMenu() {
		System.out.println("  --  Place An Order  --  ");
		System.out.println("Select an option:");
		System.out.println("1: Order by Title.");
		System.out.println("2: Order by ISBN.");
		System.out.println("3: Order by Author.");
		System.out.println("4: Back to Main Menu.");
		System.out.println("5: Log out and go to welcome menu.");
		System.out.println("0: Log out and Exit.");
	}
	
	public String getOrders(String attrName, String attrValue, Statement stmt) throws Exception{
		String query;
		String resultstr="";
		ResultSet results; 
		query="Select * from orders where "+attrName+"='"+attrValue+"'";
		try{
			results = stmt.executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		System.out.println("Order:getOrders query="+query+"\n");
		while (results.next()){
			resultstr += "<b>"+results.getString("login")+"</b> purchased "+results.getInt("quantity") +
							" copies of &nbsp'<i>"+results.getString("title")+"'</i><BR>\n";	
		}
		return resultstr;
	}
}
