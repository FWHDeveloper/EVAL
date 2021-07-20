package com.fatma.eval.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatma.eval.entities.Role;
import com.fatma.eval.entities.User;
import com.fatma.eval.repository.RoleRepository;
import com.fatma.eval.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
@Service("userService")
public class UserService {
	  private UserRepository userRepository;
	    private RoleRepository roleRepository;
	    private BCryptPasswordEncoder bCryptPasswordEncoder;
	    @Autowired
	    public UserService(UserRepository userRepository,
	                       RoleRepository roleRepository,
	                       BCryptPasswordEncoder bCryptPasswordEncoder) {
	        this.userRepository = userRepository;
	        this.roleRepository = roleRepository;
	        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	    }
	    public User findUserByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }
	    public void saveUser(User user) {
	        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	        user.setActive(1);
	       // Role userRole = roleRepository.findByRole("TEACHER");
	        Role userRole = roleRepository.findByRole("STUDENT");
	        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	        userRepository.save(user);
	    }




}
