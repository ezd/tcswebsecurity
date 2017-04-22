package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.services.UserSecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	 UserSecurityService uSecurityService;
	 @Bean
	 public BCryptPasswordEncoder mypasswordEncoder(){
		 return new BCryptPasswordEncoder();
	 }
	
	private static final String[] RESOURCES = {
			"/css/**","/js/**","/","/webjars/**","/login","/publicinfo","/contactus","/aboutus","/saveUser","/forgetPassword"
			,"/updatePassword","/register"
	};
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
        .authorizeRequests()
        	.antMatchers(RESOURCES).permitAll()
        	.antMatchers("/emp/**").hasAnyRole("EMPLOYEE","ADMIN")
        	.antMatchers("/empr/**").hasAnyRole("EMPLOYER","ADMIN")
        	.antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and().exceptionHandling().accessDeniedPage("/accessdenide")
        .and()
        .formLogin()
            .loginPage("/login").defaultSuccessUrl("/limited/info")
            .and()
			.logout().invalidateHttpSession(true).permitAll()
			.and();
		
//		http
//			.authorizeRequests()
//				.antMatchers(RESOURCES).permitAll()
//		.and()
//			.formLogin().loginPage("/login").defaultSuccessUrl("/limited/info")
//		.and()
//			.logout().invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/");
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		//		auth.inMemoryAuthentication()
//		.withUser("emp").password("emp").roles("EMPLOYEE")
//		.and()
//		.withUser("empr").password("empr").roles("EMPLOYER")
//		.and()
//		.withUser("user").password("user").roles("USER")
//		.and()
//		.withUser("admin").password("admin").roles("ADMIN");
		auth
        .userDetailsService(uSecurityService).passwordEncoder(mypasswordEncoder());
	}

}
