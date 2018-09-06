package kz.astana.uvaissov.insta.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.entity.DicUrl;
import kz.astana.uvaissov.insta.entity.ProfileUrls;

@Service("localDataService")
public class LocalDataServiceImpl implements LocalDataService{
	
	@Autowired
	DicService dicService;
	
	protected HashMap<String,String> svgMap = new HashMap<String,String>();
	protected List<DicUrl> urls;
	
	@PostConstruct
	public void init() {
		getUrlDict();
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
	
	@Override
	public List<DicUrl> getUrls(){
		return this.urls;
	}
	
	private void getUrlDict() {
		urls = dicService.getUrls();
	}
	
    private void getSvgBackgoundInit() {
    	String folderPath = "static/assets/bg_svg";
    	ClassLoader classLoader = getClass().getClassLoader();
    	File folder = new File(classLoader.getResource(folderPath).getFile());
    	String[] list =  folder.list();
    	try { 
    	    for(String file : list) {
    	    	if(!file.toLowerCase().contains(".svg")) {
    	    		continue;
    	    	}
    	    	String body = IOUtils.toString(classLoader.getResourceAsStream(folderPath+"/"+file));
    	    	body = body.replaceAll("\r\n", "");
    	    	body = body.replaceAll("\r", "");
    	    	body = body.replaceAll("\n", "");
    	    	svgMap.put(file,body );
        	}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
      }

	@Override
	public DicUrl getUrl(Long url_id) {
		Optional<DicUrl> value = this.urls.stream().filter(a -> url_id.equals(a.getId()) ).findFirst();
		if(value!=null && value.isPresent()) {
			return value.get();
		}
		return null;
	}

}
