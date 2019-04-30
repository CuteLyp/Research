package Algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IO {
	public IO () {}
	public List<Order> loadingData(String file) {
		File aFile = new File(file);
		BufferedReader input = null;
		List<Order> orders = new ArrayList<>();
		try {
			input = new BufferedReader( new FileReader(aFile) );
			String line = null; 
			while (( line = input.readLine()) != null){
				String[] arr = line.split("  ");
				int id = Integer.parseInt(arr[0]);
				String addr = arr[1];
				String time = arr[2];
				double latitude = Double.valueOf(arr[3]);
				double longitude = Double.valueOf(arr[4]);
				Order o = new Order(id, addr, time, longitude, latitude);
				orders.add(o);
			}
		}
		catch (FileNotFoundException ex) {
			System.out.println("Can't find the file - are you sure the file is in this location: " + file);
			ex.printStackTrace();
		}
		catch (IOException ex){
			System.out.println("Input output exception while processing file");
			ex.printStackTrace();
		}
		finally {
			try {
				if (input!= null) {
					input.close();
				}
			}
			catch (IOException ex) {
				System.out.println("Input output exception while processing file");
				ex.printStackTrace();
			}
		}
		return orders;
	}
}
