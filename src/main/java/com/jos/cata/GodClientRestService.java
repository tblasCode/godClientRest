package com.jos.cata;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GodClientRestService {
	
	private GodClientRestService() {
	    throw new IllegalStateException("Utility class");
	  }

	public static List<String> filterGods(List<String> gods) {
		return gods.stream().filter(data -> data.charAt(0) == 'N').collect(Collectors.toList());
	}
	
	public static String convertNameToDecimal(String godName) {
		return godName.chars().mapToObj(data -> {
			return String.format("%03d", data);
		}).collect(Collectors.joining());
	}
	
	public static int sumDecimalName(String nameDecimal) {
		return Arrays.asList(nameDecimal.split("(?<=\\G.{3})")).stream().mapToInt(i -> Integer.parseInt(i)).sum(); 
	}
}
