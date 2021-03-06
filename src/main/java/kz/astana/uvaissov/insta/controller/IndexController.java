package kz.astana.uvaissov.insta.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import kz.astana.uvaissov.insta.cabinet.model.ActiveSession;
import kz.astana.uvaissov.insta.cabinet.model.ButtonContainer;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.entity.LogAction;
import kz.astana.uvaissov.insta.repository.GsonHttp;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.LinksService;
import kz.astana.uvaissov.insta.service.LocalDataService;
import kz.astana.uvaissov.insta.service.LogService;
import kz.astana.uvaissov.insta.service.UserService;
import kz.astana.uvaissov.insta.util.EncryptionUtil;


@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private InfoService infoService;
	
	@Autowired
	private LinksService linksService;
	
	@Autowired
	private LogService logService;
	
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private LocalDataService localDataService;
	
	@Autowired
	private GsonHttp gson;
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	CabinetController cabinet;
	
	
    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView workspace(Model model) {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("brand", "My Brand");
		modelAndView.setViewName("index");
		return modelAndView;
    }
    
    @RequestMapping(method = RequestMethod.GET,path = "/{userName:.+}")
    public ModelAndView profile(
    		Model model,
    		Device device, 
    		@PathVariable String userName,
    		@CookieValue(value = "comming", defaultValue="none") String comming,
    		HttpServletResponse response,
    		HttpServletRequest request,
    		@RequestHeader(value = "referer", required = false) final String referer
    		) {
    	ModelAndView modelAndView = new ModelAndView();
    	ProfileInfo profileInfo = infoService.findByAccountname(userName.toLowerCase());
    	
    	if(profileInfo == null) {//проброс на главную, случае отсутсвия данных
    		modelAndView.setViewName("redirect:/");
    		return modelAndView;
    	}
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth!=null) {
    		System.out.println("AUTH");
    		User user = null;
    		
    		if(request.getSession().getAttribute("user")==null) {
	    		user = userService.findUserByEmail(auth.getName());
	    		request.getSession().setAttribute("user", user);
    		}
    		
	    	ActiveSession session = (ActiveSession) request.getSession().getAttribute("userSession");
	    	if(session==null) {
	    		session = new ActiveSession();
	    		session.profileId =user.getProfile_info_id();
	    		session.userName = user.getAccount_name();
	    		session.mode="view";
	    		request.getSession().setAttribute("userSession", session);
	    	}
	    	if(session!=null && "cabinet".equalsIgnoreCase(session.mode)) {
	    		return cabinet.workspace(model,  device,request);
	    	}
    	}
    	
    	//подгрузка ссылок
    	List<Object[]> listUrls =	linksService.getButtons(profileInfo.getId());
    	List<ButtonContainer> buttons = new ArrayList<ButtonContainer>();
    	for(Object[] url : listUrls) {
    		ButtonContainer bu = new ButtonContainer(url,device,gson.getGson(),profileInfo.getId());
    		if(Arrays.asList("phone").contains(bu.getName())) {
    			bu.setType(0);//main
    		} else if(Arrays.asList("twitter","instagram","facebook").contains(bu.getName())) {
    			//bu.setType(2);//followUs
    		}
    		buttons.add(bu);
    	}
    	System.out.println("referer:"+referer);
    	System.out.println(EncryptionUtil.getDomainName(referer));
    	
    	//Логирование просмотра страницы
    	if("none".equalsIgnoreCase(comming)) {
    		CompletableFuture
    	        .runAsync(() -> logAction(Long.valueOf(profileInfo.getId()), device), Executors.newCachedThreadPool());
    		Cookie foo = new Cookie("comming", "comming");
    		foo.setMaxAge(1000); //set expire time to 1000 sec
    		response.addCookie(foo); //put cookie in response 
    	}
    	
    	//формирование логотипа
    	modelAndView.addObject("logoUrl", profileInfo.getLogo_url());
    	if(profileInfo.getLogo_url()==null && userName!=null && userName.length()>1) {
    		modelAndView.addObject("firstLetter",userName.substring(0, 1).toUpperCase());
    		modelAndView.addObject("secondLetter",userName.substring(1, 2).toUpperCase());
    	}
    	modelAndView.addObject("backGroundUrl", profileInfo.getBackground_url());
    	
    	//основная информация
    	modelAndView.addObject("username", profileInfo.getProfilename());
    	modelAndView.addObject("customText",profileInfo.getDescription());
    	modelAndView.addObject("background", profileInfo.getBackground());
    	modelAndView.addObject("element_color", profileInfo.getElement_color());
    	modelAndView.addObject("buttons",buttons);
    	
    	//System.out.println(servletContext.getResourceAsStream("\\static\\assets\\bg_svg\\eyes.svg"));
    	addSvgTemplate(modelAndView,profileInfo);
    	
    	if("material".equalsIgnoreCase(profileInfo.getDesign_type())) {
    		modelAndView.setViewName("/profile3");
    	} else if("casual".equalsIgnoreCase(profileInfo.getDesign_type())) {
    		modelAndView.setViewName("/profile");
    	}
    	
		return modelAndView;
    }
    
    private void addSvgTemplate(ModelAndView modelAndView, ProfileInfo profileInfo) {
    	String body = localDataService.getSvgBackgroundByName(profileInfo.getBackground_svg());
    	modelAndView.addObject("svg_body",body);
    	String svg = body.replaceAll("fill=\"#000\"", "fill=\""+profileInfo.getElement_color()+"\" fill-opacity=\"1\"");
    	svg = svg.replaceAll("\"", "'");
    	svg = svg.replaceAll("<", "%3C");
    	svg = svg.replaceAll(">", "%3E");
    	svg = svg.replaceAll("&", "%26");
    	svg = svg.replaceAll("#", "%23");
    	
    	modelAndView.addObject("svg","background-image:url(\"data:image/svg+xml,"+svg+"\"); background-color:"+profileInfo.getBackground_color()+";");
    	modelAndView.addObject("backgroundBody",profileInfo.getBackground_color());
    }
    
   
    /**Логирование просмотров*/
    @Transactional
	private void logAction(Long profileId,Device device) {
    	LogAction action = new LogAction();
    	action.setAction_type(1);//view
    	action.setProfileInfoId(profileId);
    	action.setMobile(device.isMobile());
    	action.setMobileOs(device.getDevicePlatform().name());
    	action.setActionDatetime(new Timestamp(System.currentTimeMillis()));
    	logService.save(action);
	}
   
}
