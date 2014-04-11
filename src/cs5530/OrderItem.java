package cs5530;


public class OrderItem extends DatabaseModel{

	public OrderItem() {
		table = "Order_Items";
		columns.add("oid");
		columns.add("ISBN");
		columns.add("count");
		columns.add("price");
		primaryKeyColumns.add("oid");
		primaryKeyColumns.add("ISBN");
	}

	public OrderItem(String isbn, String count, String price) {
		table = "Order_Items";
		columns.add("oid");
		columns.add("ISBN");
		columns.add("count");
		columns.add("price");
		primaryKeyColumns.add("oid");
		primaryKeyColumns.add("ISBN");
		
		colValPairs.put("ISBN", isbn);
		colValPairs.put("count", count);
		colValPairs.put("Price", price);
	}
}
