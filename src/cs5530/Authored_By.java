package cs5530;

import cs5530.db.DatabaseModel;

public class Authored_By extends DatabaseModel {
	public String isbn;
	public String aid;
	
	/**
	 * empty constructor
	 */
	public Authored_By(){
		table = "Authored_By";
		columns.add("ISBN");
		columns.add("aid");
		primaryKeyColumns.addAll(columns);
	}
	
	public Authored_By(String isbn, String aid){
		this.isbn = isbn;
		this.aid = aid;
		table = "Authored_By";
		columns.add("ISBN");
		columns.add("aid");
		colValPairs.put("ISBN", isbn);
		colValPairs.put("aid", aid);
		primaryKeyColumns.addAll(columns);
	}
	
}
