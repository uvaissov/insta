package kz.astana.uvaissov.insta.contrlrest;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
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

import kz.astana.uvaissov.insta.cabinet.constant.UrlConstant;
import kz.astana.uvaissov.insta.cabinet.model.ButtonsModel;
import kz.astana.uvaissov.insta.cabinet.model.PrimaryModel;
import kz.astana.uvaissov.insta.entity.DicUrl;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.ProfileUrls;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.DicService;
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
	@Autowired
	private DicService dicService;
	
	@GetMapping
	public ButtonsModel getInfo(@ModelAttribute("user") User user){
		System.out.println("infoId:"+user.getId());
		ButtonsModel model = new ButtonsModel();
		List<ProfileUrls> list = linksService.findByProfileInfoId(user.getProfile_info_id());
		
		for(ProfileUrls url : list) {
			if(UrlConstant.WHATSAPP.equals(url.getUrl_id())) {
				model.whatsapp = url.getUrl_value();
			} else if(UrlConstant.TELEGRAM.equals(url.getUrl_id())) {
				model.telegram = url.getUrl_value();
			} else if(UrlConstant.VIBER.equals(url.getUrl_id())) {
				model.viber = url.getUrl_value();
			} else if(UrlConstant.MESSENGER.equals(url.getUrl_id())) {
				model.messenger = url.getUrl_value();
			} else if(UrlConstant.SKYPE.equals(url.getUrl_id())) {
				model.skype = url.getUrl_value();
			} else if(UrlConstant.VK.equals(url.getUrl_id())) {
				model.vk = url.getUrl_value();
			} else if(UrlConstant.FACEBOOK.equals(url.getUrl_id())) {
				model.facebook = url.getUrl_value();
			} else if(UrlConstant.INSTAGRAM.equals(url.getUrl_id())) {
				model.instagram = url.getUrl_value();
			} else if(UrlConstant.TWITTER.equals(url.getUrl_id())) {
				model.twitter = url.getUrl_value();
			} else if(UrlConstant.PHONE.equals(url.getUrl_id())) {
				model.phone = url.getUrl_value();
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
		innerEquals(list, UrlConstant.WHATSAPP, model.whatsapp,user.getProfile_info_id());
		innerEquals(list, UrlConstant.TELEGRAM, model.telegram,user.getProfile_info_id());
		innerEquals(list, UrlConstant.VIBER, model.viber,user.getProfile_info_id());
		innerEquals(list, UrlConstant.MESSENGER, model.messenger,user.getProfile_info_id());
		innerEquals(list, UrlConstant.SKYPE, model.skype,user.getProfile_info_id());
		innerEquals(list, UrlConstant.VK, model.vk,user.getProfile_info_id());
		innerEquals(list, UrlConstant.FACEBOOK, model.facebook,user.getProfile_info_id());
		innerEquals(list, UrlConstant.INSTAGRAM, model.instagram,user.getProfile_info_id());
		innerEquals(list, UrlConstant.TWITTER, model.twitter,user.getProfile_info_id());
		innerEquals(list, UrlConstant.PHONE, model.phone,user.getProfile_info_id());
		
		return new ResponseEntity(model, HttpStatus.OK);
	}
	
	
	private void innerEquals(List<ProfileUrls> list,Long urlConstant,String value,Long profile_info_id) {
		ProfileUrls service = getUrlByName(urlConstant, list);
		if(StringUtils.isNotBlank(value)) {
			if(service!=null) {
				if(!service.getUrl_value().equalsIgnoreCase(value)) {
					service.setUrl_value(value);;
					linksService.save(service);
				}
			} else {
				service = new ProfileUrls();
				service.setUrl_value(value);
				service.setUrl_id(urlConstant);
				service.setProfile_info_id(profile_info_id);
				linksService.save(service);
			}
		} else if(service!=null) {
			linksService.remove(service);
		}
	}
	
	private ProfileUrls getUrlByName(Long id,List<ProfileUrls> list) {
		Optional<ProfileUrls> value = list.stream().filter(a -> id.equals(a.getUrl_id()) ).findFirst();
		if(value!=null && value.isPresent()) {
			return value.get();
		}
		return null;
	}
}
