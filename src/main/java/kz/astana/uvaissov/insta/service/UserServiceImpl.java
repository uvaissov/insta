package kz.astana.uvaissov.insta.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.cabinet.constant.Backgrounds;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.Role;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.repository.RoleRepository;
import kz.astana.uvaissov.insta.repository.UserRepository;


@Service("userService")
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	private InfoService infoService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user!=null)
			user.getRoles().size();//EAGER or call method? we call method!
		return user;
	}
	
	public String findAccountByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user!=null) {
			return user.getAccount_name();
		} else 
			return null;
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        
        ProfileInfo info = new ProfileInfo();
        info.setProfilename(user.getAccount_name());
        info.setDescription("Будь на связи!");
        info.setBackground(Backgrounds.BG1);
        info.setDesign_type("material");
        info.setBackground_color("#284ab4");
        info.setBackground_svg("morphing-diamonds.svg");
        info.setElement_color("#53a5d4");
        infoService.save(info);
        user.setProfile_info_id(info.getId());
		userRepository.save(user);
	}
	
	
	@Override
	public void save(User user) {
		userRepository.save(user);
	}
	
	

}
