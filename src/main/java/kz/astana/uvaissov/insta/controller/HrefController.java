package kz.astana.uvaissov.insta.controller;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import kz.astana.uvaissov.insta.entity.LogAction;
import kz.astana.uvaissov.insta.repository.GsonHttp;
import kz.astana.uvaissov.insta.service.LogService;
import kz.astana.uvaissov.insta.util.EncryptionUtil;


@Controller
@SessionAttributes({"user"})
@RequestMapping("/a")
public class HrefController {

	@Autowired
	private LogService logService;
	@Autowired
	private GsonHttp gson;
	

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET,path = "/{url:.+}")
    public RedirectView profile(Device device,@PathVariable String url) {
    	RedirectView redirectView = new RedirectView();
    	//parse json
    	String json = EncryptionUtil.decode(url);
    	Map<String,String> map = new HashMap<String,String>();
    	map = gson.getGson().fromJson(json, map.getClass());
    	
    	System.out.println(json);
    	System.out.println(map);
    	
    	String urlId = map.get("id");
    	String profileId = map.get("profileId");
    	
    	CompletableFuture<Void> future = CompletableFuture
    	        .runAsync(() -> logAction(Long.valueOf(urlId), Long.valueOf(profileId), device), Executors.newCachedThreadPool());
    	System.out.println(future.isDone());
    	redirectView.setUrl(map.get("url").toString());
		return redirectView;
    }
	
	@Transactional
	private void logAction(Long urlId,Long profileId,Device device) {
    	LogAction action = new LogAction();
    	action.setAction_type(2);//redirect
    	action.setUrlId(urlId);
    	action.setProfileInfoId(profileId);
    	action.setMobile(device.isMobile());
    	action.setMobileOs(device.getDevicePlatform().name());
    	action.setActionDatetime(new Timestamp(System.currentTimeMillis()));
    	logService.save(action);
	}
}
