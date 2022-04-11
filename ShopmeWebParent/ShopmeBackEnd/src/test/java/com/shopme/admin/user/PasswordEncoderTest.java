package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	@Test
	public void testEncodePassword() {
		//create a new instance of the BCryptPasswordEncoder directly
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "12345678";
				
				//call encode() to cryptography the password
				String encodedPassword = passwordEncoder.encode(rawPassword);
		
				System.out.println(encodedPassword);
				
				//test is they are the same
				boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
				assertThat(matches).isTrue();
	}
	

}
