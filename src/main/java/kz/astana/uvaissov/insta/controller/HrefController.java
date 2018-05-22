package kz.astana.uvaissov.insta.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.repository.GsonHttp;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.UserService;
import kz.astana.uvaissov.insta.util.EncryptionUtil;
import kz.astana.uvaissov.insta.util.SitePreference;


@Controller
@SessionAttributes({"user"})
@RequestMapping("/a")
public class HrefController {

	@Autowired
	private UserService userService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private GsonHttp gson;
	

	@RequestMapping(method = RequestMethod.GET,path = "/{url:.+}")
    public RedirectView profile(Device device,@PathVariable String url) {
    	RedirectView redirectView = new RedirectView();
    	//parse json
    	String json = EncryptionUtil.decode(url);
    	Map<String,Object> map = new HashMap<String,Object>();
    	map = gson.getGson().fromJson(json, map.getClass());
    	
    	//check device
    	System.out.println("isMobile:"+ device.isMobile());
    	
    	redirectView.setUrl(map.get("url").toString());
		return redirectView;
    }
   
}
