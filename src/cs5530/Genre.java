/**
 * 
 */
package cs5530;


/**
 * @author Alex Stout - u0583813
 *
 */
public class Genre extends DatabaseModel{
	
	/**
	 * empty constructor.  Declares table name, registers column names and the primary key.
	 */
	public Genre() {
		table = "Genres";
		columns.add("gid");
		columns.add("Genre");
		primaryKeyColumns.add("gid");
	}
	
	/**
	 * Declares table name, registers column names and the primary key.
	 * Sets the genre by the given string.
	 * 
	 * @param genre
	 */
	public Genre(String genre) {
		table = "Genre";
		columns.add("gid");
		columns.add("Genre");
		colValPairs.put("Genre", genre);
		primaryKeyColumns.add("gid");
	}
}
