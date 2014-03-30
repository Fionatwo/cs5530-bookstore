package cs5530;

import cs5530.db.DatabaseModel;

public class Authored_By extends DatabaseModel {
	public String first;
	public String last;
	
	/**
	 * empty constructor
	 */
	public Authored_By(){
		table = "Authored_By";
		columns.add("ISBN");
		columns.add("aid");
		keyColumn = "aid";
	}
}
