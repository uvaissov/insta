package kz.astana.uvaissov.insta.contrl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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

import kz.astana.uvaissov.insta.cabinet.constant.Backgrounds;
import kz.astana.uvaissov.insta.cabinet.model.ButtonContainer;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.ProfileUrls;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.LinksService;
import kz.astana.uvaissov.insta.service.UserService;


@Controller
@SessionAttributes({"user"})
@RequestMapping("/")
public class IndexController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private InfoService infoService;
	
	@Autowired
	private LinksService linksService;
	
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
    	
    	List<Object[]> listUrls =	linksService.getButtons(profileInfo.getId());
    	
    	List<ButtonContainer> buttons = new ArrayList();
    	for(Object[] url : listUrls) {
    		ButtonContainer bu = new ButtonContainer(url);
    		if(Arrays.asList("phone").contains(bu.getName())) {
    			bu.setType(0);//main
    		} else if(Arrays.asList("twitter","instagram","facebook").contains(bu.getName())) {
    			bu.setType(2);//followUs
    		}
    		buttons.add(bu);
    	}
    	
    	modelAndView.addObject("brand", "@"+profileInfo.getProfilename());
    	modelAndView.addObject("customText",profileInfo.getDescription());
    	modelAndView.addObject("background", StringUtils.defaultString(profileInfo.getBackground(), Backgrounds.BG1) );
    	modelAndView.addObject("buttons",buttons);
    	modelAndView.setViewName("/profile");
		return modelAndView;
    }
    
    
    @RequestMapping("/container/setting")
    public String setting(){
    	return "redirect:/setting.do";
    }
   
}
