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
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.service.UserService;

@RestController()
@SessionAttributes({"user"})
@RequestMapping("/cabinet/data")
public class DataRestController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/primary")
	public Primary getInfo(@ModelAttribute("user") User user){
		System.out.println("infoId:"+user.getId());
		Primary primary = new Primary();
		primary.username = user.getUsername();
		return primary;
	}
	 
	@PostMapping
	@Transactional
	public ResponseEntity save(@ModelAttribute("user") User user, @RequestBody Primary primary) {
		if(primary.username!=null) {
			user.setUsername(primary.username);
		}
		userService.save(user);
		return new ResponseEntity(primary, HttpStatus.OK);
	}
}
