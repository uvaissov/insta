package kz.astana.uvaissov.insta.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Service("localDataService")
public class LocalDataServiceImpl implements LocalDataService{
	
	protected HashMap<String,String> svgMap = new HashMap<String,String>();
	
	@PostConstruct
	public void init() {
		getSvgBackgoundInit();
	}
	
	@Override
	public String getSvgBackgroundByName(String name) {
		return svgMap.get(name);
	}
	@Override
	public HashMap<String,String> getMapSvgBackground() {
		return svgMap;
	}
	
    private void getSvgBackgoundInit() {
    	String folderPath = "static/assets/bg_svg";
    	ClassLoader classLoader = getClass().getClassLoader();
    	File folder = new File(classLoader.getResource(folderPath).getFile());
    	String[] list =  folder.list();
    	try {
    	    for(String file : list) {
    	    	svgMap.put(file, IOUtils.toString(classLoader.getResourceAsStream(folderPath+"/"+file)));
        	}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
      }

}
