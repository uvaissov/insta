package kz.astana.uvaissov.insta.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import kz.astana.uvaissov.insta.cabinet.model.ButtonModel;
import kz.astana.uvaissov.insta.cabinet.model.PrimaryModel;
import kz.astana.uvaissov.insta.entity.DicUrl;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.ProfileUrls;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.DicService;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.LinksService;
import kz.astana.uvaissov.insta.service.LocalDataService;
import kz.astana.uvaissov.insta.service.UserService;

@RestController()
@SessionAttributes({"user"})
@RequestMapping("/cabinet/data/links")
public class RestControllerLinks {
	@Autowired
	private LocalDataService localDataService;
	@Autowired
	private LinksService linksService;
	@Autowired
	private DicService dicService;
	
	@GetMapping
	public List<ButtonModel> getInfo(@ModelAttribute("user") User user){
		
		List<ProfileUrls> list = linksService.findByProfileInfoId(user.getProfile_info_id());
		List<ButtonModel> result = new ArrayList<>();
		for(ProfileUrls url : list) {
			ButtonModel model = new ButtonModel();
			DicUrl dict =  localDataService.getUrl(url.getUrl_id());
			
			model.id = url.getId();
			model.urlId = dict.getId();
			model.dataType= dict.getData_type();
			model.textPrefix = dict.getText_prefix();
			
			model.title = url.getUrl_title();
			if(StringUtils.isBlank(model.title)) {
				model.title = dict.getName();
			}
			model.value = url.getUrl_value();
			model.urlName = dict.getName();
			model.urlIcon=dict.getIcon_url();
			result.add(model);
		}
		return result;
	}
	 
	@PostMapping
	@Transactional
	public ResponseEntity save(@ModelAttribute("user") User user, @RequestBody ButtonModel model) {
		ProfileUrls url;
		if(model.id!=null && model.id>0L) {
			url = linksService.findById(model.id);
		} else {
			url = new ProfileUrls();
			url.setProfile_info_id(user.getProfile_info_id());
		}
		url.setUrl_id(model.urlId);
		url.setUrl_title(model.title);
		url.setUrl_value(model.value);
		linksService.save(url);
		
		return new ResponseEntity(model, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity remove(@ModelAttribute("user") User user, @PathVariable Long id) {
		System.out.println("delete id:"+id);
		System.out.println(linksService.findById(id));
		linksService.remove(linksService.findById(id));
		return new ResponseEntity(id, HttpStatus.OK);
	}
	
	
	private void innerEquals(List<ProfileUrls> list,Long urlConstant,String value,Long profile_info_id) {
		ProfileUrls service = getUrlById(urlConstant, list);
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
	
	private ProfileUrls getUrlById(Long id,List<ProfileUrls> list) {
		Optional<ProfileUrls> value = list.stream().filter(a -> id.equals(a.getUrl_id()) ).findFirst();
		if(value!=null && value.isPresent()) {
			return value.get();
		}
		return null;
	}
}
