package kz.astana.uvaissov.insta.service;

import java.util.HashMap;

public interface LocalDataService {
	
	String getSvgBackgroundByName(String name);

	HashMap<String, String> getMapSvgBackground();
}
