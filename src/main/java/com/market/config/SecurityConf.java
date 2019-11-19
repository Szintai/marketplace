package com.market.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {
	
	

	@Bean
	public UserDetailsService userDetailsService() {
	    return super.userDetailsService();
	}
	
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService);
	}
	
	
	

	protected void configure(HttpSecurity httpSec) throws Exception{
		
		
		
		httpSec
		.authorizeRequests()
		.regexMatchers(".*\\.css$").permitAll()
		.antMatchers("/products").permitAll()
		.antMatchers("/prod").permitAll()
		.antMatchers("/cart").permitAll()
		.antMatchers("/cartDeleteItem").permitAll()
		.antMatchers("/images/**").permitAll()
		.antMatchers("/uploads/**").permitAll()
		.antMatchers("/registration").permitAll()
		.antMatchers("/reg").permitAll()
		.antMatchers("/db/**").permitAll()
		//.anyRequest().authenticated()
		.and()
	.formLogin()
	.loginPage("/login")
	.permitAll()
	.and()
	.logout()
	.logoutSuccessUrl("/login?logout")
	.permitAll();
		
		 httpSec.csrf().disable();
	        httpSec.headers().frameOptions().disable();
		
		
	}
	
	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
	
	
	 
	
}
