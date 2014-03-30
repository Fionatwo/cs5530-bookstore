package cs5530;

import java.util.*;

import cs5530.db.DatabaseModel;
import cs5530.db.UsersDB;

public class testdriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
//			Connector con= new Connector();
////			Order order= new Order();
//			
////			String result=order.getOrders("login", "user1", con.stmt);
////			System.out.println(result);
//			
//			new UsersDB("batman", "batcave", "bruce", "wayne", "5552286267", "123 Wayne Way", "1111222233334444");
//			con.closeConnection();
//			System.out.println("Hello");
//			
//			ArrayList<String> cols = new ArrayList<String>();
//			cols.add("ISBN");
//			cols.add("Title");
//			ArrayList<String> vals = new ArrayList<String>();
//			vals.add("983012013574");
//			vals.add("Eight Crazy Nights");
//			DatabaseModel db = new DatabaseModel("Books", cols, vals);
			
			
			AuthorInsert();
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void AuthorInsert() {
		Author a = new Author("Guillermo", "Del Toro");
		System.out.println(a.Insert());
	}
	
	public static void AuthorQuery() {
		Author a = new Author();
		ArrayList<HashMap<String, String>> r = a.Query("*", "where FirstName like 'Herman'");
		Iterator<HashMap<String, String>> i = r.iterator();
		int count = 1;
		while(i.hasNext())
		{
			HashMap<String, String> row = i.next();
			System.out.println("Result " + count + ":");
			Iterator<String> iCol = row.keySet().iterator();
			while(iCol.hasNext())
			{
				String col = iCol.next();
				System.out.println(col + ": " + row.get(col));
			}
		}
	}

}
