package cs5530;

public class Manager extends User{
	
	public Manager() {
		table = "Users";
		columns.add("Login");
		columns.add("Password");
		columns.add("FirstName");
		columns.add("LastName");
		columns.add("Phone");
		columns.add("Address");
		columns.add("CCNum");
		columns.add("isManager");
		primaryKeyColumns.add("Login");
	}
	
	public Manager(String login, String password) {
		table = "Users";
		columns.add("Login");
		columns.add("Password");
		primaryKeyColumns.add("Login");
		
		colValPairs.put("Login", login);
		colValPairs.put("Password", password);
		colValPairs.put("isManager", "1");
//		this.login = colValPairs.get("Login");
	}
	
	public Manager(String login, String password, String first, String last, String phone, String address, String ccnum) {
		table = "Users";
		columns.add("Login");
		columns.add("Password");
		columns.add("FirstName");
		columns.add("LastName");
		columns.add("Phone");
		columns.add("Address");
		columns.add("CCNum");
		columns.add("isManager");
		primaryKeyColumns.add("Login");
		
		
		colValPairs.put("Login", login);
		colValPairs.put("Password", password);
		colValPairs.put("FirstName", first);
		colValPairs.put("LastName", last);
		colValPairs.put("Phone", phone);
		colValPairs.put("Address" , address);
		colValPairs.put("CCNum" , ccnum);
		colValPairs.put("isManager", "1");
	}

}
