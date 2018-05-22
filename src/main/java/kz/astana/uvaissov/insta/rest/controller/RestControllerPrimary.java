package kz.astana.uvaissov.insta.rest.controller;

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

import kz.astana.uvaissov.insta.cabinet.model.PrimaryModel;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.UserService;

@RestController()
@SessionAttributes({"user"})
@RequestMapping("/cabinet/data/primary")
public class RestControllerPrimary {
	@Autowired
	private UserService userService;
	@Autowired
	private InfoService infoService;
	
	@GetMapping
	public PrimaryModel getInfo(@ModelAttribute("user") User user){
		PrimaryModel primary = new PrimaryModel();
		ProfileInfo profileInfo = infoService.findByInfoId(user.getProfile_info_id());
		primary.username = profileInfo.getProfilename();
		primary.description = profileInfo.getDescription();
		return primary;
	}
	 
	@PostMapping
	@Transactional
	public ResponseEntity save(@ModelAttribute("user") User user, @RequestBody PrimaryModel primary) {
		ProfileInfo profileInfo = infoService.findByInfoId(user.getProfile_info_id());
		if(primary.username!=null) {
			profileInfo.setProfilename(primary.username.toLowerCase());
		}
		if(primary.description!=null) {
			profileInfo.setDescription(primary.description);
		}
		if(primary.background!=null) {
			profileInfo.setBackground(primary.background);
		}
		infoService.save(profileInfo);
		return new ResponseEntity(primary, HttpStatus.OK);
	}
}
