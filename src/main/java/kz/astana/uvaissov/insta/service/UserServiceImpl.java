package kz.astana.uvaissov.insta.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.entity.Role;
import kz.astana.uvaissov.insta.entity.User;
import kz.astana.uvaissov.insta.repository.RoleRepository;
import kz.astana.uvaissov.insta.repository.UserRepository;


@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		user.getRoles().size();//EAGER or call method? we call method!
		return user;
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

}
