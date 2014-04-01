package cs5530;

import cs5530.db.DatabaseModel;

public class User extends DatabaseModel{
	
//	String login, first, last, phone, address, ccnum;
	
	public User() {
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
	
	public User(String login, String password) {
		table = "Users";
		columns.add("Login");
		columns.add("Password");
		primaryKeyColumns.add("Login");
		
		colValPairs.put("Login", login);
		colValPairs.put("Password", password);
		colValPairs.put("isManager", "0");
//		this.login = colValPairs.get("Login");
	}
	
	public User(String login, String password, String first, String last, String phone, String address, String ccnum) {
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
		colValPairs.put("isManager", "0");
	}
	
	public boolean isManager() {
		try 
		{
			String m = colValPairs.get("isManager");
			Boolean.parseBoolean(m);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean checkLoginExists() {
		return !this.Query("Login", "where Login='"+colValPairs.get("Login")+"'", "").isEmpty();
	}
	
	private String trustedUsersSQL() {
		//select r.ISBN, AVG(r.Score) as TrustRating from Reviews r join Trusts t on 
		//r.Login=t.Trustee and t.Assessment=true and t.Truster=$USER group by r.ISBN
		String trustedRatings = "select r.ISBN, AVG(r.Score) as TrustRating from Reviews r join Trusts t ";
		trustedRatings += "on r.Login=t.Trustee and t.Assessment=true and t.Truster=";
		trustedRatings += "'"+colValPairs.get("Login")+"' group by r.ISBN";
		return trustedRatings;
	}
	
	public static String trustedUsersSQL(String login) {
		//select r.ISBN, AVG(r.Score) as TrustRating from Reviews r join Trusts t on 
		//r.Login=t.Trustee and t.Assessment=true and t.Truster=$USER group by r.ISBN
		String trustedRatings = "select r.ISBN, AVG(r.Score) as TrustRating from Reviews r join Trusts t ";
		trustedRatings += "on r.Login=t.Trustee and t.Assessment=true and t.Truster=";
		trustedRatings += "'"+login+"' group by r.ISBN";
		return trustedRatings;
	}
}
