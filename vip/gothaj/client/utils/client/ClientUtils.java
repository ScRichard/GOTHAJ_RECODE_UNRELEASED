package vip.gothaj.client.utils.client;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import vip.gothaj.client.utils.Wrapper;

public class ClientUtils extends Wrapper{

	private static String version = "3.5 Recode";
	
	private static boolean ClNames = true;

	public static String getVersion() {
		return version;
	}
	
	public static String getName(String name) {
		return name;
	}
	
	public static int getDiffBetweenDates(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, formatter);
		
		return 0;
	}
	
}
