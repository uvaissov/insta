package kz.astana.uvaissov.insta.controller;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import kz.astana.uvaissov.insta.cabinet.constant.Backgrounds;
import kz.astana.uvaissov.insta.cabinet.model.ActiveSession;
import kz.astana.uvaissov.insta.cabinet.model.AnalyticModel;
import kz.astana.uvaissov.insta.cabinet.model.BackgroundItem;
import kz.astana.uvaissov.insta.cabinet.model.NavItem;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.AnalyticsService;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.UserService;


@Controller
@SessionAttributes({"user","userSession"})
@RequestMapping("/cabinet")
public class CabinetController {

	@Autowired
	private UserService userService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private AnalyticsService analytics;
	
	@ModelAttribute("user")//Обьявим основной аттрибут пользователя
	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
	}
	
	@ModelAttribute("userSession")//Обьявим обьект сессии пользователя
	public ActiveSession getSession(@ModelAttribute("user") User user) {
		ActiveSession session = new ActiveSession();
		System.out.println("user.getProfile_info_id():"+user.getProfile_info_id());
		session.profileId =user.getProfile_info_id();
		session.userName = user.getAccount_name();
		return session;
	}


    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView workspace(Model model,@ModelAttribute("user") User user,@ModelAttribute("userSession") ActiveSession session,Device device) {
    	ModelAndView modelAndView = new ModelAndView();
    	if(user==null || user.getId()==null) {
    		modelAndView.setViewName("login");
    		return modelAndView;
    	}
    	
    	ProfileInfo profileInfo = infoService.findByInfoId(user.getProfile_info_id());
    	if(profileInfo!=null) {
    		System.out.println(profileInfo);
    		modelAndView.addObject("brand", "My Brand");
    		modelAndView.addObject("username", user.getAccount_name());
    		session.logoUrl = profileInfo.getLogo_url();
    		session.backgroundUrl= profileInfo.getBackground_url();
    	}
    	//Device
    	modelAndView.addObject("isMobile",device.isMobile());
    	modelAndView.addObject("isTablet",device.isTablet());
    	modelAndView.addObject("isNormal",device.isNormal());
    	
    	//Вкладки
    	List<NavItem> navItems = new ArrayList<NavItem>();
    	navItems.add(new NavItem("Профайл", "primary", true,"left"));
    	navItems.add(new NavItem("Ссылки", "links", false,"left"));
    	navItems.add(new NavItem("Аналитика", "analytics", false,"left"));
    	modelAndView.addObject("navItems",navItems);
    	
    	//Список фонов
    	List<BackgroundItem> backItems = new ArrayList<BackgroundItem>();
    	for(String name : Backgrounds.list) {
    		backItems.add(new BackgroundItem(name, profileInfo!=null? name.equals(profileInfo.getBackground()) : false));
    	}
    	modelAndView.addObject("backItems",backItems);
		modelAndView.setViewName("/cabinet/index");
		return modelAndView;
    }
    
    @RequestMapping("/container/primary")
    public ModelAndView primary(@ModelAttribute("userSession") ActiveSession session){
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("logoUrl", session.logoUrl);
    	if(session.backgroundUrl!=null) {
    		modelAndView.addObject("backgroundUrl", session.backgroundUrl.split("\\.")[0]+"_thumb."+session.backgroundUrl.split("\\.")[1]);
    	}
    	String userName = session.userName;
    	if(session.logoUrl==null && userName!=null && userName.length()>1) {
    		modelAndView.addObject("firstLetter",userName.substring(0, 1).toUpperCase());
    		modelAndView.addObject("secondLetter",userName.substring(1, 2).toUpperCase());
    	}
    	
    	modelAndView.setViewName("/cabinet/container/primary");
		return modelAndView;
    }
    
    @RequestMapping("/container/links")
    public String links(@ModelAttribute("userSession") ActiveSession session){
    	return "/cabinet/container/links";
    }
    @RequestMapping("/container/analytics")
    public ModelAndView analytics(@ModelAttribute("user") User user,@ModelAttribute("userSession") ActiveSession session){
    	ModelAndView modelAndView = new ModelAndView();
    	
    	List<Object[]> list = analytics.getShort(user.getProfile_info_id());
    	AnalyticModel model = new AnalyticModel();
    	for(Object[] row : list) {
    		String time = (String) row[0];
    		BigInteger count = (BigInteger) row[1];
    		switch (time) {
			case "total":
				model.setTotalRedirect(count.intValue());
				break;
			case "week":
				model.setWeekRedirect(count.intValue());
				break;
			case "day":
				model.setDayRedirect(count.intValue());
				break;

			default:
				break;
			}
    	}
    	modelAndView.addObject("short",model);
    	modelAndView.setViewName("/cabinet/container/analytics");
		return modelAndView;
    }
    
    @RequestMapping("/container/setting")
    public String setting(){
    	return "redirect:/setting.do";
    }
   
}
