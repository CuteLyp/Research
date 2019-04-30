package Algorithm;

import java.util.*;

public class Solution1 {
	
	private static List<Order> sequence = new ArrayList<>();
	private static List<Double> distances = new ArrayList<>();
	
	private static int totalTime = 0;
	
	public static void main(String[] args) {
		String file = "D:\\Workspace\\Test2\\src\\Algorithm\\address.txt";
		IO io = new IO();
		List<Order> orders = io.loadingData(file);
		
		double startLat = 53.38195;
		double startLng = -6.59192;
		Order start = new Order(0, "0", "8:00 PM", startLng, startLat);
		//start 53.38195  -6.59192
		double speed = 22.222;   //  22.22m/s
		String currentTime = "8:00:00";
		int count = 0;
		
		while(orders.size() > 0) {
			Order next = findNearest(orders, start);
			//System.out.println(next.id + " " + next.address + " " + next.time);
			double distance = getDistance(start.latitude, start.longitude, next.latitude, next.longitude);
			double seconds = distance / speed;
			double minutes = 0;
			if(seconds >= 60) {
				minutes = seconds / 60;
				seconds %= 60;
			}
			currentTime = addTime(currentTime, (int)minutes, (int)seconds);
			//System.out.println(currentTime);
			next.deliveryTime = currentTime;
			if(isOnTime(currentTime, addTime(next.time, 30, 0))) {
				count++;
				//System.out.println(next.id + " " + currentTime);
			}
			start = next;
		}
		for(Order t : sequence) {
			System.out.println(t.id + "  - " + t.address + " - " + t.latitude + " " + t.longitude);
		}
		System.out.println("On time rate: " + (double)count/100);
	}
	
	public static Order findNearest(List<Order> orders, Order start) {
		double minDistance = Double.MAX_VALUE;
		Order current = null;
		for(Order temp : orders) {
			double i = getDistance(start.latitude, start.longitude, temp.latitude, temp.longitude);
			if(i < minDistance) {
				minDistance = i;
				current = temp;
			}
		}
		sequence.add(current);
		//totalTime += minDistance;
		//time = distance / speed
		orders.remove(current);
		return current;
	}
	
	private static double EARTH_RADIUS = 6378.137;  
    private static double rad(double d) {  
        return d * Math.PI / 180.0;  
    }
    
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {  
		double radLat1 = rad(lat1);  
		double radLat2 = rad(lat2);  
		double a = radLat1 - radLat2;  
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));  
		s = s * EARTH_RADIUS;  
		s = Math.round(s * 10000d) / 10000d;
		s = s*1000;  
		return s;  
	}
	
	public static String addTime(String s1, int minutes, int seconds) {
		//8:00:00   3:40
		if(s1.contains(" PM")) {
			s1 = s1.substring(0, s1.length()-3);
		}
		String[] a = s1.split(":");
		int carry = 0;
		String s = "";
		for(int i = a.length-1; i >= 0; i--) {
			int curNum = Integer.parseInt(a[i]);
			int temp = 0;
			if(i == 2) {
				temp = curNum + seconds;
				if(temp > 59) {
					carry = 1;
					temp %= 60;
				}
			} else if(i == 1) {
				temp = curNum + minutes + carry;
				if(temp > 59) {
					temp %= 60;
					carry = 1;
				} else {
					carry = 0;
				}
			} else if(i == 0) {
				temp = curNum + carry;
			}
			if(i != 0) {
				s += temp + ":";
			} else {
				s += temp;
			}
		}
		a = s.split(":");
		s = "";
		for(int i = a.length-1; i >= 0; i--) {
			if(i != 0) {
				s += a[i] + ":";
			} else {
				s += a[i];
			}
		}
		return s;
	}
	
	public static boolean isOnTime(String s1, String s2) {
		//s1 current   s2 planned
		if(s1.contains(" PM")) {
			s1 = s1.substring(0, s1.length()-3);
		}
		if(s2.contains(" PM")) {
			s2 = s2.substring(0, s2.length()-3);
		}
		String[] a1 = s1.split(":");
		String[] a2 = s2.split(":");
		for (int i = 0; i < a2.length; i++) {
			if (Integer.valueOf(a1[i]) < Integer.valueOf(a2[i])) {
				return true;
			} else if (Integer.valueOf(a1[i]) > Integer.valueOf(a2[i])) {
				return false;
			}
		}
		return true;
	}
}
