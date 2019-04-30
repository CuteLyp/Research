package Algorithm;

public class Order {
	int id;
	String address;
	String time;
	double longitude;
	double latitude;
	String deliveryTime;
	public Order(int id, String address, String time, double longitude, double latitude) {
		this.id = id;
		this.address = address;
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}