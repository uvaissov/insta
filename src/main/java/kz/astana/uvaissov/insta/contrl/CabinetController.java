package kz.astana.uvaissov.insta.contrl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import kz.astana.uvaissov.insta.cabinet.model.NavItem;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.UserService;


@Controller
@SessionAttributes({"user","profile_info"})
@RequestMapping("/cabinet")
public class CabinetController {

	@Autowired
	private UserService userService;
	@Autowired
	private InfoService infoService;
	
	@ModelAttribute("user")//Обьявим основной аттрибут пользователя
	public User getUser() {
		System.out.println("getUser()");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
	}
	
	@ModelAttribute("profile_info")//Обьявим основной аттрибут пользователя
	public ProfileInfo getProfileInfo(@ModelAttribute("user") User user) {
		System.out.println("getProfileInfo()");
		
		ProfileInfo profileInfo =  infoService.findByInfoId(user.getProfile_info_id());
		System.out.println("getprofileInfo:"+profileInfo.getId());
		return profileInfo;
	}

    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView workspace(Model model,@ModelAttribute("user") User user,@ModelAttribute("profile_info") ProfileInfo profileInfo) {
    	ModelAndView modelAndView = new ModelAndView();
    	//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	//User user = userService.findUserByEmail(auth.getName());
    	if(user==null || user.getId()==null) {
    		modelAndView.setViewName("login");
    		return modelAndView;
    	}
    	
    	modelAndView.addObject("brand", "My Brand");
    	modelAndView.addObject("username", "@"+profileInfo.getProfilename());
    	
    	List<NavItem> navItems = new ArrayList<NavItem>();
    	navItems.add(new NavItem("Профиль", "primary", true,"left"));
    	navItems.add(new NavItem("Кнопки", "links", false,"left"));
    	navItems.add(new NavItem("Аналитика", "analytics", false,"left"));
    	modelAndView.addObject("navItems",navItems);
    	
		modelAndView.setViewName("/cabinet/index");
		return modelAndView;
    }
    
    @RequestMapping("/container/primary")
    public String primary(){
    	return "/cabinet/container/primary";
    }
    
    @RequestMapping("/container/links")
    public String links(){
    	return "/cabinet/container/links";
    }
    @RequestMapping("/container/analytics")
    public String analytics(){
    	return "/cabinet/container/analytics";
    }
    
    @RequestMapping("/container/setting")
    public String setting(){
    	return "redirect:/setting.do";
    }
   
}
