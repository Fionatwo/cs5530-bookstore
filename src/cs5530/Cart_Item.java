/**
 * 
 */
package cs5530;


/**
 * @author Alex Stout - u0583813
 *
 */
public class Cart_Item extends DatabaseModel{
	
	/**
	 * empty constructor.  Declares table name, registers column names and the primary key.
	 */
	public Cart_Item() {
		table = "Cart_Items";
		columns.add("Login");
		columns.add("ISBN");
		columns.add("Count");
		primaryKeyColumns.add("Login");
		primaryKeyColumns.add("ISBN");
	}
	
	/**
	 * Declares table name, registers column names and the primary key.
	 * Sets the login, isbn, and count by the by the given strings.
	 * 
	 * @param genre
	 */
	public Cart_Item(String login, String isbn, String count) {
		table = "Cart_Items";
		columns.add("Login");
		columns.add("ISBN");
		columns.add("Count");
		primaryKeyColumns.add("Login");
		primaryKeyColumns.add("ISBN");
		colValPairs.put("Login", login);
		colValPairs.put("ISBN", isbn);
		colValPairs.put("Count", count);
	}
}
