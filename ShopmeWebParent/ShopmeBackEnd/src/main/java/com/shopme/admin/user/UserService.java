package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;


/* User listing function:
 * This class will be used together with the HTML to allow ADMIN to see all the users from
 * ShopmeAdmin application in tabular format*/

@Service //business class type use this annotation
public class UserService {

	//reference to UserRepository
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	//declared the Spring Security configuration class
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//this method return the list of all users
	public List<User> listAll() {
		return (List<User>) userRepo.findAll();
	}
	
//implement a method that will return the role options list for the register form
	public List<Role> listRoles(){
		return (List<Role>) roleRepo.findAll();
	}

	public void save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		
		if (isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			
			if(user.getPassword().isEmpty()) {
				//if empty = keep the old password when editing user
				user.setPassword(existingUser.getPassword());
			} else {
				//not empty = save a new password when editing user
				//before save user encode password
				encodePassword(user);
			}
			
		}else {
		
		//before save user encode password
		encodePassword(user);
		}
		userRepo.save(user);
		
	}
	
	//method to encoding password
	private void encodePassword(User user) {
		//retrieve value of the user password
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		//update the password with the encoded value
		user.setPassword(encodedPassword);
	}
	
	//method to check the uniqueness of the email
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		
		if (userByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if (isCreatingNew) {
			if (userByEmail != null) return false;
		} else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		
		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
	}
	
	//method that delete a user from database
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if (countById == null || countById == 0 ) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		
		userRepo.deleteById(id);
		
	}
}
