package cs5530.db;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import cs5530.Connector;


/**
 * 
 * @author Alex Stout
 * @for University of Utah - CS5530 - Database Systems
 *
 * This is the Customer Class
 */
public class UsersDB {
	
	String table = "Users";

	public String login;

	public String passwd;

	public String first;

	public String last;

	public String phone;

	public String address;

	public String ccnum;
	
	public boolean isManager = false;
	
	public UsersDB(){}
	
	public UsersDB(String login, String passwd) throws Exception{
		String sql="insert into "+table+" (Login, Password) VALUES (?,?)";
	 	System.out.println("executing "+sql);
	 	
	 	try{
	 		Connector con = new Connector();
	 		PreparedStatement prep = con.con.prepareStatement(sql);
	 		prep.setString(1, login);
	 		prep.setString(2, passwd);
	 		prep.executeUpdate();
	 		this.login = login;
	 		this.passwd = passwd;
	 		prep.close();
	 		con.closeConnection();
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("That username is already taken.");
	 	}
	}
	
	public UsersDB(String login, String passwd, String first, String last, String phone, String address, String ccnum) throws Exception {
//		String sql="insert into " + table +" (Login, Password, FirstName, LastName, Phone, Address, CCNum) VALUES ('"+login+"','"+passwd+"','"+first+"','"+last+"',";
//		sql += "'"+phone+"','"+address+"','"+ccnum+"')";
		String sql="insert into " + table +" (Login, Password, FirstName, LastName, Phone, Address, CCNum) VALUES (?,?,?,?,?,?,?,?)";
	 	System.out.println("executing "+sql);
	 	Connector con;
	 	try{
	 		con = new Connector();
	 		PreparedStatement prep = con.con.prepareStatement(sql);
	 		prep.setString(1, login);
	 		prep.setString(2, passwd);
	 		prep.setString(3, first);
	 		prep.setString(4, last);
	 		prep.setString(5, phone);
	 		prep.setString(6, address);
	 		prep.setString(7, ccnum);
	 		prep.setInt(8, 0);
	 		prep.executeUpdate();
	 		prep.close();
	 		this.login = login;
	 		this.passwd = passwd;
	 		this.first = first;
	 		this.last = last;
	 		this.phone = phone;
	 		this.address = address;
	 		this.ccnum = ccnum;
	 	}
	 	catch(Exception e)
	 	{
	 		System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
    		System.err.println(e.getMessage());
	 	}
	}
	
	public boolean checkLoginExists(String login) {
		String sql = "SELECT Login FROM "+table+" WHERE Login=?";
	 	System.out.println("executing "+sql);
		try
		{
			Connector con = new Connector();
			PreparedStatement prep = con.con.prepareStatement(sql);
	 		prep.setString(1, login);
	 		ResultSet rs = prep.executeQuery();
	 		con.closeConnection();
	 		prep.close();
			String _login = null;
			while(rs.next())
			{
				_login = rs.getString("Login");
			}
			if(_login != null)
			{
				rs.close();
				return true;
			}
			rs.close();
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
       	 	return false;
		}
	}

	public boolean VerifyLogin(String login, String passwd) {
//		String sql = "SELECT Login, Password FROM "+table+" WHERE Login='"+login+"'";
		String sql = "SELECT Login, Password, isManager FROM "+table+" WHERE Login=?";
	 	System.out.println("executing "+sql);
		try
		{
			Connector con = new Connector();
			PreparedStatement prep = con.con.prepareStatement(sql);
	 		prep.setString(1, login);
//			ResultSet rs = con.stmt.executeQuery(sql);
	 		ResultSet rs = prep.executeQuery();
//	 		prep.ex
			String _passwd = null;
//			boolean _isManager = false;
			while(rs.next())
			{
				_passwd = rs.getString("Password");
				this.isManager = rs.getBoolean("isManager");
			}

			if(!passwd.equals(_passwd))
			{
				return false;
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
       	 	return false;
		}
    }

	public Map<String, String> getFullRecord(String login) {
		Map<String, String> record = new HashMap<String, String>();
		Map<String, String> pData = getPeronalData(login);
		
		record.put("login", "alex");
		record.put("passwd", "cheese");
		record.put("", "");
		
		return record;
//		getPersonalData();
//		getSalesHistory();
//		getFeedbackHistory();
//		getFeedbackRatings();
//		getListOfTrustees();
	}

	public Map<String, String> getPeronalData(String login) {
		Map<String, String> data = new HashMap<String, String>();
		String sql = "SELECT * FROM "+table+" WHERE Login='"+login+"'";
		
		System.out.println("executing "+sql);
		try
		{
			Connector con = new Connector();
			ResultSet rs = con.stmt.executeQuery(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
       	 	System.err.println ("Cannot connect to database server");
		}
		
		
		return data;
	}
	
}
