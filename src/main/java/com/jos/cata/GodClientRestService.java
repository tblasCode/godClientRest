package com.jos.cata;

import java.util.List;
import java.util.stream.Collectors;

public class GodClientRestService {

	public static List<String> filterGods(List<String> gods) {
		
		return gods.stream().filter(data -> data.charAt(0) == 'n').collect(Collectors.toList());
	}
}
