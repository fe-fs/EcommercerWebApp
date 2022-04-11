package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//declare method that returns an instant user
	
	/*
	 * @Bean public UserDetailsService userDetailsService() { return new
	 * ShopmeUserDetailsService(); }
	 */
	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * //method that configures authentication provider public
	 * DaoAuthenticationProvider authenticationProvider() {
	 * DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	 * 
	 * 
	 * authProvider.setUserDetailsService(userDetailsService());
	 * 
	 * //set password encoder for authentication provider
	 * authProvider.setPasswordEncoder(PasswordEncoder()); return authProvider; }
	 * 
	 * //overwrite to configure the authentication provider
	 * 
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.authenticationProvider(authenticationProvider()); }
	 * 
	 */
	
	//This is to use view the admin site without the login screen from security
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//allow public access to the  Admin without authentication
		http.authorizeRequests().anyRequest().permitAll();
	}
	
	
	
	/*
	 * //to use the login page
	 * //allow public access to the Admin without authentication
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.authorizeRequests()
	 * .anyRequest().authenticated()
	 * .and()
	 * .formLogin()
	 * .loginPage("/login") .usernameParameter("email"); //to use login with email as usernamePermitAll method 
	 * }
	 * 
	 */ 
	/*
	 * //method to Liberate security to get the static content (images, javascript,
	 * folders,jars) //ignore authentication for static resources
	 * 
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * 
	 * web.ignoring().antMatchers("/images/**","/js/**", "/webjars/**"); }
	 */
	
}
