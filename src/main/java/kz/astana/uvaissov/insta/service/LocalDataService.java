package kz.astana.uvaissov.insta.service;

import java.util.HashMap;
import java.util.List;

import kz.astana.uvaissov.insta.entity.DicUrl;

public interface LocalDataService {
	
	String getSvgBackgroundByName(String name);

	HashMap<String, String> getMapSvgBackground();

	List<DicUrl> getUrls();
}
