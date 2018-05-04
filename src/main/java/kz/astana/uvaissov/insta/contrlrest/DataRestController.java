package kz.astana.uvaissov.insta.contrlrest;

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

import kz.astana.uvaissov.insta.cabinet.model.Primary;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.UserService;

@RestController()
@SessionAttributes({"user","profile_info"})
@RequestMapping("/cabinet/data")
public class DataRestController {
	@Autowired
	private UserService userService;
	@Autowired
	private InfoService infoService;
	
	@GetMapping("/primary")
	public Primary getInfo(@ModelAttribute("user") User user,@ModelAttribute("profile_info") ProfileInfo profileInfo){
		System.out.println("infoId:"+user.getId());
		Primary primary = new Primary();
		primary.username = profileInfo.getProfilename();
		primary.description = profileInfo.getDescription();
		return primary;
	}
	 
	@PostMapping
	@Transactional
	public ResponseEntity save(@ModelAttribute("profile_info") ProfileInfo profileInfo, @RequestBody Primary primary) {
		if(primary.username!=null) {
			profileInfo.setProfilename(primary.username.toLowerCase());
		}
		if(primary.description!=null) {
			profileInfo.setDescription(primary.description);
		}
		System.out.println("profileInfo:"+profileInfo.getId());
		infoService.save(profileInfo);
		return new ResponseEntity(primary, HttpStatus.OK);
	}
}
