package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

/* This class will handle request for the user module*/

@Controller // This is a Spring MVC controller class
public class UserController {

	// need to reference UserService class

	@Autowired // this annotation let Spring framework inject an instance of the UserService
				// class at runtime
	private UserService service;

	// handler method: has the use the link name in the index.html
	// call method listAll() of the UserService that returns a List of Users objects
	// from the database
	@GetMapping("/users") // will be in HTTP GET method
	public String listAll(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);

		return "users"; // will resolve this into a view file (users.html)

	}

	// implement handler method for users/new.html
	@GetMapping("/users/new") // will be in HTTP GET method
	public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();

		// bind object to the form
		User user = new User();
		user.setEnabled(true);

		model.addAttribute("user", user); // link to the html page using th
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");

		// the method returns a logical view name (user_form) to the user form page
		return "user_form"; // create a html with this name to link

	}

	// postMapping annotation is for handling HTTP POST request
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		System.out.println(user);
		service.save(user);

		// to add the success msg
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		return "redirect:/users";

	}

	// method for edit user by the front end
	@GetMapping("/users/edit/{id}") //placeholder for the id of user being edited
	public String editUser(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			User user = service.get(id);
			List<Role> listRoles = service.listRoles(); //to add option to edit roles
			
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
			model.addAttribute("listRoles", listRoles);
			
			return "user_form";
			
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}

	// handler for delete page
	@GetMapping("/users/delete/{id}") //placeholder for the id of user being edited
	public String deleteUser(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
				try {
					service.delete(id);
					redirectAttributes.addFlashAttribute("message", 
							"The user ID " + id + " has been deleted sucessfully");
					
				} catch (UserNotFoundException ex) {
					redirectAttributes.addFlashAttribute("message", ex.getMessage());
					
				}
				return "redirect:/users";
		}
}
