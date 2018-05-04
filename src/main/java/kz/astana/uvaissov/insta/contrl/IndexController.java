package kz.astana.uvaissov.insta.contrl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.UserService;


@Controller
@SessionAttributes({"user"})
@RequestMapping("/")
public class IndexController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private InfoService infoService;
	
    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView workspace(Model model) {
    	ModelAndView modelAndView = new ModelAndView();
    	//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	//User user = userService.findUserByEmail(auth.getName());
    	modelAndView.addObject("brand", "My Brand");
    	//modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
    	
		modelAndView.setViewName("index");
		return modelAndView;
    }
    
    @RequestMapping(method = RequestMethod.GET,path = "/{userName:.+}")
    public ModelAndView profile(@PathVariable String userName) {
    	System.out.println(userName);
    	ModelAndView modelAndView = new ModelAndView();
    	ProfileInfo profileInfo = infoService.findByProfilename(userName.toLowerCase());
    	if(profileInfo == null) {
    		modelAndView.setViewName("redirect:/");
    		return modelAndView;
    	}
    	modelAndView.addObject("brand", "@"+profileInfo.getProfilename());
    	modelAndView.addObject("customText",profileInfo.getDescription());
    	modelAndView.setViewName("/profile");
		return modelAndView;
    }
    
    
    @RequestMapping("/container/setting")
    public String setting(){
    	return "redirect:/setting.do";
    }
   
}
