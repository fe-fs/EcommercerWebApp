package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.mysql.cj.x.protobuf.MysqlxCrud.Find;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false) // Hibernate will commit the changes to the underlying database after each test
public class UserRepositoryTests {

	/* Test class for database MySQL commands input*/
	//refresh maven again
	//again
	//again
	
//we have to reference to the UserRepository repo
	@Autowired
	private UserRepository repo;
	
	
	//this class is provided by the Spring Data JPA for unit testing with repository
	@Autowired
	private TestEntityManager entityManager;
	
	@Test //this test method is to test persisting a new User object into the database
	public void testCreateUserWithOneRole() {
		//use this method empty to test is to let the Hibernate create the table
		
		//populate method with this to create a new User for the User table (import from common entity User)
			Role roleAdmin = entityManager.find(Role.class, 1); //user entityManager to get a specific role from the database
			User userFernanda = new User("test1@gmail.com", "1234", "Silva" , "Fernanda"); //email / password/ lastN / FirstN
			userFernanda.addRole(roleAdmin); //set a role to this user
			
			//call the method from the UserRepository interface
			User savedUser = repo.save(userFernanda); // returns a persistent object
			assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test //create user with 2 roles
	public void testCreateUserWithTwoRoles() {
		User userTest2 = new User("test2@gmail.com", "1234", "User" , "Test2");
		Role roleEditor = new Role(3); //editor is ID 3
		Role roleAssistent = new Role(5); //assistant is ID 5
		
		userTest2.addRole(roleEditor);
		userTest2.addRole(roleAssistent);
		
		//persist User object into the database
		User savedUser = repo.save(userTest2);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	//test method to retrieve all users from the database
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
		
	}
	
	//test method to retrieve user from ID
	@Test
	public void testGetUserById() {
		User userFernanda = repo.findById(1).get(); //returns a optional type of user
		System.out.println(userFernanda);
		assertThat(userFernanda).isNotNull();
		
	}
	
	//test to update user in the Database
	@Test
	public void testUpdateUserDetails(){
		User userFernanda = repo.findById(1).get();
		userFernanda.setEnabled(true); //default is false inside MySQL = 0
		userFernanda.setEmail("TestMudando@gmail.com");
		
		repo.save(userFernanda);
	}
	
	//test to update the roles of an existing user
	@Test
	public void testUpdateUserRoles() {
		User userTest2 = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		//remote editor
		userTest2.getRoles().remove(roleEditor); //Because override the equals() and hashcode() in Role entity class it
		                                         //is implemented, it is possible to select by ID
		
		//add Salesperson
		userTest2.addRole(roleSalesperson);
		
		repo.save(userTest2);
	}
	
	//test to delete user
	@Test
	public void testDeleteUser() {
		Integer userId = 1;
	
		repo.deleteById(userId);	
	}
	
	@Test
	public void testGetUserByEmail() {
		//String email = "abc@def.com"; //no user with this email in database -> it will fail
		String email = "test4@gmail.com"; //this is in the database == will pass the test
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountByID() {
		Integer id = 9; //user's id exist in database
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
}
