package kz.astana.uvaissov.insta.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import kz.astana.uvaissov.insta.cabinet.constant.Backgrounds;
import kz.astana.uvaissov.insta.cabinet.model.BackgroundItem;
import kz.astana.uvaissov.insta.cabinet.model.ButtonContainer;
import kz.astana.uvaissov.insta.cabinet.model.NavItem;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.UrlAction;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.repository.GsonHttp;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.LogService;
import kz.astana.uvaissov.insta.service.UserService;
import kz.astana.uvaissov.insta.util.EncryptionUtil;
import kz.astana.uvaissov.insta.util.SitePreference;


@Controller
@SessionAttributes({"user"})
@RequestMapping("/a")
public class HrefController {

	@Autowired
	private InfoService infoService;
	@Autowired
	private LogService logService;
	@Autowired
	private GsonHttp gson;
	

	@RequestMapping(method = RequestMethod.GET,path = "/{url:.+}")
    public RedirectView profile(Device device,@PathVariable String url) {
    	RedirectView redirectView = new RedirectView();
    	//parse json
    	String json = EncryptionUtil.decode(url);
    	Map<String,String> map = new HashMap<String,String>();
    	map = gson.getGson().fromJson(json, map.getClass());
    	
    	System.out.println(json);
    	System.out.println(map);
    	
    	String urlId = (String) map.get("id");
    	String profileId = (String) map.get("profileId");
    	
    	CompletableFuture<Void> future = CompletableFuture
    	        .runAsync(() -> logAction(Long.valueOf(urlId), Long.valueOf(profileId), device), Executors.newCachedThreadPool());
    	System.out.println(future.isDone());
//    	logAction(Long.valueOf(urlId), Long.valueOf(profileId), device);
    	redirectView.setUrl(map.get("url").toString());
		return redirectView;
    }
	
	@Transactional
	private void logAction(Long urlId,Long profileId,Device device) {
    	UrlAction action = new UrlAction();
    	action.setUrlId(urlId);
    	action.setProfileInfoId(profileId);
    	action.setMobile(device.isMobile());
    	action.setMobileOs(device.getDevicePlatform().name());
    	action.setActionDatetime(new Timestamp(System.currentTimeMillis()));
    	logService.save(action);
	}
}
