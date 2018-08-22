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

import kz.astana.uvaissov.insta.cabinet.model.DesignModel;
import kz.astana.uvaissov.insta.cabinet.model.PrimaryModel;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.InfoService;
import kz.astana.uvaissov.insta.service.UserService;

@RestController()
@SessionAttributes({"user"})
@RequestMapping("/cabinet/data/design")
public class RestControllerDesign {
	@Autowired
	private InfoService infoService;
	
	@GetMapping
	public DesignModel get(@ModelAttribute("user") User user){
		DesignModel model = new DesignModel();
		ProfileInfo profileInfo = infoService.findByInfoId(user.getProfile_info_id());
		if(profileInfo!=null) {
			model.setBackground_color(profileInfo.getBackground_color());
			model.setBackground(profileInfo.getBackground());
			model.setBackground_svg(profileInfo.getBackground_svg());
			model.setElement_color(profileInfo.getElement_color());
			model.setType(profileInfo.getDesign_type());
		}
		return model;
	}
	 
	@PostMapping
	@Transactional
	public ResponseEntity save(@ModelAttribute("user") User user, @RequestBody DesignModel model) {
		ProfileInfo profileInfo = infoService.findByInfoId(user.getProfile_info_id());
		
		if(model.getBackground()!=null) {
			profileInfo.setBackground(model.getBackground());
		}
		if(model.getBackground_svg()!=null) {
			profileInfo.setBackground_svg(model.getBackground_svg());
		}
		if(model.getBackground_color()!=null) {
			profileInfo.setBackground_color(model.getBackground_color());
		}
		if(model.getElement_color()!=null) {
			profileInfo.setElement_color(model.getElement_color());;
		}
		if(model.getType()!=null) {
			profileInfo.setDesign_type(model.getType());
		}
		
		infoService.save(profileInfo);
		return new ResponseEntity(model, HttpStatus.OK);
	}
}
