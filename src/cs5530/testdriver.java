package cs5530;

import java.util.*;

import cs5530.db.UsersDB;

public class testdriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Connector con= new Connector();
//			Order order= new Order();
			
//			String result=order.getOrders("login", "user1", con.stmt);
//			System.out.println(result);
			
			new UsersDB("batman", "batcave", "bruce", "wayne", "5552286267", "123 Wayne Way", "1111222233334444");
			con.closeConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
