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

import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.UserService;


@Controller
@SessionAttributes({"user"})
@RequestMapping("/cabinet")
public class CabinetController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")//Обьявим основной аттрибут пользователя
	public User getUser() {
		System.out.println("getUser()");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
	}

    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView workspace(Model model) {
    	ModelAndView modelAndView = new ModelAndView();
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findUserByEmail(auth.getName());
    	modelAndView.addObject("brand", "My Brand");
    	modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
    	
		modelAndView.setViewName("/cabinet/index");
		return modelAndView;
    }
    
    @RequestMapping("/container/calendar")
    public String calendar(){
    	return "/cabinet/container/calendar";
    }
    
    @RequestMapping("/container/setting")
    public String setting(){
    	return "redirect:/setting.do";
    }
   
}
