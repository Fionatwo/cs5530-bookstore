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
			
			
//			AuthorGetBooksBy();
//			searchByAuthor();
			uniqueLogin();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/* * * * * * * * * * * * * * * * * AUTHORS * * * * * * * * * * * * * * * */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public static void AuthorInsert() {
		Author a = new Author("Yann", "Martel");
		System.out.println(a.Insert());
	}
	
	public static void AuthorQuery() {
		Author a = new Author();
		HashSet<String> cols = new HashSet<String>();
		cols.add("Title");
		a.Query("*", "where FirstName like '%Herman%'", a.generateOrderClause(cols));
		System.out.println(a.lastQueryToString());
		
		a.Query("*", "where FirstName like '%Herman%'", a.generateOrderClause("Title"));
		System.out.println(a.lastQueryToString());
	}
	
	public static void AuthorGetBooksBy() {
		Author a = new Author("J.K.", "Rowling");
		a.getBooksBy();
		System.out.println(a.lastQueryToString());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/* * * * * * * * * * * * * * * * * BOOKS * * * * * * * * * * * * * * * */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public static void BookInsert() {
		Book b = new Book("9780156027328", "Life of Pi", "Houghton Mifflin Harcourt", "2003", "English", "soft", "12.74", "60");
		System.out.println(b.Insert());
	}
	
	public static void BookQuery() {
		Book b = new Book();
		b.Query("*", "where Title like '%the%'");
		System.out.println(b.lastQueryToString());
	}
	
	public static void searchByAuthor() {
		Book b = new Book();
		b.searchByAuthor("Collins Suz", 0);
		System.out.println(b.lastQueryToString());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/* * * * * * * * * * * * * * * * * AUTHORED BY * * * * * * * * * * * * * */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public static void AuthoredByInsert() {
		Authored_By ab = new Authored_By("9780156027328", "17");
		System.out.println(ab.Insert());
	}
	
	public static void AuthoredByQuery() {
		Book b = new Book();
		ArrayList<HashMap<String, String>> r = b.Query("*", "where Title like '%the%'");
		System.out.println(b.lastQueryToString());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/* * * * * * * * * * * * * * * * * USER * * *  * * * * * * * * * * * * * */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public static void uniqueLogin() {
		User u = new User("alex", "");
		System.out.println(u.checkLoginExists());
	}
}
