/**
 * 
 */
package cs5530;

import cs5530.db.DatabaseModel;

/**
 * @author Alex Stout - u0583813
 *
 */
public class Is_Genre extends DatabaseModel{

	/**
	 * empty constructor.  Declares table name, registers column names and the primary key.
	 */
	public Is_Genre() {
		table = "Is_Genre";
		columns.add("ISBN");
		columns.add("gid");
		primaryKeyColumns.add("ISBN");
		primaryKeyColumns.add("gid");
	}
	
	/**
	 * Declares table name, registers column names and the primary key.
	 * Sets the genre by the given string.
	 * 
	 * @param genre
	 */
	public Is_Genre(String isbn, String gid) {
		table = "Is_Genre";
		columns.add("ISBN");
		columns.add("gid");
		colValPairs.put("ISBN", isbn);
		colValPairs.put("gid", gid);
		primaryKeyColumns.add("ISBN");
		primaryKeyColumns.add("gid");
	}
}
