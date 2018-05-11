package kz.astana.uvaissov.insta.contrlrest;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import kz.astana.uvaissov.insta.cabinet.model.ButtonsModel;
import kz.astana.uvaissov.insta.cabinet.model.PrimaryModel;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.ProfileUrls;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.LinksService;
import kz.astana.uvaissov.insta.service.UserService;

@RestController()
@SessionAttributes({"user"})
@RequestMapping("/cabinet/data/links")
public class RestControllerLinks {
	@Autowired
	private UserService userService;
	@Autowired
	private LinksService linksService;
	
	@GetMapping
	public ButtonsModel getInfo(@ModelAttribute("user") User user){
		System.out.println("infoId:"+user.getId());
		ButtonsModel model = new ButtonsModel();
		List<ProfileUrls> list = linksService.findByProfileInfoId(user.getProfile_info_id());
		
		for(ProfileUrls url : list) {
			switch (url.getUrl().getName()) {
			case "whatsapp":
				model.whatsapp = url.getUrl_value();
				break;
			case "telegram":
				model.telegram = url.getUrl_value();
				break;
			case "viber":
				model.viber = url.getUrl_value();
				break;
			case "messenger":
				model.messenger = url.getUrl_value();
				break;
			case "skype":
				model.skype = url.getUrl_value();
				break;
			case "vk":
				model.vk = url.getUrl_value();
				break;
			case "facebook":
				model.facebook = url.getUrl_value();
				break;
			case "instagram":
				model.instagram = url.getUrl_value();
				break;
			case "twitter":
				model.twitter = url.getUrl_value();
				break;
			default:
				break;
			}
		}
		
		System.out.println(model.toString());
		return model;
	}
	 
	@PostMapping
	@Transactional
	public ResponseEntity save(@ModelAttribute("user") User user, @RequestBody ButtonsModel model) {
		
		List<ProfileUrls> list = linksService.findByProfileInfoId(user.getProfile_info_id());
		//whatsapp 
		ProfileUrls whatsapp = getUrlByName("whatsapp", list);
		if(model.whatsapp!=null) {
			if(whatsapp!=null) {
				if(!whatsapp.getUrl_value().equalsIgnoreCase(model.whatsapp))
					linksService.save(whatsapp);
			} else {
				
			}
		} else {
			
		}
		
		return new ResponseEntity(primary, HttpStatus.OK);
	}
	
	
	private ProfileUrls getUrlByName(String name,List<ProfileUrls> list) {
		Optional<ProfileUrls> value = list.stream().filter(a -> a.getUrl().getName().equals(name)).findFirst();
		if(value!=null) {
			return value.get();
		}
		return null;
	}
}
